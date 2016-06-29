package com.alejoyarce.simplechat.contactlist.mvp.impl;

import com.alejoyarce.simplechat.contactlist.mvp.ContactListInteractor;
import com.alejoyarce.simplechat.contactlist.mvp.ContactListRepository;

public class ContactListInteractorImpl implements ContactListInteractor {

    private ContactListRepository contactListRepository;

    public ContactListInteractorImpl() {
        this.contactListRepository = new ContactListRepositoryImpl();
    }

    @Override
    public void subscribe() {
        contactListRepository.subscribeToContactListEvents();
    }

    @Override
    public void unsubscribe() {
        contactListRepository.unsubscribeToContactListEvents();
    }

    @Override
    public void destroyListener() {
        contactListRepository.destroyListener();
    }

    @Override
    public void removeContact(String mail) {
        contactListRepository.removeContact(mail);
    }

    @Override
    public void signOff() {
        contactListRepository.signOff();
    }

    @Override
    public String getCurrentUserMail() {
        return contactListRepository.getCurrentUserMail();
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        contactListRepository.changeConnectionStatus(online);
    }

}
