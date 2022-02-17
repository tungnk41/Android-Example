package com.example.backgroundthread;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.example.backgroundthread.AsyncTask.AsyncTaskUI;
import com.example.backgroundthread.Loader.AsyncTaskLoaderUI;
import com.example.backgroundthread.ThreadTaskUI.ThreadTaskUI;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Switch switchState;
    private Button btnStart;
    private Button btnStop;

    private ProgressBar pbDownload;
    private Button btnStartDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchState = findViewById(R.id.switchState);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        pbDownload = findViewById(R.id.pbDownload);
        btnStartDownload = findViewById(R.id.btnStartDownload);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startThread();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopThread();
            }
        });

        btnStartDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkerTask();
            }
        });
        initLoader();

    }


    private void startThread(){

    }

    private void stopThread(){
        switchState.setChecked(false);
    }

    private void startWorkerTask(){
        int params = 10;
        new DownloadTask(this).execute(params);

        //Objects.requireNonNull(LoaderManager.getInstance(this).getLoader(1)).forceLoad();
    }

    void startThreadTaskUI(){
        new ThreadTaskUI(MainActivity.this){
            @Override
            public void execute() {
                super.execute();
            }

            @Override
            public void doInBackground() {

            }
            @Override
            public void onPostExecute() {

            }
        }.execute();
    }



    private class DownloadTask extends AsyncTaskUI<Integer,Integer,String,MainActivity> {

        private MainActivity activity;
        public DownloadTask(MainActivity activity) {
            super(activity);
            this.activity = getActivityWeakReference();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {
            Log.d(TAG, "doInBackground: " + Thread.currentThread().getName());
            String result = "Finish";
            int progress;
            for (int i = 0; i < integers[0]; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progress = (i*100)/integers[0];
                publishProgress(progress);
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            activity.pbDownload.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            activity.pbDownload.setProgress(0);
        }
    }


    private void initLoader(){
        LoaderManager.getInstance(this).initLoader(1, null , new LoaderManager.LoaderCallbacks<Integer>() {
            @NonNull
            @Override
            public Loader<Integer> onCreateLoader(int id, @Nullable  Bundle args) {
                return new AsyncTaskLoaderUI(MainActivity.this); }

            @Override
            public void onLoadFinished(@NonNull  Loader<Integer> loader, Integer data) {
                Log.d(TAG, "onLoadFinished: " + data);
                pbDownload.setProgress(data);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Integer> loader) {

            }
        });
    }

    
    /*
    Threading Components that Attach to an Activity/Fragment
    + AsyncTask
    + Loader
     */



}