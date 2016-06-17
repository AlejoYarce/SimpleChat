package com.alejoyarce.simplechat.login.mvp.impl;

import com.alejoyarce.simplechat.login.mvp.LoginInteractor;
import com.alejoyarce.simplechat.login.mvp.LoginPresenter;
import com.alejoyarce.simplechat.login.mvp.LoginView;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractosImpl();
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void checkAuthenticatedUser() {
        if ( loginView != null ) {
            loginView.disableInputs();
            loginView.showProgressBar();
            loginInteractor.checkSession();
        }
    }

    @Override
    public void validateLogin(String mail, String pass) {
        if ( loginView != null ) {
            loginView.disableInputs();
            loginView.showProgressBar();
            loginInteractor.doSignIn(mail, pass);
        }
    }

    @Override
    public void registerNewUser(String mail, String pass) {
        if ( loginView != null ) {
            loginView.disableInputs();
            loginView.showProgressBar();
            loginInteractor.doSignUp(mail, pass);
        }
    }

    private void onSignInSuccess() {
        if ( loginView != null ) {
            loginView.loginSuccess();
        }
    }

    private void onSignUpSuccess() {
        if ( loginView != null ) {
            loginView.newUserSuccess();
        }
    }

    private void onSignInError(String error) {
        if ( loginView != null ) {
            loginView.hideProgressBar();
            loginView.enableInputs();
            loginView.loginError(error);
        }
    }

    private void onSignUpError(String error) {
        if ( loginView != null ) {
            loginView.hideProgressBar();
            loginView.enableInputs();
            loginView.newUserError(error);
        }
    }
}
