package com.myfirstapp.logintest2;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupMenu;
import android.view.View.OnClickListener;
import android.support.design.widget.FloatingActionButton;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//daily task, missions 보여주기
public class MainActivity extends AppCompatActivity {

    TextView t1;
    TextView t2;
    TextView t3;

    private static final String TAG = "MainActivity";

    private String UID;

    TextView txtPhone;
    ImageButton button1;
    ImageButton button_calendar;

    ImageView imageView;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;


    private boolean fabExpanded = false;
    private FloatingActionButton fabSettings, fabMission, fabReminders;
    private LinearLayout layoutFabMission;
    private LinearLayout layoutFabReminders;

    Button DialogSave, Show;
    TextView Myname;
    Dialog ThisDialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        Typeface HoonTopBI = Typeface.createFromAsset(getAssets(), "fonts/HoonTop Bold italic.ttf");
        Typeface BebasNeue = Typeface.createFromAsset(getAssets(), "fonts/BebasNeue.otf");
        Typeface HoonSaemaulundongR = Typeface.createFromAsset(getAssets(), "fonts/HoonSaemaulundongR.ttf");
        Typeface Binggrae = Typeface.createFromAsset(getAssets(), "fonts/Binggrae.ttf");
        Typeface BinggraeB = Typeface.createFromAsset(getAssets(), "fonts/Binggrae-Bold.ttf");

        t1 = (TextView)findViewById(R.id.text_OurMissions);
        t1.setTypeface(HoonTopBI);

        t2 = (TextView)findViewById(R.id.text_DailyReminder);
        t2.setTypeface(HoonTopBI);

        t3 = (TextView)findViewById(R.id.text_dailyTask);
        t3.setTypeface(Binggrae);



        //이전 액티비티(LoginActivity.class)에서 넘겨준 사용자의 UID 값을 받아온다
        Intent intent = getIntent();
        UID = intent.getStringExtra(STATIC.EXTRA_UID);

        final TextView mDailyTaskTextView = (TextView) findViewById(R.id.text_dailyTask);

        //화면에 들어갈 데이터를 받아와야하지
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference promiseAlwaysRef = db.collection("promiseAlways");
        int sampleGroupId = 1; //todo sample

        db.collection("promiseAlways")
                .whereEqualTo("groupId", sampleGroupId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<String> titles = new ArrayList<>();

                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Map<String, Object> data = document.getData();
                                titles.add(data.get("title").toString());
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            titles.add("Error getting documents: " + task.getException());
                        }

