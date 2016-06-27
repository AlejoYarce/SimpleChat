package com.alejoyarce.simplechat.contactlist.mvp;

public interface ContactListPresenter {

    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void signOff();
    String getCurrentUserMail();
    void removeContact(String mail);
    void onEventMainThread(ContactListEvent contactListEvent);

}
