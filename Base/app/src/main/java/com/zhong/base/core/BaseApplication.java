package com.zhong.base.core;

import android.app.Application;
import android.util.Log;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;
import com.zhong.base.BuildConfig;

/**
 * Created by zhong on 16/7/4.
 */
public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Logger.init(getPackageName()).hideThreadInfo().methodCount(1).logLevel(BuildConfig.SHOW_LOG ? LogLevel.FULL : LogLevel.NONE);  //初始化Log工具

    }

}
