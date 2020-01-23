package rabbitmq.spring.common;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producter {

    public static final String QUEUE_TEST = "springqueuehello";
    public static final String QUEUE_CONTAIN_FACTORY_TEST = "springcontainfactoryhello";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String hello_rabbitMQ){
        rabbitTemplate.convertAndSend(QUEUE_TEST, hello_rabbitMQ);
        rabbitTemplate.convertAndSend(QUEUE_CONTAIN_FACTORY_TEST, "containMessageListener-"+hello_rabbitMQ);
    }
}
