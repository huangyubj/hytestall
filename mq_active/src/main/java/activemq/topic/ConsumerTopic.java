package activemq.topic;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerTopic {

    @JmsListener(destination="topic.test", containerFactory="JmsTopicListenerContainerFactory")
    public void comsumMessage(String msg){
        System.out.println("get message from topic.test: " + msg);
    }
}
