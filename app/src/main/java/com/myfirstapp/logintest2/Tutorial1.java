package com.myfirstapp.logintest2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Tutorial1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial1);
    }

    public void tutorial1(View view) {
        startActivity(new Intent(this, Tutorial2.class));
        finish();
    }
}
