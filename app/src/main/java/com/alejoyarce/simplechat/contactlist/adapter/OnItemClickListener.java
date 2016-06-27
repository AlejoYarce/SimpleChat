package com.alejoyarce.simplechat.contactlist.adapter;

import com.alejoyarce.simplechat.login.domain.User;

public interface OnItemClickListener {

    void onItemClick(User user);
    void onItemLongClick(User user);
}
