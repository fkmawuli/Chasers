package com.example.mawuli.chasers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class PictureActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMG = 1;
    private static int PERMISSION_REQUEST_CODE = 100;
    private String imgDecodableString;
    private CircleImageView circleImageView;
    private ImageButton imageButton;
    private String selectedImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);


        circleImageView = (CircleImageView) findViewById(R.id.profile_image);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isStoragePermissionGranted())
                    startPickerIntent();

        //Next Button Intent
        imageButton = (ImageButton) findViewById(R.id.nextImageButton);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedImage.isEmpty()) {
                            Toast.makeText(PictureActivity.this ,"You haven't picked an Image",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        Intent loginIntent = new Intent(PictureActivity.this,CreateAccountActivity.class);
                        loginIntent.putExtra("uploadImage",selectedImage);
                        startActivity(loginIntent);
                        finish();
                    }
                });


            }
        });

    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                requestPermission();
                //
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(PictureActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(PictureActivity.this, "Write External Storage permission allows us to do store images. " +
                    "Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(PictureActivity.this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }



       private void startPickerIntent() {
           Crop.pickImage(this);
        }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

            // When an Image is picked
            if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
               beginCrop(data.getData());
                // Get the Image from data


            } else if (requestCode == Crop.REQUEST_CROP){
                handleCrop(resultCode,data);
            }


    }
    private void beginCrop(Uri source){
        Uri destination = Uri.fromFile(new File(getCacheDir(),"cropped"));
        Crop.of(source,destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result){
        if (resultCode == RESULT_OK){
            selectedImage =Crop.getOutput(result).toString();
            circleImageView.setImageURI(Crop.getOutput(result));
        }else if (resultCode == Crop.RESULT_ERROR){
            Toast.makeText(this,Crop.getError(result).getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startPickerIntent();
                //resume tasks needing this permission
            }
        }

    }
}