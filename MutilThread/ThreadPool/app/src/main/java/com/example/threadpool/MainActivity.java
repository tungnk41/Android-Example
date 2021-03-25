package com.example.threadpool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvContent;
    Button btnStart;
    int count;

    ThreadPool threadPool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvContent = findViewById(R.id.tvContent);
        btnStart = findViewById(R.id.btnStart);
        threadPool = new ThreadPool();
        count = 0;

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStartclicked();
            }
        });
    }

    void btnStartclicked(){
        //runSingleThread();
        runThreadPool();
    }

    void runSingleThread(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvContent.setText(String.valueOf(count++));
                    }
                });
            }
        };

        for (int i = 0; i < 10; i++) {
            threadPool.runSingleThread(runnable);
        }
    }


    void runThreadPool(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvContent.setText(String.valueOf(count++));
                    }
                });
            }
        };

        for (int i = 0; i < 10; i++) {
            threadPool.runThreadPoolExecutor(runnable);
        }

    }
}