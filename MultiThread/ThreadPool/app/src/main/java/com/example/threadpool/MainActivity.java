package com.example.threadpool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    TextView tvContent;
    Button btnStart;
    volatile int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvContent = findViewById(R.id.tvContent);
        btnStart = findViewById(R.id.btnStart);
        count = 0;

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStartclicked();
            }
        });
    }

    void btnStartclicked(){
        runSingleThread();
        //runThreadPool();
    }

    void runSingleThread(){
        count = 0;
        for (int i = 0; i < 10; i++) {
//            App.getInstance().getThreadPool().runSingleThread(getRunnable());

            Future<Integer> future =  App.getInstance().getThreadPool().runSingleThread(getCallable());
            try {
                Log.d("TAG", "runSingleThread: future.get() = " + future.get());  //Block current Thread until calculate is done
                Log.d("TAG", "runSingleThread: future.get(Timeout,Time_Unit) = " + future.get(2000, TimeUnit.MILLISECONDS));  //Block current Thread in time setting

            }catch (Exception e){
                Log.d("TAG", "Exception: " + e);
            }
        }
    }


    void runThreadPool(){
        count = 0;
        for (int i = 0; i < 10; i++) {
            App.getInstance().getThreadPool().runThreadPoolExecutor(getRunnable());
        }

    }


    Runnable getRunnable(){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("TAG", "Runnable run: " + count);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvContent.setText(String.valueOf(count++));
                    }
                });
            }
        };
    }

    Callable<Integer> getCallable(){
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("TAG", "Callable run: " + count);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvContent.setText(String.valueOf(count++));
                    }
                });
                return count;
            }
        };
    }
}