package rabbitmq.spring.common;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * 用来处理需要自定义的监听
 */
@Component
public class ContainMessageListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try{
            String msg = new String(message.getBody());
            System.out.println(msg+ "----消息");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            System.out.println("消息接收失败，拒绝确认，重新发送");
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            System.err.println(e.getStackTrace());
        }
    }
}
