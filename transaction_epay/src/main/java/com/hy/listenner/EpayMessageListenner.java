package com.hy.listenner;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听余额宝发来的金额消费信息
 */
@Component
//@RabbitListener(queues = Producter.QUEUE_TEST)
public class EpayMessageListenner {
    @RabbitHandler
    public void message(String msg){
        System.out.println(this.getClass().getName() + "--msg:" + msg);
    }
}
