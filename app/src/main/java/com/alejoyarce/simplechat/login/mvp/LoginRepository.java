package com.alejoyarce.simplechat.login.mvp;

public interface LoginRepository {

    void signIn(String mail, String pass);
    void signUp(String mail, String pass);
    void checkSession();

}
