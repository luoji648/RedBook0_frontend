package com.zhiyan.redbookbackend.util;

public class RabbitMqConstants {
    public static final String SECKILL_DIRECT_EXCHANGE = "seckill.direct";
    public static final String SECKILL_ORDER_KEY = "seckill.order";
    public static final String SECKILL_ORDER_QUEUE = "seckill.order.queue";

    public static final String ERROR_DIRECT_EXCHANGE = "error.direct";
    public static final String ERROR_QUEUE = "error.queue";
    public static final String ERROR_KEY = "error";
}
