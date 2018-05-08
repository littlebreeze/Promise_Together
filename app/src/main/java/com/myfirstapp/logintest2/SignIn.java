package com.myfirstapp.logintest2;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SignIn extends AppCompatActivity {

    TextView txtPhone;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_sign_out){
            signOut();
        }
        return true;
    }

    private void signOut(){

        AuthUI.getInstance().signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(SignIn.this, MainActivity.class));
                        finish();
                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("Promise Together");
        setSupportActionBar(toolbar);


    }

    public void onClickJoin(View view) {
        Intent Intent = new Intent(this, JoinActivity.class);
        startActivity(Intent);
    }

    public void onClickAlreadyJoin(View view) {
        Intent Intent = new Intent(this, StartActivity.class);
        startActivity(Intent);
    }


}
