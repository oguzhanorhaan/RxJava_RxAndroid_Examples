package com.example.basics.examples.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.basics.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Button ex1,ex2,ex3,ex4,ex5,ex6,ex7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initalizeViews();
        setClickListenersToViews();
    }

    private void initalizeViews(){
        ex1 = findViewById(R.id.button1);
        ex2 = findViewById(R.id.button2);
        ex3 = findViewById(R.id.button3);
        ex4 = findViewById(R.id.button4);
        ex5 = findViewById(R.id.button5);
        ex6 = findViewById(R.id.button6);
        ex7 = findViewById(R.id.button7);
    }

    private void setClickListenersToViews(){
        ex1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Example1Activity.class);
                startActivity(intent);
            }
        });

        ex2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Example2Activity.class);
                startActivity(intent);
            }
        });

        ex3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Example3Activity.class);
                startActivity(intent);
            }
        });

        ex4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Example4Activity.class);
                startActivity(intent);
            }
        });

        ex5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Example5Activity.class);
                startActivity(intent);
            }
        });

        ex6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Example6Activity.class);
                startActivity(intent);
            }
        });

        ex7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Example7Activity.class);
                startActivity(intent);
            }
        });
    }


}
