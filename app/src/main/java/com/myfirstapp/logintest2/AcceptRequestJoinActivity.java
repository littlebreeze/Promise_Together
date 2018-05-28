package com.myfirstapp.logintest2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

//access code from partner (연결)

public class AcceptRequestJoinActivity extends AppCompatActivity {

    private int randomCode;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_request_join);

        Bundle extras = getIntent().getExtras();
        randomCode = extras.getInt(STATIC.EXTRA_CODE, -1);
        user = extras.getParcelable(STATIC.EXTRA_USER);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("Promise Together");
        setSupportActionBar(toolbar);

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button startButton = (Button) findViewById(R.id.start_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = editText.getText().toString();
                int inputCode = Integer.parseInt(inputText);

                Toast.makeText(getApplicationContext(), "랜덤: " + randomCode + " 내: " + inputCode, Toast.LENGTH_SHORT).show();

                if (inputCode == randomCode) {
                    //맞음
                    Toast.makeText(getApplicationContext(), "성공!", Toast.LENGTH_SHORT).show();

                    user.updateEmail("2");

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra(STATIC.EXTRA_USER, user);
                    startActivity(intent); finish();
                }
                else {
                    //틀림
                    Toast.makeText(getApplicationContext(), "실패!", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    public void start(View view) {
        Intent Intent = new Intent(this, MainActivity.class);
        startActivity(Intent);
    }
}
