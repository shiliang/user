package com.user.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class SSOController {

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/validate")
    public boolean verifyToken(HttpServletRequest request) {
        String sessionId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("USERSESSIONID")) {
                    sessionId = cookie.getValue();
                }
            }
        }

        if (sessionId == null) {
            return false;
        } else {
            //去Redis里面判断是否过期,就看sessionId是否存在如果过期在数据库里面会被删了?
            //spring:session:sessions:sessionId
            String key = "spring:session:sessions:" + sessionId;
            if (redisTemplate.hasKey(key)) {
                return true;
            } else {
                return false;
            }
        }

    }
}
