package com.myfirstapp.logintest2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import com.google.firebase.auth.PhoneAuthCredential;

//login@singn in firebase connection
public class LoginRegister extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private Button loginButton;
    private Button phoneButton;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private String TAG = "LoginActivity";
    private int RC_SIGN_IN = 9001;
    private LinearLayout loadingProgress;
    private LinearLayout loginScreen;
    private AppCompatEditText phoneNumber;
    private LinearLayout verifyLayout;
    private LinearLayout inputCodeLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        loadingProgress = (LinearLayout)findViewById(R.id.loadingProgress);
        loadingProgress.setVisibility(View.INVISIBLE);
        loginScreen = (LinearLayout)findViewById(R.id.login_screen);
        //initialize my Firebase Auth (get an instance of it)
        mAuth = FirebaseAuth.getInstance();



        phoneButton = (Button)findViewById(R.id.phone);
        phoneNumber = (AppCompatEditText)findViewById(R.id.phone_number);

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginRegister.this, MainActivity.class);
                startActivity(i);
            }
        });


        //on create ends here
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //here we take the account that was passed to this method when the authentication with Gmail was successful, and then use that to perform
        //a firebase authentication
        showView(loadingProgress);
        hideView(loginScreen);
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.getDisplayName(); //this is the name gotten from the Google Account, you can choose to store this in a Shared pref and use in all activities or whatever

                            //you can add an intent of the new activity where you want the user to go to next when the authentication is successful

                            Intent i = new Intent(LoginRegister.this,SignIn.class);
                            i.putExtra("userName",user.getDisplayName()); //just passing the name to the next activity
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginRegister.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }




    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void showView (View... views){
        for(View v: views){
            v.setVisibility(View.VISIBLE);

        }

    }
    private void hideView (View... views){
        for(View v: views){
            v.setVisibility(View.INVISIBLE);

        }

    }





}