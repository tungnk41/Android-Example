package com.example.rxjava;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
private TextView tvText;
private Button btnButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvText = findViewById(R.id.tvText);
        btnButton = findViewById(R.id.btnButton);

        btnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run();
                //runCustomObservable();
            }
        });


    }

    private void run(){
        List<String> arr = Arrays.asList("123","24","3243");
        //Observable.just("one", "two", "three", "four", "fives")
        Observable.fromIterable(arr)
                .subscribeOn(Schedulers.computation())
                //.flatMap(x -> Observable.just(x + "_1"))  //not keep the order items
                .concatMapEager(x -> Observable.just(x + "_1", x + "_2")) //same flatmap but keep the order items
                .filter(x -> x.length() > 2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        tvText.setText(s);
                        Log.d("TAG", "doOnNext: " + s + "  " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void runCustomObservable(){
        getObservable(1)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d("TAG", "onSubscribe runCustomObservable: stratergy 1 ");
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d("TAG", "onNext runCustomObservable: stratergy 1 " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        getObservable(2)
                .subscribeOn(Schedulers.io())
                .map(tick -> Log.d("TAG", "runCustomObservable: stratergy 2 " + tick))
                .repeat(5)
                .delay(1000,TimeUnit.MILLISECONDS)
                .subscribe();

        Disposable disposable = getObservable(3)
                .subscribeOn(Schedulers.io())
                .map(tick -> Log.d("TAG", "runCustomObservable: stratergy 3 " + tick))
                .subscribe();

        disposable.dispose();  //Stop

    }

    private Observable getObservable(int stratergy){
        switch (stratergy){
            case 1: {
                return Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter emitter) throws Throwable {
                        try {
                            for(int i = 0; i< 5;i++){
                                emitter.onNext(String.valueOf(i));
                            }
                            emitter.onComplete();
                        }catch (Exception e){
                            emitter.onError(e);
                        }
                    }
                });
            }
            case 2:{
                return Observable.timer(1000, TimeUnit.MILLISECONDS);
            }
            case 3:{
                return Observable.interval(2000,TimeUnit.MILLISECONDS);
            }
            default: return Observable.empty();
        }
    }
}