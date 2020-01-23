package activemq.queue;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @JmsListener(destination="queue.test")
    public void consumMessage(String msg){
        System.out.println("get message from queue.test: " + msg);
    }
}
