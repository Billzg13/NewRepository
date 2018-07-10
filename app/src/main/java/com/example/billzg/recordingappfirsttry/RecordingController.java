package com.example.billzg.recordingappfirsttry;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.Toast;

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
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recording_area, container, false);

        dbHelper = new DBHelper(v.getContext(), null, null, 1);
        startRec = (Button) v.findViewById(R.id.buttonStartRecording);
        stopRec = (Button) v.findViewById(R.id.buttonStopRecording);
        stopRec.setEnabled(false);


        startRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startFragment
                //onReturn of fragment the name is back Via interface if the name doesn't already exist in the DB then we save it or we ask for another name
                CustomNameFragmentController myDialog = new CustomNameFragmentController();
                Log.d(TAG, "onClick: myDialog.interface? value: "+myDialog.mOnInputListener);
                myDialog.show(getActivity().getSupportFragmentManager(), "my custom dialog");

                myDialog.mOnInputListener = new CustomNameFragmentController.OnInputListener() {
                    @Override
                    public void onReturnInput(String input) { //always have to initialize the interface within the onClick view's
                        Log.d(TAG, "onReturnInput: it coems here?");
                        Toast.makeText(getContext(), "it works? :"+input, Toast.LENGTH_SHORT).show();
                        fileName = "/"+input+".3gp";
                        //checkName here?
                        if (dbHelper.isAvailable(fileName)) {
                            finalName = getContext().getFilesDir().getAbsolutePath();
                            finalName += fileName;
                            StartRecording(finalName);
                            stopRec.setEnabled(true);
                        }else {
                            Toast.makeText(getContext(), "Name already exists", Toast.LENGTH_LONG).show();
                        }
                        Log.d(TAG, "onClick: finalName is :"+finalName);

                    }
                };

            }
        });


        stopRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onStopButtonPressed it saves the recording in the database


                mediaRecorder.stop();
                mediaRecorder.reset();
                stopRec.setEnabled(false);
                Log.d(TAG, "onClick: extensions value is : "+fileName);

                dbHelper.SaveRecInDb(fileName);
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

}
