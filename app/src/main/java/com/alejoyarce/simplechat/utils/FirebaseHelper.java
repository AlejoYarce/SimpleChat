package com.alejoyarce.simplechat.utils;

import com.alejoyarce.simplechat.login.domain.User;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {

    private Firebase dataReference;

    public FirebaseHelper() {
        this.dataReference = new Firebase(Constants.FIREBASE_URL);
    }

    public static FirebaseHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public String getAuthMail() {
        AuthData authData = dataReference.getAuth();
        String mail = null;
        if ( authData != null ) {
            Map<String, Object> providerData = authData.getProviderData();
            mail = providerData.get("email").toString();
        }

        return mail;
    }

    public Firebase getUserReference(String mail) {
        Firebase userReference = null;

        if ( mail != null ) {
            String mailKey = mail.replace(".", "_");
            userReference = dataReference.getRoot().child(Constants.FIREBASE_USERS_PATH).child(mailKey);
        }

        return userReference;
    }

    public Firebase getMyUserReference() {
        return getUserReference(getAuthMail());
    }

    public Firebase getContactsReference(String mail) {
        return getUserReference(mail).child(Constants.FIREBASE_CONTACTS_PATH);
    }

    public Firebase getMyContactsReference() {
        return getUserReference(getAuthMail());
    }

    public Firebase getOneContactsReference(String mainMail, String childMail) {
        String childKey = childMail.replace(".", "_");

        return getUserReference(mainMail).child(Constants.FIREBASE_CONTACTS_PATH).child(childKey);
    }

    public Firebase getChatsReference(String receiver) {
        String keySender = getAuthMail().replace(".", "_");
        String keyReceiver = receiver.replace(".", "_");

        String keyChat = keySender + Constants.FIREBASE_CHATS_SEPARATOR + keyReceiver;
        if ( keySender.compareTo(keyReceiver) > 0 ) {
            keyChat = keyReceiver + Constants.FIREBASE_CHATS_SEPARATOR + keySender;
        }

        return dataReference.getRoot().child(Constants.FIREBASE_CHATS_PATH).child(keyChat);
    }

    public void changeUserConnectionStatus(boolean online) {
        if ( getMyUserReference() != null ) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("online", online);
            getMyUserReference().updateChildren(updates);

            notifyConnectionChange(online);
        }
    }

    public void notifyConnectionChange(boolean online) {
        notifyConnectionChange(online, false);
    }

    public void singOff() {
        notifyConnectionChange(User.OFFLINE, true);
    }

    private void notifyConnectionChange(final boolean online, final boolean signOff) {
        final String myMail = getAuthMail();

        getMyContactsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot child : dataSnapshot.getChildren() ) {
                    String mail = child.getKey();
                    Firebase reference = getOneContactsReference(mail, myMail);
                    reference.setValue(online);
                }

                if ( signOff ) {
                    dataReference.unauth();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


    public Firebase getDataReference() {
        return dataReference;
    }

    private static class SingletonHolder {
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }
}
