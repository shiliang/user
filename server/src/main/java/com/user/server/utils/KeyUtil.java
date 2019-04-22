package com.user.server.utils;

import java.util.Random;

public class KeyUtil {
    /**
     * 生成唯一的主键,不适合分布式
     * 格式: 时间+随机数
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }
}
