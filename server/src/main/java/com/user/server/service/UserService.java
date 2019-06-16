package com.user.server.service;

import com.user.server.entity.UserInfo;

public interface UserService {
    UserInfo findByOpenid(String openid);
    String addLoginTicket(String userId);
    UserInfo getUserByName(String name);
}
