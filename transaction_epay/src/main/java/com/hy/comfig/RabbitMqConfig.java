package com.hy.comfig;

import com.hy.util.Constant;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * spring 使用rabbitmq(xml、config注册均一样)
 * 连接工厂、声明队列、交换器、template 模版发送消息、接收消息
 *
 */
@Configuration
@EnableRabbit
public class RabbitMqConfig {

        @Bean("messagePayQueue")
        public Queue messagePayQueue(){
            Queue queue = new Queue(Constant.MESSAGE_PAY_QUEUE);
            return queue;
        }

        @Bean
        public RabbitTemplate getRabbitTemplate(ConnectionFactory connectionFactory){
            RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
            rabbitTemplate.setMandatory(true);
            rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
                public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                    if(ack){
                        System.out.println("消息确认成功");
                    }else{
                        System.out.println("消息确认失败,"+cause    );
                    }
                }
            });
            rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
                public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                    String msg = new String(message.getBody());
                    System.err.println(msg+ "----路由失败");
                    System.out.println("replyCode="+replyCode);
                    System.out.println("replyText="+replyText);
                    System.out.println("exchange="+exchange);
                    System.out.println("routingKey="+routingKey);
                }
            });
            return rabbitTemplate;
        }

        /**
         * 注册自定义消息监听
         * @return
         */
        @Bean
        public SimpleMessageListenerContainer getSimpleMessageListenerContainer(ConnectionFactory connectionFactory){
            SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
            messageListenerContainer.setConnectionFactory(connectionFactory);
            messageListenerContainer.setQueues(new Queue("message_queue"));
            messageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);//手动确认
            messageListenerContainer.setMessageListener(new ContainMessageListener());
            messageListenerContainer.start();
            return messageListenerContainer;
        }
}
