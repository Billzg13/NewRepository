package com.example.billzg.recordingappfirsttry;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private final int REQUEST_AUDIO_PERMS = 1;
    private Boolean perms;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: started");
        //check for Permissions and ask if user doesn't have perms
        checkForAudioPermissions();

    }


    private void checkForAudioPermissions() {
        Log.d(TAG, "checkForAudioPermissions: checking for permissions");

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            //permission Denied here
            //ask for the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_AUDIO_PERMS);
            System.out.println("I am asking for Permission (i am in AudioCheckPermissions)");
            perms = false;
        }else {
            System.out.println("Permission is already Granted (i am in AudioCheckPermissions)");
            perms = true;
        }

    } //end of checkForAudioPermissions


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: permissions answers are back!");
        switch (requestCode){
            case REQUEST_AUDIO_PERMS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    System.out.println("permision Granted (i am in onRequestPermissionResult)");
                    perms = true;
                }else {
                    Toast.makeText(this, "permission Denied", Toast.LENGTH_LONG).show();
                    System.out.println("permision denied (i am in onRequestPermissionResult)");
                    perms = false;
                }
        }

    }

    public void StartRecordingActivity(View view) {

        if (perms.equals(true)){
            //start Activity
            intent = new Intent(MainActivity.this, AppStartedActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(MainActivity.this, "you didn't give permissions ", Toast.LENGTH_LONG).show();
        }
    }
}
