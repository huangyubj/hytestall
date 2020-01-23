package rabbitmq.spring.common;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = Producter.QUEUE_TEST)
public class Comsumer {
    @RabbitHandler
    public void message(String msg){
        System.out.println(this.getClass().getName() + "--msg:" + msg);
    }
}
