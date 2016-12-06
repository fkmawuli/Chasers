package com.example.mawuli.chasers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mawuli.chasers.util.Helper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateAccountActivity extends AppCompatActivity{

    private FirebaseAuth firebaseAuth;

    private EditText inputEmail, inputPassword;
    private Button btnCreateAccount, btnSignIn, btnResetPassword;
    private CircleImageView circleImageView;
    private String imagePath;
    private Context context;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        firebaseAuth = FirebaseAuth.getInstance();
        context = this;

        btnCreateAccount = (Button) findViewById(R.id.btn_create_Account);
        btnSignIn = (Button) findViewById(R.id.create_account) ;
        btnResetPassword = (Button) findViewById(R.id.btn_forget_password);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,LoginActivity.class));
            }
        });



            btnCreateAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    createAccount();
                }
            });

       //Code to display Image in Circular view
       circleImageView = (CircleImageView) findViewById(R.id.profile_image);

        imagePath = getIntent().getStringExtra("uploadImage");
        Uri uri = Uri.parse(imagePath);
        circleImageView.setImageURI(uri);
       //End of Code to display Image in Circular view

    }



    private void createAccount() {

        if (!Helper.isNetworkAvailable(context)){
            Toast.makeText(this,"Network is not available",Toast.LENGTH_SHORT).show();
            return;
        }

       final String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();


        if (TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Enter email address!",Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Enter password!",Toast.LENGTH_LONG).show();
            return;
        }

        if (password.length() < 6){
            Toast.makeText(getApplicationContext(),"Password is too short",Toast.LENGTH_LONG).show();
            return;
        }




        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            PreferenceManager.getDefaultSharedPreferences(context).edit().putString("email",email).apply();

                            Toast.makeText(CreateAccountActivity.this,"Account Created Successfully", Toast.LENGTH_SHORT).show();
                            Intent loginIntent = new Intent(context,LoginSucess.class);
                            startActivity(loginIntent);
                            finish();
                        }
                        else {
                            Log.e("Error",task.getException().toString());
                            Toast.makeText(CreateAccountActivity.this,task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

}
