package com.example.mawuli.chasers.setup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mawuli.chasers.ChasersHomeActivity;
import com.example.mawuli.chasers.R;
import com.example.mawuli.chasers.util.User;

import io.realm.Realm;


public class SplashActivity extends AppCompatActivity {

    private Button getStartedButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Realm realm = Realm.getDefaultInstance();
        final User user = realm.where(User.class).findFirst();

        if (user != null){
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
