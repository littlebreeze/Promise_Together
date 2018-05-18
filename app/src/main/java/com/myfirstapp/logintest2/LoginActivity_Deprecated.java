package com.myfirstapp.logintest2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

//user already have account
//if not sign out-> no need to sign in
public class LoginActivity_Deprecated extends AppCompatActivity {
    private static final String TAG = "LoginActivity_Deprecated";

    private static final int RC_SIGN_IN = 123;

    private final int REQUEST_LOGIN = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() == null) {
            //Need Register


            /*startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder().setAvailableProviders(
                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()
                            )).build(),REQUEST_LOGIN);*/
        }
        else {
            //Need Login
            if(!FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty()) {
                startActivity(new Intent(this, GroupActivity_Deprecated.class)
                        .putExtra("phone", FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty())
                );
                finish();
            }
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_LOGIN){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            //Successfully signed in
            if(resultCode == RESULT_OK){
                if(!FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty()){
                    startActivity(new Intent(this,GroupActivity_Deprecated.class).putExtra("phone",FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty()));
                    finish();
                    return;
                }

                else //sign in failed
                {
                    if(response == null) {
                        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                        Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                        Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT).show();
                        return;
                    }


                }

                Toast.makeText(this, "Unknown Sign In Error !!!", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
