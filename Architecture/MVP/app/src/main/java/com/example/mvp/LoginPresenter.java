package com.example.mvp;


//Xu ly tat ca bussines logic, logic nghiep vu
//Giao tiep voi view qua interface
public class LoginPresenter {
    private LoginInterface mLoginInterface;

    public LoginPresenter(LoginInterface mLoginInterface) {
        this.mLoginInterface = mLoginInterface;
    }

    public void login(User user){
        if(user.isValidEmail() && user.isValidPassword()){
            mLoginInterface.loginSuccess();
        }else{
            mLoginInterface.loginError();
        }
    }
}
