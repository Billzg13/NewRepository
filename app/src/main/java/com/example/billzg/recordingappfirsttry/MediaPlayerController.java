package com.example.billzg.recordingappfirsttry;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Context;

import java.util.ArrayList;

public class MediaPlayerController extends Fragment{
    private static final String TAG = "MediaPlayerController";

    private RecyclerView recyclerViewb;
    private SwipeRefreshLayout refreshLayout;
    private RecycleViewAdapter myAdapter;
    private ArrayList<String> myRecordings = new ArrayList<>();
    private DBHelper dbHelper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started");
        View v = inflater.inflate(R.layout.media_player_area, container, false);

        recyclerViewb = (RecyclerView)v.findViewById(R.id.recyclerViewXml);
        refreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh: we are here on refresh");
                myRecordings = dbHelper.fetchAllRecs();

                myAdapter = new RecycleViewAdapter(getContext(), myRecordings);

                recyclerViewb.setAdapter(myAdapter);
                refreshLayout.setRefreshing(false);
            }
        });
        dbHelper = new DBHelper(v.getContext(), null, null, 1);
        myRecordings = dbHelper.fetchAllRecs();

        myAdapter = new RecycleViewAdapter(v.getContext(), myRecordings);

        recyclerViewb.setAdapter(myAdapter);
        recyclerViewb.setLayoutManager(new LinearLayoutManager(v.getContext()));


        return v;
    }

}
