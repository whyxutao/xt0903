package com.bawei.xutao.app;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //应用程序入口
        //初始化公共工具
        context = this;
    }
}
