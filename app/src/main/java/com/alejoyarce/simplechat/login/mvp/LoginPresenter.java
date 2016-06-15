package com.alejoyarce.simplechat.login.mvp;

public interface LoginPresenter {

    void onDestroy();
    void checkAuthenticatedUser();
    void validateLogin(String mail, String pass);
    void registerNewUser(String mail, String pass);

}
