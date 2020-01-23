package activemq.queue;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer1 {

    @JmsListener(destination="queue.test")
    public void consumMessage(String msg){
        System.out.println("从 queue.test 消费消息: " + msg);
    }
}
