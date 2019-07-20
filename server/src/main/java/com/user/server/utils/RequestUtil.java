package com.user.server.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class RequestUtil {

    /**
     * 获取请求中的csessionid，如果有直接使用，
     * 没有的话创建一个csessionid，并保存到response,添加到浏览器
     * @param request
     * @param response  用于最后将cookie存到浏览器，所以需要response
     * @return
     */
    public static String getCSESSIONID(HttpServletRequest request, HttpServletResponse response) {
        // 1、获取Cookies (里面有jsession 购物车。。。)
        Cookie[] cookies = request.getCookies();
        if (null !=cookies && cookies.length>0) {
            for (Cookie cookie : cookies) {
                // 2、从 Cookie中获取CSESSIONID
                if (cookie.getName().equals("CSESSIONID")) {
                    // 3、如果有 直接使用
                    return cookie.getValue();
                }
            }
        }
        // 4、判断如果没有，创建一个CSESSIONID 保存CESSIONID到cookie,保存Cookie写回浏览器
        String csessionid = UUID.randomUUID().toString().replaceAll("-", "");
        Cookie cookie = new Cookie("CSESSIONID", csessionid);
        //设置路径
        cookie.setPath("/");
        //设置Cookie的存活时间     立即销毁0    关闭浏览器销毁-1      到时间了再消失>0   前提：没有清理Cookie
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        return csessionid;
    }
}
