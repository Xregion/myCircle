package com.adc.mycircle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordResetActivity extends AppCompatActivity {

    private Button resetPasswordBtn;
    private EditText emailField;

    private String TAG;

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        TAG = "PasswordResetActivity";

        emailField = (EditText) findViewById(R.id.email_field);

        resetPasswordBtn = (Button) findViewById(R.id.reset_button);
        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Reset Button Clicked");
                sendPasswordResetEmail();
            }
        });
    }

    void sendPasswordResetEmail () {
        String emailAddress = emailField.getText().toString();
        if (emailAddress != "") { // ADD VERIFICATION ON EMAIL TO MAKE SURE IT'S VALID AND WE HAVE IT ON RECORD
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent.");
                                Toast.makeText(PasswordResetActivity.this, R.string.reset_password_email_sent,
                                        Toast.LENGTH_LONG).show();
                                startActivity(new Intent(PasswordResetActivity.this, LoginActivity.class));
                                finish();
                            }
                        }
                    });
        }
    }
}
