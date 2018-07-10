package com.example.billzg.recordingappfirsttry;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class CustomNameFragmentController extends DialogFragment {
    private static final String TAG = "CustomNameFragmentContr";

    private EditText mText;
    private Button okButton;
    private Button cButton;

    private String theText;


    public interface OnInputListener {
        void onReturnInput(String input);
    }

    public OnInputListener mOnInputListener;

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.custom_name_layout_fragment, container, false);
        Log.d(TAG, "onCreateView: started");


        mText = v.findViewById(R.id.inputTextt);
        cButton = v.findViewById(R.id.cancelButton);
        okButton = v.findViewById(R.id.okButtonxml);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theText = mText.getText().toString();
                try {
                    mOnInputListener.onReturnInput(theText);
                }catch (NullPointerException e){
                    Log.d(TAG, "onClick: in try/catch "+e );
                }
                getDialog().dismiss();
            }
        });

        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mOnInputListener = (OnInputListener) getActivity();
        }catch (ClassCastException e){
            System.out.println("onAttach: ClassCastException: " + e.getMessage() );
        }
    }
}
