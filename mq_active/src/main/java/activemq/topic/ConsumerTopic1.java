package activemq.topic;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerTopic1 {

    @JmsListener(destination="topic.test", containerFactory="JmsTopicListenerContainerFactory")
    public void comsumMessage(String msg){
        System.out.println("从 topic.test 消费消息: " + msg);
    }
}
