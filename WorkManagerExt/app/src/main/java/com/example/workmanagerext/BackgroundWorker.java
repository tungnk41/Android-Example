package com.example.workmanagerext;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
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
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000);
                data += i;
            }
            String dataInput = getInputData().getString("abc");
            Log.d("TAG", "doWork: " + dataInput);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success();
    }
}
