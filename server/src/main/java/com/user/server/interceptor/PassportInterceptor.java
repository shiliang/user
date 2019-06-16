package com.user.server.interceptor;

import com.user.server.entity.LoginTicket;
import com.user.server.service.LoginTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/*
拦截看cookie中携带的ticket是否有效,在网关处的请求到用户服务验证通过才放行
 */

public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginTicketService loginTicketService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = null;
        String cookies = request.getHeader("Cookie");
        if (request.getCookies() != null) {
            for (Cookie cookie:
                 request.getCookies()) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

        if (ticket != null) {
            //查询ticket
            LoginTicket loginTicket = loginTicketService.selectByTicket(ticket);

            //判断ticket是否有效
            if (loginTicket == null ||
                loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
                return true;
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
