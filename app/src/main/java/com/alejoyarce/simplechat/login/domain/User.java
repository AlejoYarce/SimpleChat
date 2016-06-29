package com.alejoyarce.simplechat.login.domain;

import java.util.Map;

public class User {

    private String email;
    private Boolean online;
    private Map<String, Boolean> contacts;

    public static final boolean ONLINE = true;
    public static final boolean OFFLINE = false;

    public User() { }

    public String getMail() {
        return email;
    }

    public void setMail(String email) {
        this.email = email;
    }

    public Boolean isOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Map<String, Boolean> getContacts() {
        return contacts;
    }

    public void setContacts(Map<String, Boolean> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object object) {
        boolean equal = false;

        if ( object instanceof User ) {
            User master = (User)object;
            equal = this.email.equals(master.getMail());
        }

        return equal;
    }

}
