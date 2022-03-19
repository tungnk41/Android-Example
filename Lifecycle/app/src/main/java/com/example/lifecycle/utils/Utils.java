package com.example.lifecycle.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Utils {
    private static Utils instance = null;
    private Utils() {

    }

    public static Utils getInstance() {
        if(instance == null){
            return new Utils();
        }
        return instance;
    }

    public void navigate(FragmentManager fragmentManager, int contentViewId, Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(contentViewId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
