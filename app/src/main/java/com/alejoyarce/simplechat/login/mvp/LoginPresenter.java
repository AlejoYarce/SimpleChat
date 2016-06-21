package com.alejoyarce.simplechat.login.mvp;

public interface LoginPresenter {

    void onCreate();
    void onDestroy();
    void checkAuthenticatedUser();
    void validateLogin(String mail, String pass);
    void registerNewUser(String mail, String pass);
    void onEventMainThread(LoginEvent loginEvent);
}
