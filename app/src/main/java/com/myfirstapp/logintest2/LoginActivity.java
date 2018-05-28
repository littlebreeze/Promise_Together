package com.myfirstapp.logintest2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

//login@singn in firebase connection
public class LoginActivity extends AppCompatActivity
        //implements GoogleApiClient.OnConnectionFailedListener
{

    private Button loginButton;
    private Button phoneButton;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private String TAG = "LoginActivity";
    private LinearLayout loadingProgress;
    private LinearLayout loginScreen;
    private AppCompatEditText phoneNumber;
    private LinearLayout verifyLayout;
    private LinearLayout inputCodeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        loadingProgress = (LinearLayout) findViewById(R.id.loadingProgress);
        loadingProgress.setVisibility(View.INVISIBLE);
        loginScreen = (LinearLayout) findViewById(R.id.login_screen);
        //initialize my Firebase Auth (get an instance of it)
        mAuth = FirebaseAuth.getInstance();


        phoneButton = (Button) findViewById(R.id.login_button);
        //phoneNumber = (AppCompatEditText)findViewById(R.id.phone_number);

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i = new Intent(LoginActivity.this, LoginActivity_Deprecated.class);
                startActivity(i);*/
                login();
            }
        });


        //on create ends here 알음
    }

    private static final int RC_SIGN_IN = 123;

    private void login() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                //어차피 이메일 정보를 안쓴다면 이렇게하는게 편하므로
                //user의 email 속성이 들어갈자리에 groupId 정보를 넣도록하겠음.
                String groupId = user.getEmail();
                if (groupId == null || groupId.equals("")) {
                    //소속된 그룹이 없으면
                    Intent intent = new Intent(getApplicationContext(), GroupRequestActivity.class);
                    intent.putExtra(STATIC.EXTRA_USER, user); //putExtra로 데이터 넘김
                    startActivity(intent); finish();
                }
                else {
                    //있으면 그냥 실행
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra(STATIC.EXTRA_UID, user.getUid()); //putExtra로 데이터 넘김
                    startActivity(intent); finish();
                }
            }
            else {
                // Sign in failed, check response for error code
                Toast.makeText(getApplicationContext(), "로그인 실패!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /*
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

                            Intent i = new Intent(LoginActivity.this,GroupActivity_Deprecated.class);
                            i.putExtra("userName",user.getDisplayName()); //just passing the name to the next activity
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
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
*/

}