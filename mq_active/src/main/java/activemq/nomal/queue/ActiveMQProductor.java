package activemq.nomal.queue;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActiveMQProductor {
    /*默认连接用户名*/
    private static final String USERNAME
            = ActiveMQConnection.DEFAULT_USER;
    /* 默认连接密码*/
    private static final String PASSWORD
            = ActiveMQConnection.DEFAULT_PASSWORD;
    /* 默认连接地址*/
    private static final String BROKEURL
            = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final int SENDNUM = 3;

    public static final String QUEUENAME = "helloqueue";

    public static void main(String[] args) {
        ConnectionFactory factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD,
                BROKEURL);
        /* 连接*/
        Connection connection = null;
        /* 会话*/
        Session session;
        /* 消息的目的地*/
        Destination destination;
        /* 消息的生产者*/
        MessageProducer messageProducer;
        try{
            /* 通过连接工厂获取连接*/
            connection = factory.createConnection();
            /* 启动连接*/
            connection.start();
            /* 创建session
             * 第一个参数表示是否使用事务，第二次参数表示是否自动确认*/
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(QUEUENAME);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            /* 创建消息生产者*/
            messageProducer = session.createProducer(destination);
            for (int i = 0; i < SENDNUM; i++) {
                String msg = dateFormat.format(new Date()) + " 发送消息" + i ;
                TextMessage textMessage = session.createTextMessage(msg);
                textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 10000);
                textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 1000);
                textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 10);
                System.out.println("sendmessage:------" + msg);
                messageProducer.send(textMessage);
            }
            //开启事物后需要手动提交
            session.commit();
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
