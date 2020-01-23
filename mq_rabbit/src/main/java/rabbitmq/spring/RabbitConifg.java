package rabbitmq.spring;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rabbitmq.spring.common.ContainMessageListener;
import rabbitmq.spring.common.Producter;

/**
 * spring 使用rabbitmq(xml、config注册均一样)
 * 连接工厂、声明队列、交换器、template 模版发送消息、接收消息
 *
 */
@Configuration
@EnableRabbit
public class RabbitConifg {
    @Bean
    public ConnectionFactory getConnectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setVirtualHost("testhost");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        return factory;
    }

    @Bean
    public RabbitAdmin getRabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Queue getSpringcontainfactoryhello(){
        Queue queue = new Queue("springcontainfactoryhello");
        return queue;
    }
    @Bean
    public Queue getSpringQueuehello(){
        Queue queue = new Queue("springqueuehello");
        return queue;
    }

    @Bean
    public RabbitTemplate getRabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(getConnectionFactory());
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if(ack){
                    System.out.println("消息确认成功");
                }else{
                    System.out.println("消息确认失败,"+cause    );
                }
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
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
    public SimpleMessageListenerContainer getSimpleMessageListenerContainer(){
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(getConnectionFactory());
        messageListenerContainer.setQueues(new Queue(Producter.QUEUE_CONTAIN_FACTORY_TEST));
        messageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);//手动确认
        messageListenerContainer.setMessageListener(new ContainMessageListener());
        messageListenerContainer.start();
        return messageListenerContainer;
    }
}
