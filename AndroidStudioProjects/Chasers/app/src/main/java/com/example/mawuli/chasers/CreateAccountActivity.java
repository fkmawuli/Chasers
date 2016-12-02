package com.example.mawuli.chasers;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private Bitmap bitmap;
    private String imagePath;
    private ProgressBar createAccountProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        firebaseAuth = FirebaseAuth.getInstance();

        btnCreateAccount = (Button) findViewById(R.id.btn_create_Account);
        btnSignIn = (Button) findViewById(R.id.sign_up_button) ;
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);

        //seeting progres Bar
        createAccountProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        createAccountProgressBar.setVisibility(View.GONE);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateAccountActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateAccountActivity.this,LoginActivity.class));
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
        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        // Get the cursor
       Cursor cursor = getContentResolver().query(Uri.parse(imagePath),
                filePathColumn, null, null, null);
        // Move to first row
        cursor.moveToFirst();

      int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
       String imgDecodableString = cursor.getString(columnIndex);

        circleImageView.setImageBitmap(BitmapFactory
               .decodeFile(imgDecodableString));
       //End of Code to display Image in Circular view



    }

    private void createAccount() {

        String email = inputEmail.getText().toString().trim();
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
                        createAccountProgressBar.setVisibility(View.VISIBLE);

                        if (task.isSuccessful()){
                            Toast.makeText(CreateAccountActivity.this,"Account Created Successfully",
                                    Toast.LENGTH_SHORT).show();
                            Intent loginIntent = new Intent(CreateAccountActivity.this,LoginActivity.class);
                            startActivity(loginIntent);
                        }
                        else {
                            Log.e("Error",task.getException().toString());
                            Toast.makeText(CreateAccountActivity.this,task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        createAccountProgressBar.setVisibility(View.GONE);
    }
}
