package activemq.replyto;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class ReplyConsumer1 {

    @JmsListener(destination = "consum.reply.test")
    @SendTo("out.reply.srping")
    public String consumMsg(String msg){
        System.out.println("1 收到消息：" + msg);
        return "1 回应 " + msg;
    }
}
