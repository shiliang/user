package com.user.server.entity;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    private static ThreadLocal<UserInfo> users = new ThreadLocal<UserInfo>();

    public UserInfo getUser() {
        return users.get();
    }

    public void setUser(UserInfo user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}
