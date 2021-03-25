package com.example.threadpool;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

    //Get total physic core of device
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    //Set alive time for thread when it idle
    private static final int KEEP_ALIVE_TIME = 1000;
    //time unit of alive time
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.MILLISECONDS;

    private Executor executor;
    private ThreadPoolExecutor threadPoolExecutor;

    public ThreadPool(){
        initSingleThread();
        initThreadPool();
    }

    private void initSingleThread(){
        executor = Executors.newSingleThreadExecutor();
    }

    private void initThreadPool(){
        threadPoolExecutor = new ThreadPoolExecutor(
                NUMBER_OF_CORES + 4,        //Initial number of thread in pool size
                NUMBER_OF_CORES + 8,    //Max thread in pool size
                KEEP_ALIVE_TIME,                       //Time idle thread unit wait before terminated
                KEEP_ALIVE_TIME_UNIT,                  //Time unit for KEEP_ALIVE_TIME
                new LinkedBlockingQueue<Runnable>()
        );
    }

    public void runSingleThread(Runnable runnable){
        executor.execute(runnable);
    }

    public void runThreadPoolExecutor(Runnable runnable){
        threadPoolExecutor.execute(runnable);
    }
}
