package com.example.backgroundthread.AsyncTask;


import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import java.lang.ref.WeakReference;

/*
When to use AsyncTask:

 + Short or interruptible tasks.
 + Tasks that don't need to report back to UI or user.
 + Low-priority tasks that can be left unfinished.

    AsyncTask can make Context leaks, missed callbacks, or crashes on configuration changes
    AysncTask cannot automatically stop when activity is destroyed
 */


public abstract class AsyncTaskUI<Params, Progress, Result, ActivityType extends AppCompatActivity> extends AsyncTask<Params, Progress, Result> {

    private WeakReference<ActivityType> activityWeakReference;

    public AsyncTaskUI(ActivityType activity) {
        this.activityWeakReference = new WeakReference<ActivityType>(activity);
    }

    public void preProcess(){
        if(activityWeakReference.get() == null || activityWeakReference.get().isFinishing()){
            return;
        }
    }

    public ActivityType getActivityWeakReference(){
        return activityWeakReference.get();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        preProcess();
    }

    @Override
    protected void onProgressUpdate(Progress... values) {
        super.onProgressUpdate(values);
        preProcess();
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        preProcess();
    }
}

