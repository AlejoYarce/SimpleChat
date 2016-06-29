package com.alejoyarce.simplechat.contactlist.mvp.impl;

import com.alejoyarce.simplechat.contactlist.mvp.ContactListEvent;
import com.alejoyarce.simplechat.contactlist.mvp.ContactListRepository;
import com.alejoyarce.simplechat.lib.EventBus;
import com.alejoyarce.simplechat.lib.impl.EventBusImpl;
import com.alejoyarce.simplechat.login.domain.User;
import com.alejoyarce.simplechat.utils.FirebaseHelper;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

public class ContactListRepositoryImpl implements ContactListRepository {

    private FirebaseHelper firebaseHelper;
    private ChildEventListener contactEvenListener;
    private EventBus eventBus;

    public ContactListRepositoryImpl() {
        this.firebaseHelper = FirebaseHelper.getInstance();
        this.eventBus = EventBusImpl.getInstance();
    }

    @Override
    public void signOff() {
        firebaseHelper.singOff();
    }

    @Override
    public String getCurrentUserMail() {
        return firebaseHelper.getAuthUserMail();
    }

    @Override
    public void removeContact(String mail) {
        String currentUserMail = firebaseHelper.getAuthUserMail();
        firebaseHelper.getOneContactsReference(currentUserMail, mail).removeValue();
        firebaseHelper.getOneContactsReference(mail, currentUserMail).removeValue();
    }

    @Override
    public void subscribeToContactListEvents() {
        if ( contactEvenListener == null ) {
            contactEvenListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot, ContactListEvent.onContactAdded);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot, ContactListEvent.onContactChanged);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    handleContact(dataSnapshot, ContactListEvent.onContactRemoved);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

                @Override
                public void onCancelled(FirebaseError firebaseError) { }
            };
        }
        firebaseHelper.getMyContactsReference().addChildEventListener(contactEvenListener);
    }

    private void handleContact(DataSnapshot dataSnapshot, int eventType) {
        User user = new User();
        try {
            Map<String, Object> userMap = (HashMap) dataSnapshot.getValue();
            user.setMail(String.valueOf(userMap.get("email")));
            user.setOnline(new Boolean(String.valueOf(userMap.get("online"))));
        } catch (Exception e) {
            String mail = dataSnapshot.getKey();
//            boolean online = ((Boolean) dataSnapshot.getValue()).booleanValue();
            boolean online = false;
            user.setMail(mail);
            user.setOnline(online);
        }

        ContactListEvent contactListEvent = new ContactListEvent();
        contactListEvent.setEventType(eventType);
        contactListEvent.setUser(user);
        eventBus.post(contactListEvent);
    }

    @Override
    public void unsubscribeToContactListEvents() {
        if ( contactEvenListener != null ) {
            firebaseHelper.getMyContactsReference().removeEventListener(contactEvenListener);
        }
    }

    @Override
    public void destroyListener() {
        contactEvenListener = null;
    }

    @Override
    public void changeConnectionStatus(boolean online) {

    }

}
