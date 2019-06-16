package com.user.server.service.impl;

import com.user.server.entity.LoginTicket;
import com.user.server.entity.UserInfo;
import com.user.server.repository.UserInfoRepository;
import com.user.server.service.LoginTicketService;
import com.user.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private LoginTicketService loginTicketService;

    @Override
    public UserInfo findByOpenid(String openid) {
        return userInfoRepository.findByOpenid(openid);
    }

    @Override
    public String addLoginTicket(String userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date now = new Date();
        now.setTime(3600*24*160 + now.getTime());
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketService.addTicket(loginTicket);
        return loginTicket.getTicket();
    }

    @Override
    public UserInfo getUserByName(String name) {
        return null;
    }
}
