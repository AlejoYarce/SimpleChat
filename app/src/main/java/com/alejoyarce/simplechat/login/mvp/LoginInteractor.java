package com.alejoyarce.simplechat.login.mvp;

public interface LoginInteractor {

    void checkSession();
    void doSignIn(String mail, String pass);
    void doSignUp(String mail, String pass);
}
