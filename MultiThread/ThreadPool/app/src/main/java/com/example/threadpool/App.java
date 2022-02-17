package com.example.threadpool;

import android.app.Application;

public class App extends Application {
    private static App INSTANCE;
    private ThreadPool threadPool;
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        threadPool = new ThreadPool();
        threadPool.initThreadPool();
        threadPool.initSingleThread();
    }

    public static App getInstance(){
        return INSTANCE;
    }

    public ThreadPool getThreadPool(){
        return threadPool;
    }
}
