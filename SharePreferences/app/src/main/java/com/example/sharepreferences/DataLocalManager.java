package com.example.sharepreferences;

import android.content.Context;
import android.util.Log;

import com.example.sharepreferences.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataLocalManager {
    private static DataLocalManager instance;
    private Preferences preferences;

    private DataLocalManager() {
    }

    public static void init(Context context){
        instance = new DataLocalManager();
        instance.preferences = new Preferences(context);
    }

    public static DataLocalManager getInstance(){
        if(instance == null){
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void setPreferenceBoolean(String key, boolean value){
        DataLocalManager.getInstance().preferences.putBooleanValue(key,value);
    }

    public static boolean getPreferenceBoolean(String key){
        return DataLocalManager.getInstance().preferences.getBooleanValue(key);
    }

    public static void setPreferenceString(String key, String value){
        DataLocalManager.getInstance().preferences.putStringValue(key,value);
    }

    public static String getPreferenceString(String key){
        return DataLocalManager.getInstance().preferences.getStringValue(key);
    }

    public static void saveUser(User user){
        Gson gson = new Gson();
        String strUserJson = gson.toJson(user);
        DataLocalManager.getInstance().preferences.putStringValue("USER",strUserJson);

    }

    public static User getUser(){
        String strUserJson = DataLocalManager.getInstance().preferences.getStringValue("USER");
        Gson gson = new Gson();
        User user = gson.fromJson(strUserJson,User.class);
        return user;
    }

    public static void saveListUser(List<User> list){
        Gson gson = new Gson();
        JsonArray jsonArray = gson.toJsonTree(list).getAsJsonArray();
        String strJsonArray = jsonArray.toString();
        DataLocalManager.getInstance().preferences.putStringValue("LIST_USER",strJsonArray);
    }

    public static List<User> getListUser(){
        Gson gson = new Gson();
        List<User> listUser = new ArrayList<>();
        String strJsonArray = DataLocalManager.getInstance().preferences.getStringValue("LIST_USER");
        try {
            JSONArray jsonArray = new JSONArray(strJsonArray);
            JSONObject jsonObject;
            for (int i=0;i<jsonArray.length();i++){
                jsonObject = jsonArray.getJSONObject(i);
                listUser.add(gson.fromJson(jsonObject.toString(),User.class));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listUser;

    }

}
