package com.myfirstapp.logintest2;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//daily task, missions 보여주기
public class TodoDetailActivity extends AppCompatActivity {

    private static final String TAG = TodoDetailActivity.class.getSimpleName();

    private TextView title, content, endtime, requiredtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        initToolbar();

        //이전 액티비티(MainActivity)에서 LongClick한 오브젝트의 투두 데이터를 받아옴
        TODO todo = (TODO) getIntent().getParcelableExtra(STATIC.EXTRA_TODO);


        title = (TextView) findViewById(R.id.todo_detail_title);
        content = (TextView) findViewById(R.id.todo_detail_content);
        endtime = (TextView) findViewById(R.id.todo_detail_endtime);
        requiredtime = (TextView) findViewById(R.id.todo_detail_requiredtime);

        title.setText(todo.getTitle());
        content.setText(todo.getContent());
        endtime.setText(getTimeFromDate(todo.getEndTime()) + "까지");
        requiredtime.setText(todo.getRequiredTime() + "분 소요");
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.todo_detail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getTimeFromDate(Date date) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return transFormat.format(date);
    }

}
