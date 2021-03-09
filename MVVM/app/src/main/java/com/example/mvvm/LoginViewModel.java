package com.example.mvvm;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

public class LoginViewModel extends BaseObservable {
    private String email;
    private String password;
    public ObservableField<String> messageLogin = new ObservableField<>();
    public ObservableField<Boolean> isShowMessage = new ObservableField<>();

    public LoginViewModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginViewModel() {

    }

    @Bindable
    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public void onClickLogin(){
        isShowMessage.set(true);
        User user = new User(getEmail(),getPassword());
        if(user.isValidEmail() && user.isValidPassword()){
            messageLogin.set("Login success");
        }else{
            messageLogin.set("Login invalid");
        }
    }


}
