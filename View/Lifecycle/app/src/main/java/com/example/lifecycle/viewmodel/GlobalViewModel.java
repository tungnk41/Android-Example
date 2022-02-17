package com.example.lifecycle.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GlobalViewModel extends ViewModel {
    public MutableLiveData<String> textViewModel = new MutableLiveData<>();

    public void loadData(String s)
    {
        textViewModel.setValue(s);
    }
}
