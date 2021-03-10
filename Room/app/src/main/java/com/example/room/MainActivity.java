package com.example.room;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.room.database.UserDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_UPDATE_USER = 1;
    private EditText edtUserName;
    private EditText edtAddress;
    private Button btnAddUser;
    private RecyclerView rvUser;
    private TextView tvDeleteAll;
    private EditText edtSearch;

    private UserAdapter userAdapter;
    private List<User> listUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        loadData();

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        tvDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllUser();
            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    handleSearchUser();
                }
                return false;
            }
        });

    }

    private void initView(){
        edtUserName = findViewById(R.id.edtUserName);
        edtAddress = findViewById(R.id.edtAddress);
        btnAddUser = findViewById(R.id.btnAddUser);
        rvUser = findViewById(R.id.rvUser);
        tvDeleteAll = findViewById(R.id.tvDeleteAll);
        edtSearch = findViewById(R.id.edtSearch);

        userAdapter = new UserAdapter(new UserAdapter.IClickItemUser() {
            @Override
            public void updateUser(User user) {
                clickUpdateUser(user);
            }

            @Override
            public void deleteUser(User user) {
                clickDeleteUser(user);
            }
        });
        listUser = new ArrayList<>();
        userAdapter.setData(listUser);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvUser.setLayoutManager(linearLayoutManager);
        rvUser.setAdapter(userAdapter);
    }

    private void addUser() {
        String strUserName = edtUserName.getText().toString().trim();
        String strAddress = edtAddress.getText().toString().trim();

        if(TextUtils.isEmpty(strUserName) || TextUtils.isEmpty(strAddress)){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(strUserName,strAddress,2000);
        if(isUserExist(user)){
            Toast.makeText(this, "User exist", Toast.LENGTH_SHORT).show();
            return;
        }
        UserDatabase.getInstance(this).userDAO().insertUser(user);
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        edtAddress.setText("");
        edtUserName.setText("");
        loadData();
        hideSoftKeyboard();

    }
    private void hideSoftKeyboard(){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }
    }
    private void loadData(){
        listUser = UserDatabase.getInstance(this).userDAO().getListUser();
        userAdapter.setData(listUser);
    }

    private boolean isUserExist(User user){
        List<User> list = UserDatabase.getInstance(this).userDAO().checkUser(user.getUserName());
        return list!=null && !list.isEmpty();
    }

    private void clickUpdateUser(User user) {
        Intent intent = new Intent(MainActivity.this,UpdateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",user);
        intent.putExtras(bundle);
        startActivityForResult(intent,REQUEST_UPDATE_USER);
    }

    private void clickDeleteUser(User user){
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete user")
                .setMessage("Are you sure ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserDatabase.getInstance(MainActivity.this).userDAO().deleteUser(user);
                        Toast.makeText(MainActivity.this, "Delete user successfully", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteAllUser(){
        new AlertDialog.Builder(this)
                .setTitle("Delete all user")
                .setMessage("Are you sure ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserDatabase.getInstance(MainActivity.this).userDAO().deleteAllUser();
                        Toast.makeText(MainActivity.this, "Delete user successfully", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void handleSearchUser(){
        String strSearch = edtSearch.getText().toString().trim();
        listUser.clear();
        listUser = UserDatabase.getInstance(this).userDAO().searchUser(strSearch);
        userAdapter.setData(listUser);
        hideSoftKeyboard();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_UPDATE_USER && resultCode == Activity.RESULT_OK){
            loadData();
        }
    }
}