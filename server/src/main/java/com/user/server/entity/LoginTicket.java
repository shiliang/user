package com.user.server.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class LoginTicket {

    @Id
    private String id;

    private String userId;

    private Date expired;

    private int status;// 0有效，1无效

    private String ticket;
}
