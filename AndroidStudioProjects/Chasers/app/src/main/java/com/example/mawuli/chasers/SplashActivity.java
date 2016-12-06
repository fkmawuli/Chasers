package com.example.mawuli.chasers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;



public class SplashActivity extends AppCompatActivity {

    private Button getStartedButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String email = PreferenceManager.getDefaultSharedPreferences(this).getString("email","");
         if (!email.isEmpty()){
             startActivity(new Intent(SplashActivity.this,ChasersHomeActivity.class));
             finish();
             return;
         }

        setContentView(R.layout.activity_splash);

        getStartedButton = (Button) findViewById(R.id.button);

        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent uploadPictureIntent = new Intent(SplashActivity.this,PictureActivity.class);
                startActivity(uploadPictureIntent);
                finish();
            }
        });


    }
}
