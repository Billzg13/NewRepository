package com.example.billzg.recordingappfirsttry;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;

public class RecordingController extends Fragment{
    private static final String TAG = "RecordingController";

    private MediaRecorder mediaRecorder;
    private String fileName = null;
    private DBHelper dbHelper;
    private String finalName;
    private String extension;
    private String baseNamet;


    //widgets
    private Button startRec;
    private Button stopRec;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recording_area, container, false);

        dbHelper = new DBHelper(v.getContext(), null, null, 1);
        startRec = (Button) v.findViewById(R.id.buttonStartRecording);
        stopRec = (Button) v.findViewById(R.id.buttonStopRecording);
        stopRec.setEnabled(false);


        startRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changes the fileName so its unique
                finalName = ChangeFileName(fileName, getContext());
                //starts recording to that unique file
                StartRecording(finalName);
                //also set the stop Button to enabled
                stopRec.setEnabled(true);
            }
        });


        stopRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onStopButtonPressed it saves the recording in the database
                mediaRecorder.stop();
                mediaRecorder.reset();
                stopRec.setEnabled(false);
                Log.d(TAG, "onClick: extensions value is : "+extension);
                dbHelper.SaveRecInDb(extension);
            }
        });

        return v;
    }


    private void StartRecording(String fileName) {
        Log.d(TAG, "StartRecording: started on fileName: "+fileName);
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(fileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try{
            mediaRecorder.prepare();
        }catch (Exception e ){
            Log.d(TAG, "StartRecording: prepare failed");
            Log.d(TAG, "StartRecording: "+e.getMessage());
        }
        //finally start recording
        mediaRecorder.start();

    }


    private String ChangeFileName(String fileName, Context v) {
        int fileNum = dbHelper.getLatestSize();
        fileNum++;
        Log.d(TAG, "changeFileName: started fileNum is : "+fileNum);

        baseNamet = v.getFilesDir().getAbsolutePath();
        extension = "/myRecording"+fileNum+".3gp";

        fileName = v.getFilesDir().getAbsolutePath();
        //fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/myRecording"+fileNum+".3gp";
        Log.d(TAG, "at end of ChangeFileName: filename is :"+fileName);

        return fileName;
    }

}
