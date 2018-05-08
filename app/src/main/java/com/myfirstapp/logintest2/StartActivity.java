package com.myfirstapp.logintest2;

import android.*;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("Promise Together");
        setSupportActionBar(toolbar);
    }


    public void calendar(View view) {

    }

    public void profile(View view) {

    }

    public void floatButton(View view) {

    }
}

