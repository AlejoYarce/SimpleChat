package com.alejoyarce.simplechat.lib.impl;

import com.alejoyarce.simplechat.lib.EventBus;

public class EventBusImpl implements EventBus {

    org.greenrobot.eventbus.EventBus eventBus;

    public static EventBusImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public EventBusImpl() {
        this.eventBus = org.greenrobot.eventbus.EventBus.getDefault();
    }

    @Override
    public void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public void unRegister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }

    private static class SingletonHolder {
        private static final EventBusImpl INSTANCE = new EventBusImpl();
    }

}
