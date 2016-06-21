package com.alejoyarce.simplechat.login.mvp.impl;

import com.alejoyarce.simplechat.lib.EventBus;
import com.alejoyarce.simplechat.lib.impl.EventBusImpl;
import com.alejoyarce.simplechat.login.mvp.LoginEvent;
import com.alejoyarce.simplechat.login.mvp.LoginInteractor;
import com.alejoyarce.simplechat.login.mvp.LoginPresenter;
import com.alejoyarce.simplechat.login.mvp.LoginView;

import org.greenrobot.eventbus.Subscribe;

public class LoginPresenterImpl implements LoginPresenter {

    private EventBus eventBus;
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractosImpl();
        this.eventBus = EventBusImpl.getInstance();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
        eventBus.unRegister(this);
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

    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent loginEvent) {
        switch ( loginEvent.getEventType() ) {
            case LoginEvent.onSignInError:
                onSignInError(loginEvent.getErrorMessage());
                break;
            case LoginEvent.onSignUpError:
                onSignUpError(loginEvent.getErrorMessage());
                break;
            case LoginEvent.onSignInSuccess:
                onSignInSuccess();
                break;
            case LoginEvent.onSignUpSuccess:
                onSignUpSuccess();
                break;
            case LoginEvent.onFailedToRecoverSession:
                onFailedToRecoverSession();
                break;
        }
    }

    private void onFailedToRecoverSession() {
        if ( loginView != null ) {
            loginView.hideProgressBar();
            loginView.enableInputs();
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
