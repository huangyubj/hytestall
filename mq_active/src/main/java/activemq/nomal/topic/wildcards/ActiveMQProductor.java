package activemq.nomal.topic.wildcards;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

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
    private static final int SENDNUM = 10;

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
            session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
            //适配符订阅
            // "." 用于作为路径上名字间的分隔符。
            // "*" 用于匹配路径上的任何名字。
            // ">" 用于递归地匹配任何以这个名字开始的destination。
            //批量队列发送
            destination = session.createQueue("topic://hy.test.p.s.test,topic://hy.test.p.redis.cache,topic://zs.aa.ss.redis.cache");
//            destination = session.createTopic("hy.test.p.redis.cache");
            messageProducer = session.createProducer(destination);
            for(int i=0;i<SENDNUM;i++){
                String msg = "vtgroup "+i+" "+System.currentTimeMillis();
                TextMessage message = session.createTextMessage(msg);
                System.out.println("发送消息:"+msg);
                messageProducer.send(message);
            }
            session.commit();
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
