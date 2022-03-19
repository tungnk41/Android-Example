package com.example.tranferviewdata;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class RevFragment extends Fragment {
    Button btnTranferAct;
    TextView tvContent;
    View view;
    MainActivity mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public RevFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_rev, container, false);
        mainActivity = (MainActivity) getActivity();
        init();
        return view;
    }

    private void init() {
        btnTranferAct = view.findViewById(R.id.btnTranferAct);
        tvContent = view.findViewById(R.id.tvContent);
        btnTranferAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToAct();
            }
        });

        if(getArguments() != null){
            tvContent.setText(getArguments().getString("KEY_FRAG"));
        }

    }

    //Send data from frag to activity by call function
    private void sendDataToAct() {
        String data = "Frag data";
        mainActivity.callFromFrag(data);
    }


    public void revDataFromAct(Bundle bundle) {
        tvContent.setText(bundle.getString("KEY_FRAG"));
    }
}