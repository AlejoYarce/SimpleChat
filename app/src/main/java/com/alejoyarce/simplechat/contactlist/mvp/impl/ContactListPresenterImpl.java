package com.alejoyarce.simplechat.contactlist.mvp.impl;

import com.alejoyarce.simplechat.contactlist.mvp.ContactListEvent;
import com.alejoyarce.simplechat.contactlist.mvp.ContactListInteractor;
import com.alejoyarce.simplechat.contactlist.mvp.ContactListPresenter;
import com.alejoyarce.simplechat.contactlist.mvp.ContactListView;
import com.alejoyarce.simplechat.lib.EventBus;
import com.alejoyarce.simplechat.lib.impl.EventBusImpl;
import com.alejoyarce.simplechat.login.domain.User;

import org.greenrobot.eventbus.Subscribe;

public class ContactListPresenterImpl implements ContactListPresenter {

    private EventBus eventBus;
    private ContactListView view;
    private ContactListInteractor contactListInteractor;

    public ContactListPresenterImpl(ContactListView view) {
        this.view = view;
        eventBus = EventBusImpl.getInstance();
        this.contactListInteractor = new ContactListInteractorImpl();
    }

    @Override
    public void onPause() {
        contactListInteractor.changeConnectionStatus(User.OFFLINE);
        contactListInteractor.unsubscribe();
    }

    @Override
    public void onResume() {
        contactListInteractor.changeConnectionStatus(User.ONLINE);
        contactListInteractor.subscribe();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unRegister(this);
        contactListInteractor.destroyListener();
        view = null;
    }

    @Override
    public void signOff() {
        contactListInteractor.changeConnectionStatus(User.OFFLINE);
        contactListInteractor.unsubscribe();
        contactListInteractor.destroyListener();
        contactListInteractor.signOff();
    }

    @Override
    public String getCurrentUserMail() {
        return contactListInteractor.getCurrentUserMail();
    }

    @Override
    public void removeContact(String mail) {
        contactListInteractor.removeContact(mail);
    }

    @Subscribe
    @Override
    public void onEventMainThread(ContactListEvent contactListEvent) {
        User user = contactListEvent.getUser();

        switch ( contactListEvent.getEventType() ) {
            case ContactListEvent.onContactAdded:
                onContactAdded(user);
                break;
            case ContactListEvent.onContactChanged:
                onContactChanged(user);
                break;
            case ContactListEvent.onContactRemoved:
                onContactRemoved(user);
                break;
        }
    }

    private void onContactAdded(User user) {
        if ( view != null ) {
            view.onContactAdded(user);
        }
    }

    private void onContactChanged(User user) {
        if ( view != null ) {
            view.onContactChanged(user);
        }
    }

    private void onContactRemoved(User user) {
        if ( view != null ) {
            view.onContactRemoved(user);
        }
    }
}
