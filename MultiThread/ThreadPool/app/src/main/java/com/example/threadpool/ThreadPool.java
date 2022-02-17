package com.example.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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

    private ExecutorService executor;
    private ThreadPoolExecutor threadPoolExecutor;

    public ThreadPool(){
//        initSingleThread();
//        initThreadPool();
    }

    public void initSingleThread(){
        executor = Executors.newSingleThreadExecutor();
    }

    public void initThreadPool(){
        threadPoolExecutor = new ThreadPoolExecutor(
                NUMBER_OF_CORES + 4,        //Initial number of thread in pool size
                NUMBER_OF_CORES + 8,    //Max thread in pool size
                KEEP_ALIVE_TIME,                       //Time idle thread unit wait before terminated
                KEEP_ALIVE_TIME_UNIT,                  //Time unit for KEEP_ALIVE_TIME
                new LinkedBlockingQueue<>()
        );
    }

    public void runSingleThread(Runnable runnable){
        executor.submit(runnable);
    }

    public Future runSingleThread(Callable callable){
        return executor.submit(callable);
    }

    public void runThreadPoolExecutor(Runnable runnable){
        threadPoolExecutor.submit(runnable);
    }

    public Future runThreadPoolExecutor(Callable callable){
        return threadPoolExecutor.submit(callable);
    }
}
