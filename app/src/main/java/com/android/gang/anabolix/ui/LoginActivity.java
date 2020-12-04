package com.android.gang.anabolix.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 265;
    private static boolean logged_in;
    private Button mLoginButton;

    public LoginActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Saving for later in case something breaks
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        updateSignButton();
         */

        if (checkLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            signIn();
        }
        //this.finish();
        /* Saving for later in case something breaks
        mLoginButton = findViewById(R.id.logbutton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logged_in == false)
                    signIn();
                else
                    signOut();
            }
        });
 */
    }

    public static boolean checkLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
            return true;
        else
            return false;
    }

    protected void signIn() {
        //Authentication Providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                //Email and Phone if needed later
                //new AuthUI.IdpConfig.EmailBuilder().build(),
                //new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());


        //Create and launch sign in
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
                logged_in = true;
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button.
                if (response == null) {
                    logged_in = false;
                    signIn();
                }
                logged_in = false;
                signIn();
            }
        }
    }

    /* Saving for now, just in case something breaks
    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        logged_in = false;
                        //updateSignButton();
                    }
                });
    }

    protected void updateSignButton() {
        String button_message;
        if (logged_in == false)
            button_message = "Sign In";
        else
            button_message = "Sign Out";

        TextView textView = findViewById(R.id.logbutton);
        textView.setText(button_message);
    }
    */
}