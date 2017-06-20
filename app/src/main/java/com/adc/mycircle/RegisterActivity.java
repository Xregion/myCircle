package com.adc.mycircle;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener authListener;

    private String TAG;
    private Button registerButton;
    private EditText usernameField;
    private EditText emailField;
    private EditText confirmEmailField;
    private EditText passwordField;
    private EditText confirmPasswordField;

    private String username;
    private String email;
    private String firstName;
    private String lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        usernameField = (EditText) findViewById(R.id.usernameField);
        emailField = (EditText) findViewById(R.id.emailField);
        confirmEmailField = (EditText) findViewById(R.id.confirmEmailField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        confirmPasswordField = (EditText) findViewById(R.id.confirmPasswordField);

        TAG = "Register Activity";

        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Register Button Clicked");
                if (validateFields())
                    createAccount(emailField.getText().toString(), passwordField.getText().toString());
            }
        });

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    firstName = ((TextView) findViewById(R.id.firstName)).getText().toString();
                    lastName = ((TextView) findViewById(R.id.lastName)).getText().toString();
                    // Attempt to create JSONObject to send to the database
                    try { // If successful send the JSONObject to our database and send the verification email
                        JSONObject userObject = new JSONObject(buildUserObject());
                        sendVerificationEmail ();
                    } catch (JSONException e) { // If we fail to create the object inform the user and delete the user from firebase
                        Toast.makeText(RegisterActivity.this, "Failed to get user data, please try again", Toast.LENGTH_LONG).show();
                        user.delete();
                        e.printStackTrace();
                        //restart this activity
                        overridePendingTransition(0, 0);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                    }
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    private void createAccount(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationEmail () {
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                            // after email is sent just logout the user and finish this activity
                            Toast.makeText(RegisterActivity.this, R.string.verification_email_sent,
                                    Toast.LENGTH_LONG).show();
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        }
                        else
                        {
                            // email not sent, so display message, delete user, and restart the activity
                            Toast.makeText(RegisterActivity.this, R.string.email_failed,
                                    Toast.LENGTH_LONG).show();
                            user.delete();
                            //restart this activity
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }

    private String buildUserObject () {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{ ");
        stringBuilder.append("username:");
        stringBuilder.append(username);
        stringBuilder.append(", ");
        stringBuilder.append("photoUrl:");
        stringBuilder.append(" ");
        stringBuilder.append(", ");
        stringBuilder.append("firstname:");
        stringBuilder.append(firstName);
        stringBuilder.append(", ");
        stringBuilder.append("lastname:");
        stringBuilder.append(lastName);
        stringBuilder.append(", ");
        stringBuilder.append("email:");
        stringBuilder.append(email);
        stringBuilder.append(" }");

        return stringBuilder.toString();
    }

    //TODO: Validate fields better
    private boolean validateFields() {
        username = usernameField.getText().toString();
        email = emailField.getText().toString();
        String confirmEmail = confirmEmailField.getText().toString();
        String password = passwordField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();

        if (username != username) {
            return false;
        } else if ((password.length() < 8 || password.length() > 30) && !password.equals(confirmPassword)) {
            return false;
        } else if (!email.contains("@") && !email.contains(".com") && !email.equals(confirmEmail)) {
            return false;
        } else
            return true;
    }

    public class UploadUserDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... objects) {


            return null;
        }
    }
}
