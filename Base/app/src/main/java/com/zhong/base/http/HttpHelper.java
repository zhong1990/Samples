package com.zhong.base.http;


import android.content.Context;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zhong.base.BuildConfig;
import com.zhong.base.http.ProgressSubscriber.OnResponseListener;
import com.zhong.base.utils.Constants;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhong on 16/7/4.
 */
public class HttpHelper {


    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private HttpApis httpApis;

    public interface HttpApis {

        @GET("{url}")
        Observable<HttpResponse> getResponseEntity(@Path("url") String url, @QueryMap Map<String, String> dynamic);

        @POST("{url}")
        Observable<HttpResponse> postResponseEntity(@Path("url") String url, @QueryMap Map<String, String> dynamic);
    }

    private HttpHelper() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build();

        httpApis = retrofit.create(HttpApis.class);
    }

    private static class SingletonHolder {
        private static final HttpHelper INSTANCE = new HttpHelper();
    }

    public static HttpHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public void getResponseEntity(Context context, String url, Map<String, String> params, OnResponseListener listener) {
        if (BuildConfig.SHOW_LOG) {
            Logger.d(Constants.BASE_URL + url + ((null == params) ? "" : new Gson().toJson(new Gson().toJson(params))));
        }
        httpApis.getResponseEntity(url, params)
                .map(new HttpResultFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber(listener, context));
    }

    public void postResponseEntity(Context context, String url, Map<String, String> params, OnResponseListener listener) {
        if (BuildConfig.SHOW_LOG) {
            Logger.d(Constants.BASE_URL + url + ((null == params) ? "" : new Gson().toJson(new Gson().toJson(params))));
        }
        httpApis.postResponseEntity(url, params)
                .map(new HttpResultFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber(listener, context));
    }


    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     */
    private class HttpResultFunc implements Func1<HttpResponse, HttpResponse> {
        @Override
        public HttpResponse call(HttpResponse response) {
            Logger.json(new Gson().toJson(response));
            if (1 != 1) {
                throw new ResultException(ResultException.USER_NOT_EXIST);
            }
            return response;
        }
    }

}
