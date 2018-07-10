package com.example.billzg.recordingappfirsttry;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import android.widget.Button;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>{
    private static final String TAG = "RecycleViewAdapter";


    private Context mContext;
    private ArrayList<String> mRecName = new ArrayList<>();
    MediaPlayer mediaPlayer;
    private DBHelper dbHelper;

    public RecycleViewAdapter(Context mContext, ArrayList<String> mRecName) {
        this.mContext = mContext;
        this.mRecName = mRecName;
        dbHelper = new DBHelper(mContext, null, null, 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: i am here");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_recycle, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.recTxt.setText(mRecName.get(position).toString());

        //setUpMediaPlayer(mRecName.get(position).toString());

        holder.buttonForPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMediaPlayerPlay(mRecName.get(position).toString());
            }
        });

        holder.buttonForStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
            }
        });

        holder.buttonForDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //extension Gets passed
                dbHelper.deleteRec(mRecName.get(position).toString());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mRecName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout parentLayout;
        TextView recTxt;
        Button buttonForPlay;
        Button buttonForStop;
        Button buttonForDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            recTxt = itemView.findViewById(R.id.recTextView);
            parentLayout = itemView.findViewById(R.id.parentLayoutXml);
            buttonForPlay = itemView.findViewById(R.id.buttonPlay);
            buttonForStop = itemView.findViewById(R.id.buttonStop);
            buttonForDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }



    public void newMediaPlayerPlay(String extension){
        Log.d(TAG, "newMediaPlayerPlay:  started");
        String baseName = mContext.getFilesDir().getAbsolutePath();
        String finalName = baseName.concat(extension);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(finalName);
        } catch (IOException e) {
            Log.d(TAG, "onCreate: IOException caught: first try");
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            Log.d(TAG, "onCreate: IOException caught: second try");
            e.printStackTrace();
        }
        mediaPlayer.start();


    }


}
