package com.user.server.controller;

import com.user.server.VO.ResultVO;
import com.user.server.constant.CookieConstant;
import com.user.server.constant.RedisConstant;
import com.user.server.entity.UserInfo;
import com.user.server.enums.ResultEnum;
import com.user.server.enums.RoleEnum;
import com.user.server.service.UserService;
import com.user.server.utils.CookieUtil;
import com.user.server.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //买家登录
    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam("openid") String openid,
                          @RequestParam(value = "username") String userName,
                          @RequestParam(value = "password") String passWord,
                          HttpServletResponse response) {

        //openid和数据库是否匹配
        UserInfo userInfo = userService.findByOpenid(openid);
        if (userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }

        //判断角色

        if (RoleEnum.BUYER.getCode() != userInfo.getRole()) {
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }

        //cookie里设置openid=abc
        CookieUtil.set(response, CookieConstant.OPENID, openid,
                CookieConstant.EXPIRE);
        return ResultVOUtil.success();


    }

    //卖家登录
    @GetMapping("/seller")
    public ResultVO seller(@RequestParam("openid") String openid,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        //判断是否已登录
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null &&
                !StringUtils.isEmpty(stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE,cookie.getValue())))) {
            return ResultVOUtil.success();
        }
        //openid和数据库是否匹配
        UserInfo userInfo = userService.findByOpenid(openid);
        if (userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }

        //判断角色

        if (RoleEnum.SELLER.getCode() != userInfo.getRole()) {
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }

        //redis设置key = UUID, value = xyz
        String token = UUID.randomUUID().toString();
        Integer expire = CookieConstant.EXPIRE;
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE,token),
                openid,
                expire,
                TimeUnit.SECONDS);

        //cookie里设置openid=abc
        CookieUtil.set(response, CookieConstant.TOKEN, token,
                CookieConstant.EXPIRE);
        return ResultVOUtil.success();
    }
}
