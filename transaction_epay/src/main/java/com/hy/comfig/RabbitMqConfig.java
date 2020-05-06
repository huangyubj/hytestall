package com.hy.comfig;

import com.hy.constan.CommonStatusConstant;
import com.hy.listenner.EpayMessageListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Value;
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

        @Value("${spring.rabbitmq.host}")
        private String addresses;

        @Value("${spring.rabbitmq.port}")
        private String port;

        @Value("${spring.rabbitmq.username}")
        private String username;

        @Value("${spring.rabbitmq.password}")
        private String password;

        @Value("${spring.rabbitmq.virtual-host}")
        private String virtualHost;

        @Value("${spring.rabbitmq.publisher-confirms}")
        private boolean publisherConfirms;

        @Bean
        public ConnectionFactory connectionFactory() {

            CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
            connectionFactory.setAddresses(addresses+":"+port);
            connectionFactory.setUsername(username);
            connectionFactory.setPassword(password);
            connectionFactory.setVirtualHost(virtualHost);
            /** 如果要进行消息回调，则这里必须要设置为true */
            connectionFactory.setPublisherConfirms(publisherConfirms);
            return connectionFactory;
        }

        @Bean
        public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
            return new RabbitAdmin(connectionFactory);
        }

        @Bean("messageQueueSend")
        public Queue messageQueueSend(){
            Queue queue = new Queue(CommonStatusConstant.MESSAGE_QUEUE_SEND);
            return queue;
        }

        @Bean
        public Binding sendBindingBuilder(){
            return BindingBuilder.bind(messageQueueSend()).to(exchange()).with(CommonStatusConstant.MESSAGE_QUEUE_SEND);
        }

        @Bean("messageQueueReceive")
        public Queue messageQueueReceive(){
            Queue queue = new Queue(CommonStatusConstant.MESSAGE_QUEUE_RECEIVE);
            return queue;
        }

        @Bean
        public Binding receiveBindingBuilder(){
            return BindingBuilder.bind(messageQueueReceive()).to(exchange()).with(CommonStatusConstant.MESSAGE_QUEUE_RECEIVE);
        }
        @Bean
        public DirectExchange exchange() {
        return new DirectExchange(CommonStatusConstant.MESSAGE_EXCHANGE);
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
                        System.out.println("消息确认失败,"+cause);
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
            messageListenerContainer.setQueues(new Queue(CommonStatusConstant.MESSAGE_QUEUE_RECEIVE));
            messageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);//手动确认
            messageListenerContainer.setMessageListener(new EpayMessageListener());
            messageListenerContainer.start();
            return messageListenerContainer;
        }
}
