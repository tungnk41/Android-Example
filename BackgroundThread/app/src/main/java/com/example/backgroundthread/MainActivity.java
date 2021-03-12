package com.example.backgroundthread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Switch switchState;
    private Button btnStart;
    private Button btnStop;

    private ProgressBar pbDownload;
    private Button btnStartDownload;

    private volatile boolean stopThread = false;


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
                startBackgroundTask();
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
    }


    private void startThread(){
        stopThread = false;
        WorkerThread workerThread = new WorkerThread(10);
        workerThread.start();

//        ObjectRunable objectRunable = new ObjectRunable(10);
//        new Thread(objectRunable).start();



//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG, "temp thread: ");
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        switchState.setChecked(true);
//                        Log.d(TAG, "runOnUiThread thread: ");
//                    }
//                });
//            }
//        }).start();
//

    }

    private void stopThread(){
        stopThread = true;
    }





    void startBackgroundTask(){
        new BackgroundTask(MainActivity.this){
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

    private void startWorkerTask(){
        int params = 10;
        new WorkerTask(MainActivity.this).execute(params);
    }

    //AsyncTask can make Context leaks, missed callbacks, or crashes on configuration changes.
    static class WorkerTask extends AsyncTask<Integer,Integer,String>{
        private WeakReference<MainActivity> activityWeakReference;

        public WorkerTask(MainActivity activity) {
            this.activityWeakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity activity = activityWeakReference.get();
        if(activity == null || activity.isFinishing()){
            return;
        }
        activity.pbDownload.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Integer... integers) {
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
        MainActivity activity = activityWeakReference.get();
        if(activity == null || activity.isFinishing()){
            return;
        }
        //Update progress value from doInBackground to UI thread
        activity.pbDownload.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        MainActivity activity = activityWeakReference.get();
        if(activity == null || activity.isFinishing()){
            return;
        }
        //Update result from doInBackground to UI thread
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
        activity.pbDownload.setProgress(0);
        activity.pbDownload.setVisibility(View.INVISIBLE);

    }

}

    class WorkerThread extends Thread{
        int second;

        public WorkerThread(int second) {
            this.second = second;
        }

        @Override
        public void run() {
            super.run();
            for(int i=0;i<second;i++){
                Log.d(TAG, "startThread: " + i);
                if(stopThread){
                    break;
                }
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switchState.setChecked(true);
                    Log.d(TAG, "runOnUiThread thread: ");
                }
            });


        }
    }


    class ObjectRunable implements Runnable{

        int second;
        Handler handler =  new Handler(Looper.getMainLooper());

        public ObjectRunable(int second) {
            this.second = second;
        }

        @Override
        public void run() {
            Log.d(TAG, "Start Runable: ");

//            switchState.post(new Runnable() {
//                @Override
//                public void run() {
//                    switchState.setChecked(true);
//                }
//            });

            handler.post(new Runnable() {
                @Override
                public void run() {
                    switchState.setChecked(true);
                    Log.d(TAG, "End Runable ");
                }
            });
        }
    }



}