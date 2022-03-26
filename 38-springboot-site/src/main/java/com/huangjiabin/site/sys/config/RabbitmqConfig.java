package com.huangjiabin.site.sys.config;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.huangjiabin.site.sys.model.EmailConstants;
import com.huangjiabin.site.sys.model.EmailLog;
import com.huangjiabin.site.sys.service.EmailLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitmqConfig {
    @Autowired
    private EmailLogService emailLogService;
    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;
    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        //消息确认回调，确认消息是否到达broker
        //data:消息唯一标识
        //ack:确认结果
        //cause：失败原因
        rabbitTemplate.setConfirmCallback((data, ack, cause) -> {
            String msgid = data.getId();
            if(ack){
                log.info("{}=========>消息发送成功",msgid);
                emailLogService.update(new UpdateWrapper<EmailLog>().set("status",1).eq("msdid",msgid));

            }else {
                log.info("{}=========>消息发送失败",msgid);
            }
        });
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                log.error("{}============>消息发送失败",returnedMessage.getMessage().getBody());
            }
        });
        return rabbitTemplate;
    }

    @Bean
    public Queue queue(){
        return new Queue(EmailConstants.EMAIL_QUEUE_NAME);
    }
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(EmailConstants.EMAIL_EXCHANGE_NAME);
    }
    @Bean
    public Binding  binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(EmailConstants.EMAIL_ROUTING_KEY_NAME);
    }

}
