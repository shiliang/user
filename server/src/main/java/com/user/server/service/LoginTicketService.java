package com.user.server.service;

import com.user.server.entity.LoginTicket;

public interface LoginTicketService {
    void addTicket(LoginTicket ticket);
    LoginTicket selectByTicket(String ticket);
    void updateStatus(String ticket, int status);
    String getTicketByUserId(String userId);

}
