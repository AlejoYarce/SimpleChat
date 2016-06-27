package com.alejoyarce.simplechat.contactlist.mvp;

import com.alejoyarce.simplechat.login.domain.User;

public interface ContactListView {

    void onContactAdded(User user);
    void onContactChanged(User user);
    void onContactRemoved(User user);
}
