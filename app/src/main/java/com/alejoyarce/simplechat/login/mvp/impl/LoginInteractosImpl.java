package com.alejoyarce.simplechat.login.mvp.impl;

import com.alejoyarce.simplechat.login.mvp.LoginInteractor;
import com.alejoyarce.simplechat.login.mvp.LoginRepository;

public class LoginInteractosImpl implements LoginInteractor {

    private LoginRepository loginRepository;

    public LoginInteractosImpl() {
        loginRepository = new LoginRepositoryImpl();
    }

    @Override
    public void checkSession() {
        loginRepository.checkSession();
    }

    @Override
    public void doSignIn(String mail, String pass) {
        loginRepository.signIn(mail, pass);
    }

    @Override
    public void doSignUp(String mail, String pass) {
        loginRepository.signUp(mail, pass);
    }


}
