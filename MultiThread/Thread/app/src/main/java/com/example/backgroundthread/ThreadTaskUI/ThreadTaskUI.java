package com.example.backgroundthread.ThreadTaskUI;

import android.app.Activity;

import java.lang.ref.WeakReference;

public abstract class ThreadTaskUI {


    private WeakReference<Activity> activityWeakReference;

    public ThreadTaskUI(Activity activity) {
        this.activityWeakReference = new WeakReference<Activity>(activity);
    }

    private void startTask(){
        new Thread(new Runnable() {
            public void run() {

                doInBackground();
                activityWeakReference.get().runOnUiThread(new Runnable() {
                    public void run() {
                        onPostExecute();
                    }
                });
            }
        }).start();
    }

    public void execute(){
        startTask();
    }

    public abstract void doInBackground();
    public abstract void onPostExecute();
}
