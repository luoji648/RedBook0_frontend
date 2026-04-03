package com.zhiyan.redbookbackend.util;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class RedisIdWorker {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final long BEGIN_TIME = 1735689600L;

    private static final int COUNT_BITS = 32;

    public long nextId(String keyPrefix) {
        // 1.生成时间戳
        long nowSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        long timestamp = nowSecond - BEGIN_TIME;

        // 2.生成序列号
        // 2.1. 获取当前日期，精确到天
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 2.2. 自增长
        long count = stringRedisTemplate.opsForValue().increment("icr" + keyPrefix + ":" + date);

        // 3，拼接并返回
        return timestamp << COUNT_BITS | count;
    }

}
