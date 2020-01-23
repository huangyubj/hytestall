package activemq.replyto;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class ReplyConsumer {

    @JmsListener(destination = "consum.reply.test")
    @SendTo("out.reply.srping")
    public String consumMsg(String msg){
        System.out.println("0 收到消息：" + msg);
        return "0 回应 " + msg;
    }
}
