package com.example.rxjava_retrofit_example.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.rxjava_retrofit_example.Clients.ApiClient;
import com.example.rxjava_retrofit_example.Clients.ApiService;
import com.example.rxjava_retrofit_example.Clients.PrefUtils;
import com.example.rxjava_retrofit_example.Models.Note;
import com.example.rxjava_retrofit_example.Models.User;
import com.example.rxjava_retrofit_example.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ApiService apiService;

    private CompositeDisposable disposable = new CompositeDisposable();

    private List<Note> notesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        if (TextUtils.isEmpty(PrefUtils.getApiKey(this))) {
            registerUser();
        } else {
            // user is already registered, fetch all notes
            fetchAllNotes();
        }
    }

    private void registerUser() {
        // unique id to identify the device
        String uniqueId = UUID.randomUUID().toString();

        disposable.add(
                apiService
                        .register(uniqueId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<User>() {
                            @Override
                            public void onSuccess(User user) {
                                // Storing user API Key in preferences
                                PrefUtils.storeApiKey(getApplicationContext(), user.getApiKey());

                                Toast.makeText(getApplicationContext(),
                                        "Device is registered successfully! ApiKey: " + PrefUtils.getApiKey(getApplicationContext()),
                                        Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("ERROR", "onError: " + e.getMessage());
                            }
                        }));
    }

    private void fetchAllNotes() {
        disposable.add(
                apiService.fetchAllNotes()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new Function<List<Note>, List<Note>>() {
                            @Override
                            public List<Note> apply(List<Note> notes) throws Exception {
                                // TODO - note about sort
                                Collections.sort(notes, new Comparator<Note>() {
                                    @Override
                                    public int compare(Note n1, Note n2) {
                                        return n2.getId() - n1.getId();
                                    }
                                });
                                return notes;
                            }
                        })
                        .subscribeWith(new DisposableSingleObserver<List<Note>>() {
                            @Override
                            public void onSuccess(List<Note> notes) {
                                notesList.clear();
                                notesList.addAll(notes);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("ERROR", "onError: " + e.getMessage());
                            }
                        })
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}

