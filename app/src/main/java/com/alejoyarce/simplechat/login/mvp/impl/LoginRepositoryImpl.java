package com.alejoyarce.simplechat.login.mvp.impl;

import com.alejoyarce.simplechat.lib.EventBus;
import com.alejoyarce.simplechat.lib.impl.EventBusImpl;
import com.alejoyarce.simplechat.login.domain.User;
import com.alejoyarce.simplechat.login.mvp.LoginEvent;
import com.alejoyarce.simplechat.login.mvp.LoginRepository;
import com.alejoyarce.simplechat.utils.FirebaseHelper;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

public class LoginRepositoryImpl implements LoginRepository {

    private FirebaseHelper firebaseHelper;
    private Firebase firebase;
    private Firebase myUserReference;

    public LoginRepositoryImpl() {
        this.firebaseHelper = firebaseHelper.getInstance();
        this.firebase = firebaseHelper.getDataReference();
        this.myUserReference = firebaseHelper.getMyUserReference();
    }

    @Override
    public void signIn(String mail, String pass) {
        firebase.authWithPassword(mail, pass, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                getUserReference();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                postEvent(LoginEvent.onSignInError, firebaseError.getMessage());
            }
        });
    }

    @Override
    public void signUp(final String mail, final String pass) {
        firebase.createUser(mail, pass, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                postEvent(LoginEvent.onSignUpSuccess);
                signIn(mail, pass);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                postEvent(LoginEvent.onSignUpError, firebaseError.getMessage());
            }
        });
    }

    @Override
    public void checkSession() {
        if ( firebase.getAuth() != null ) {
            getUserReference();
        } else {
            postEvent(LoginEvent.onFailedToRecoverSession);
        }
    }

    private void getUserReference() {
        myUserReference = firebaseHelper.getMyUserReference();
        if ( myUserReference != null ) {
            myUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User currentUser = dataSnapshot.getValue(User.class);
                    if (currentUser == null) {
                        String userMail = firebaseHelper.getAuthMail();
                        if (userMail != null) {
                            currentUser = new User();
                            currentUser.setMail(userMail);
                            firebase.setValue(currentUser);
                        }
                    }

                    firebaseHelper.changeUserConnectionStatus(User.ONLINE);
                    postEvent(LoginEvent.onSignInSuccess);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
    }

    private void postEvent(int eventType, String errorMessage) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(eventType);
        if ( errorMessage != null ) {
            loginEvent.setErrorMessage(errorMessage);
        }

        EventBus eventBus = EventBusImpl.getInstance();
        eventBus.post(loginEvent);
    }

    private void postEvent(int eventType) {
        postEvent(eventType, null);
    }
}
