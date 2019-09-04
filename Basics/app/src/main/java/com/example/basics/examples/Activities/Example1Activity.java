package com.example.basics.examples.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.example.basics.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

// --------------   Basic Example ----------------
public class Example1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example1);
        initSubscribers();
        //subscriptionContract();
        subscriptionContract_Filter();
    }

    private Disposable disposable;
    private static final String TAG = Example1Activity.class.getSimpleName();

    private Observable<String> getAnimalsObservable() {
        return Observable.fromArray(
                "Ant", "Ape",
                "Bat", "Bee", "Bear", "Butterfly",
                "Cat", "Crab", "Cod",
                "Dog", "Dove",
                "Fox", "Frog");
    }

    Observable<String> animalsObservable;

    Observer<String> animalsObserver ;


    private Observer<String> getAnimalsObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable=d;
                Log.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "Name: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "All items are emitted!");
            }
        };
    }

    private void initSubscribers(){
        // observable
        animalsObservable = getAnimalsObservable();

        // observer
        animalsObserver = getAnimalsObserver();
    }

    private void subscriptionContract(){
        animalsObservable
                .observeOn(Schedulers.io()) // This tell the Observable to run the task on a background thread.
                .subscribeOn(AndroidSchedulers.mainThread()) // This tells the Observer to receive the data on android UI thread so that you can take any UI related actions
                .subscribe(animalsObserver);
    }

    @SuppressLint("CheckResult")
    private void subscriptionContract_Filter(){
        animalsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.toLowerCase().startsWith("b");
                    }
                })
                .subscribeWith(animalsObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        disposable.dispose();
        Log.e(TAG,"Observer disposed");
    }
}
