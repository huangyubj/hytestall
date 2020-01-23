package activemq.nomal.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMQConsumerC {
    /*默认连接用户名*/
    private static final String USERNAME
            = ActiveMQConnection.DEFAULT_USER;
    /* 默认连接密码*/
    private static final String PASSWORD
            = ActiveMQConnection.DEFAULT_PASSWORD;
    /* 默认连接地址*/
    private static final String BROKEURL
            = ActiveMQConnection.DEFAULT_BROKER_URL;
    public static void main(String[] args) {
        ConnectionFactory factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD,
                BROKEURL);
        /* 连接*/
        Connection connection = null;
        /* 会话*/
        Session session;
        /* 消息的目的地*/
        Destination destination;
        /* 消息的消费者*/
        MessageConsumer messageConsumer;
        try{
            /* 通过连接工厂获取连接*/
            connection = factory.createConnection();
            /* 启动连接*/
            connection.start();
            /* 创建session
             * 第一个参数表示是否使用事务，第二次参数表示是否自动确认*/
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("Consumer.C.VirtualTopic.vtgroup");
            /* 创建消息生产者*/
            messageConsumer = session.createConsumer(destination);
            messageConsumer.setMessageListener(message->{
                try {
                    System.out.println("Accept msg : " +((TextMessage)message).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });
            long time = System.currentTimeMillis();
            while (System.currentTimeMillis() - time < 500000){

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
