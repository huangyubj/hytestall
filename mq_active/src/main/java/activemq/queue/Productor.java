package activemq.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class Productor {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendQueueMessage(String destination, String message){
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText("this is jmsTemplate send a message: " + message);
                return textMessage;
            }
        });
//        jmsMessagingTemplate.convertAndSend(destination, "this is jmsMessagingTemplate send a message: " + message);
    }

    public void sendMessage(Destination destination, String message){
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText("jmsTemplate send: " + message);
                return textMessage;
            }
        });
//        jmsMessagingTemplate.convertAndSend(destination, "jmsMessagingTemplate send: " + message);
    }
}
