package com.myfirstapp.logintest2;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupMenu;
import android.view.View.OnClickListener;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


//daily task, missions 보여주기
public class StartActivity extends AppCompatActivity {

    TextView txtPhone;
    ImageButton button1;

    ImageView imageView;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("Promise Together");
        setSupportActionBar(toolbar);


        //profile picture's menu
        button1 = (ImageButton) findViewById(R.id.profile);
        button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(StartActivity.this, button1);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.picture_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                       Toast.makeText(StartActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();

                        final CharSequence[] items={"Camera","Gallery", "Cancel"};

                        AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                        builder.setTitle("Add Image");

                        builder.setItems(items, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (items[i].equals("Camera")) {

                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(intent, REQUEST_CAMERA);

                                } else if (items[i].equals("Gallery")) {

                                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    intent.setType("image/*");
                                    startActivityForResult(intent, SELECT_FILE);

                                } else if (items[i].equals("Cancel")) {
                                    dialogInterface.dismiss();
                                }
                            }
                        });
                        builder.show();

                    return true;
                    }

                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method



    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_sign_out){
            signOut();
        }

       int id = item.getItemId();

       if (id == R.id.action_settings) {
           return true;
       }

       return super.onOptionsItemSelected(item);

    }


    private void signOut(){

        AuthUI.getInstance().signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(StartActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }



    public void calendar(View view) {

    }

    public void profile() {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){


            if(requestCode==REQUEST_CAMERA){

                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                button1.setImageBitmap(bmp);


            }else if(requestCode==SELECT_FILE){

                Uri selectedImageUri = data.getData();
                button1.setImageURI(selectedImageUri);

            }

        }
    }



    public void floatButton(View view) {

    }



}