                        mDailyTaskTextView.setText(getStringFromArrayListString(titles));
                    }
                });


        RecyclerView ourMissionsListview = (RecyclerView) findViewById(R.id.our_missions_recyclerview);
        ArrayList<Data_Our_Missions> missions = new ArrayList<>();
        missions.add(new Data_Our_Missions(false, "테스트임"));
        missions.add(new Data_Our_Missions(true, "2"));
        missions.add(new Data_Our_Missions(true, "3"));
        missions.add(new Data_Our_Missions(false, "4"));
        missions.add(new Data_Our_Missions(false, "4"));
        Adapter_Our_Missions adapterOurMissions = new Adapter_Our_Missions(getApplicationContext(), missions);
        ourMissionsListview.setAdapter(adapterOurMissions);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("Promise Together");
        setSupportActionBar(toolbar);

        //profile picture's menu
        button1 = (ImageButton) findViewById(R.id.profile);
        button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, button1);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.picture_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();

                        final CharSequence[] items={"Camera","Gallery", "Cancel"};

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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

        button_calendar = (ImageButton)findViewById(R.id.buttonimage_calendar);
        button_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intentLoadNewActivity = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });

        //floating button
        fabSettings = (FloatingActionButton) this.findViewById(R.id.fabSetting);
        fabMission = (FloatingActionButton) this.findViewById(R.id.fabMission);
        fabReminders= (FloatingActionButton) this.findViewById(R.id.fabReminders);
        layoutFabMission = (LinearLayout) this.findViewById(R.id.layoutFabMission);
        layoutFabReminders = (LinearLayout) this.findViewById(R.id.layoutFabReminders);
        //layoutFabSettings = (LinearLayout) this.findViewById(R.id.layoutFabSettings);


        //When main Fab (Settings) is clicked, it expands if not expanded already.
        //Collapses if main FAB was open already.
        //This gives FAB (Settings) open/close behavior
        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded == true){
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);     // 여기서 this는 Activity의 this

        /*// 여기서 부터는 알림창의 속성 설정
        builder.setTitle("Promise")        // 제목 설정
                .setView(R.layout.reminder_dialog)
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    // 확인 버튼 클릭시 설정
                    public void onClick(DialogInterface dialog, int whichButton){
                        finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                    // 취소 버튼 클릭시 설정
                    public void onClick(DialogInterface dialog, int whichButton){
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();    // 알림창 객체 생성
        dialog.show();    // 알림창 띄우기*/



        fabReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThisDialog = new Dialog(MainActivity.this);
                ThisDialog.setTitle("Promise");
                ThisDialog.setContentView(R.layout.reminder_dialog);
                final EditText Write = (EditText)ThisDialog.findViewById(R.id.write);
                Button SaveMyName = (Button)ThisDialog.findViewById(R.id.SaveNow);
                Write.setEnabled(true);
                SaveMyName.setEnabled(true);

                SaveMyName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView Daily = (TextView)findViewById(R.id.text_dailyTask);
                        Daily.setText(Daily.getText().toString() + Write.getText());
                        SharedPrefesSAVE(Write.getText().toString());
                        //디비 업로드
                        uploadReminderData(Write.getText().toString());
                        ThisDialog.cancel();
                    }
                });
                ThisDialog.show();
            }
        });


      /*  Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences SP = getApplicationContext().getSharedPreferences("NAME", 0);
                Myname.setText(SP.getString("Name", null));
            }
        });
*/

        fabMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(StartActivity.this, Missions.class));
                ThisDialog = new Dialog(MainActivity.this);
                ThisDialog.setTitle("Promise");
                ThisDialog.setContentView(R.layout.mission_dialog);
                final EditText Write = (EditText)ThisDialog.findViewById(R.id.titleM);
                Button SaveMyName = (Button)ThisDialog.findViewById(R.id.SaveNow);
                Write.setEnabled(true);
                SaveMyName.setEnabled(true);

                SaveMyName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPrefesSAVE(Write.getText().toString());
                        ThisDialog.cancel();
                    }
                });
                ThisDialog.show();
            }

        });


        //Only main FAB is visible in the beginning
        closeSubMenusFab();


    }



    private String getStringFromArrayListString(ArrayList<String> strings) {
        String result = "";
        for (String string : strings) {
            result += string + "\n";
        }

        return result;
    }

    /*
    public class MyAsyncTask extends AsyncTask<Integer, Integer, Integer> {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference promiseAlwaysRef = db.collection("promiseAlways");

        @Override
        protected Integer doInBackground(Integer... groupIds) {

            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);


        }
    }*/

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
                        startActivity(new Intent(MainActivity.this, LoginActivity_Deprecated.class));
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



    //closes FAB submenus
    private void closeSubMenusFab(){
        layoutFabMission.setVisibility(View.INVISIBLE);
        layoutFabReminders.setVisibility(View.INVISIBLE);
        fabSettings.setImageResource(R.drawable.ic_settings_black_24dp);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        layoutFabMission.setVisibility(View.VISIBLE);
        layoutFabReminders.setVisibility(View.VISIBLE);
        //Change settings icon to 'X' icon
        fabSettings.setImageResource(R.drawable.ic_close_black_24dp);
        fabExpanded = true;
    }


    public void SharedPrefesSAVE(String Name){
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("NAME", 0);
        SharedPreferences.Editor prefEDIT = prefs.edit();
        prefEDIT.putString("Name", Name);
        prefEDIT.commit();
    }

    private void uploadReminderData(String title) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> promiseAlways = new HashMap<>();
        promiseAlways.put("groupId", 1); //todo groupId
        promiseAlways.put("title", title);

        db.collection("promiseAlways")
                .add(promiseAlways)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
