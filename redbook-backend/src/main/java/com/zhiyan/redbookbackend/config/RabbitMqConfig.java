package com.zhiyan.redbookbackend.config;

import com.zhiyan.redbookbackend.util.RabbitMqConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public MessageConverter messageConverter() {
        // 使用Jackson2JsonMessageConverter注入MessageConverter作为消息转换器
        Jackson2JsonMessageConverter jjmc = new Jackson2JsonMessageConverter();
        // 2.配置自动创建消息id，用于识别不同消息，也可以在业务中基于ID判断是否是重复消息
        jjmc.setCreateMessageIds(true);
        return jjmc;
    }
    // 定义错误队列，交换机，绑定关系
    @Bean
    public DirectExchange errorDirectExchange() {
        return new DirectExchange(RabbitMqConstants.ERROR_DIRECT_EXCHANGE);
    }
    @Bean
    public Queue errorQueue() {
        return QueueBuilder.durable(RabbitMqConstants.ERROR_QUEUE).lazy().build();
    }
    @Bean
    public Binding errorBinding(DirectExchange errorDirectExchange, Queue errorQueue) {
        return BindingBuilder.bind(errorQueue).to(errorDirectExchange).with(RabbitMqConstants.ERROR_KEY);
    }
    // 替代原来的失败处理策略
    @Bean
    public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate) {
        return new RepublishMessageRecoverer(rabbitTemplate, RabbitMqConstants.ERROR_DIRECT_EXCHANGE, RabbitMqConstants.ERROR_KEY);
    }
}
