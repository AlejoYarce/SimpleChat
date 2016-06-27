package com.alejoyarce.simplechat.contactlist.mvp;

public interface ContactListRepository {

    void signOff();
    String getCurrentUserMail();
    void removeContact(String mail);

    void subscribeToContactListEvents();
    void unsubscribeToContactListEvents();
    void destroyListener();
    void changeConnectionStatus(boolean online);

}
