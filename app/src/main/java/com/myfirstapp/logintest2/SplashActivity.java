package com.myfirstapp.logintest2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by LittleBird on 2018-04-15.
 */


//first page of app
public class SplashActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        //startActivity(new Intent(this, ScreenSlidePagerActivity.class));
        //startActivity(new Intent(this, StartActivity.class));

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
