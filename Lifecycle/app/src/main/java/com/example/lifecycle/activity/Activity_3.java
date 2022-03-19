package com.example.lifecycle.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lifecycle.R;
import com.example.lifecycle.viewmodel.GlobalViewModel;

public class Activity_3 extends AppCompatActivity {
    private static final String TAG = "Activity_3";

    TextView tvTextView3;
    EditText edtEditText;
    Button   btnSave;
    GlobalViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        tvTextView3 = findViewById(R.id.tvTextView3);
        edtEditText = findViewById(R.id.edtEditText);
        btnSave = findViewById(R.id.btnSave);
        viewModel = new ViewModelProvider(this).get(GlobalViewModel.class);

        if(savedInstanceState != null)
        {
            Log.d(TAG, "onCreate: savedInstanceState != null");
            tvTextView3.setText(savedInstanceState.getString("key"));
        }
        viewModel.textViewModel.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvTextView3.setText(s);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                viewModel.loadData(edtEditText.getText().toString());
                tvTextView3.setText(edtEditText.getText().toString());
            }
        });

        Log.i(TAG, "onCreate: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("key",tvTextView3.getText().toString());
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: ");
    }
}