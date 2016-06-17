package com.alejoyarce.simplechat.login.mvp.impl;

import android.util.Log;

import com.alejoyarce.simplechat.login.mvp.LoginRepository;
import com.alejoyarce.simplechat.utils.FirebaseHelper;

public class LoginRepositoryImpl implements LoginRepository {

    private FirebaseHelper firebaseHelper;

    public LoginRepositoryImpl() {
        this.firebaseHelper = firebaseHelper.getInstance();
    }

    @Override
    public void signIn(String mail, String pass) {
        Log.e("LoginRepositoryImpl", "signIn");
    }

    @Override
    public void signUp(String mail, String pass) {
        Log.e("LoginRepositoryImpl", "signUp");
    }

    @Override
    public void checkSession() {
        Log.e("LoginRepositoryImpl", "checkSession");
    }
}
