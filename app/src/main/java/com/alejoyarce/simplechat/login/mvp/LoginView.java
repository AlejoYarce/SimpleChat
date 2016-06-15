package com.alejoyarce.simplechat.login.mvp;

public interface LoginView {

    void enableInputs();
    void disableInputs();
    void showProgressBar();
    void hideProgressBar();

    void handleSignIn();
    void handleSignUp();

    void loginSuccess();
    void loginError(String error);

    void newUserSuccess();
    void newUserError(String error);

}
