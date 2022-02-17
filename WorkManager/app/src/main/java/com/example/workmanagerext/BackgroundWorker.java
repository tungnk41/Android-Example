package com.example.workmanagerext;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class BackgroundWorker extends Worker {

    public BackgroundWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        int data = 0;
        //Do work here
        try {
            for (int i = 0; i < 2; i++) {
                Thread.sleep(1000);
                data += i;
            }
            String dataInput = getInputData().getString("key");
            Log.d("TAG", "doWork: " + dataInput + " ThreadID : " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.failure();
        }

        Data output = new Data.Builder()
                .putString("result_key", "result_value")
                .build();
        return Result.success(output);
    }
}
