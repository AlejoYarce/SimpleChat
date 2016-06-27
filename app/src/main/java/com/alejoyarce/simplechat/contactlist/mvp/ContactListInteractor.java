package com.alejoyarce.simplechat.contactlist.mvp;

public interface ContactListInteractor {

    void subscribe();
    void unsubscribe();
    void destroyListener();
    void removeContact(String mail);

    void signOff();
    String getCurrentUserMail();
    void changeConnectionStatus(boolean online);
}
