package activemq.replyto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReplyProductor {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(String destination, final String msg){
        jmsTemplate.convertAndSend(destination, msg);
    }

    @JmsListener(destination = "out.reply.srping")
    public void replyListener(String msg){
        System.out.println("reply msg :" +msg);
    }
}
