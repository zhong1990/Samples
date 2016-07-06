package com.zhong.base.http;

import android.content.Context;
import android.widget.Toast;


import com.orhanobut.logger.Logger;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by zhong on 16/7/4.
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressDialogHandler.ProgressCancelListener {

    private OnResponseListener mOnResponseListener;
    private ProgressDialogHandler    mProgressDialogHandler;

    private Context context;

    public ProgressSubscriber(OnResponseListener mSubscriberOnNextListener, Context context) {
        this.mOnResponseListener = mSubscriberOnNextListener;
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    private void showProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ResultException) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            Logger.d(e.getMessage());
        }
        if (mOnResponseListener != null) {
            mOnResponseListener.onError();
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mOnResponseListener != null) {
            mOnResponseListener.onSuccess(t);
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    public static abstract class OnResponseListener<T> {

        public abstract void onSuccess(T t);

        void onError(){}
    }
}