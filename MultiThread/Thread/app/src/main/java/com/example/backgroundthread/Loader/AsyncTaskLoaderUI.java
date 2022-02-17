package com.example.backgroundthread.Loader;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;


public class AsyncTaskLoaderUI extends  AsyncTaskLoader<Integer> {


    public AsyncTaskLoaderUI(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public Integer loadInBackground() {
        Log.d("TAG", "LoadInBackground: " + Thread.currentThread().getName());
        try {
            for(int i= 0;i < 10; i++){
                Thread.sleep(1000);
                deliverResult(i*100/10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 0;
    }


}
