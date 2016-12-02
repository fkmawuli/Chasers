package com.example.mawuli.chasers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private EditText inputEmail;
    private Button btnResetPassword, backButton;
    private ProgressBar resetProgressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        firebaseAuth = FirebaseAuth.getInstance();

        inputEmail = (EditText) findViewById(R.id.email);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        backButton = (Button) findViewById(R.id.back_btn);
        resetProgressBar = (ProgressBar)findViewById(R.id.progressBarReset);
        resetProgressBar.setVisibility(View.GONE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetPassword();

            }
        });

    }

    private void resetPassword() {

        String email = inputEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Enter your registered email",
                    Toast.LENGTH_LONG).show();
            return;
        }

        resetProgressBar.setVisibility(View.VISIBLE);

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(ResetPasswordActivity.this, "Password Reset email has been sent to you",
                                    Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ResetPasswordActivity.this, "Failed to send reset email",
                                    Toast.LENGTH_LONG).show();
                        }
                        resetProgressBar.setVisibility(View.GONE);
                    }
                });
    }
}
