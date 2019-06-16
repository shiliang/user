package com.user.server.service.impl;

import com.user.server.entity.LoginTicket;
import com.user.server.mapper.LoginTicketMapper;
import com.user.server.service.LoginTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginTicketServiceImpl implements LoginTicketService {

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Override
    public void addTicket(LoginTicket ticket) {

    }

    @Override
    public LoginTicket selectByTicket(String ticket) {
        return loginTicketMapper.selectByTicket(ticket);
    }

    @Override
    public void updateStatus(String ticket, int status) {

    }

    @Override
    public String getTicketByUserId(String userId) {
        return null;
    }
}
