package rabbitmq.nomal;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MainTest {
    public static void main(String[] args) throws IOException, TimeoutException {
        /**
         * direct 精准匹配，对于匹配符合的消费者都推送消息
         * fanout 广播式发送，所有消费者均能收到消息
         * topic "." 分割路由键，"*"匹配单个 "#"匹配N个
         *
         * durable:exchange是否是否持久化
         *
         * exclusive: 队列是否独占
         *
         *mandtory: publish消息失败后通知发送者
         */
//        testDirect();
//        testFanout();
        testTopic();
    }
    public static void testDirect(){
        final String exchangeName = "test1";
        final String routingKey = "error";
        final String queueName = "errorqueue";
        for (int i = 0; i < 1; i++) {
            new Consumer(exchangeName, queueName, routingKey, BuiltinExchangeType.DIRECT).start();
        }
        new Producter(exchangeName, routingKey, BuiltinExchangeType.DIRECT).start();
    }
    public static void testFanout(){
        final String exchangeName = "testfanout";
        final String routingKey = "errorfanout";
        final String queueName = "errorqueuefanout";
        for (int i = 0; i < 3; i++) {
            new Consumer(exchangeName, queueName+i, routingKey, BuiltinExchangeType.FANOUT).start();
        }
        new Producter(exchangeName, routingKey, BuiltinExchangeType.FANOUT).start();
    }
    public static void testTopic(){
        final String exchangeName = "testtopic";
        final String routingKey = "errortopic.test";
        final String queueName = "errorqueuetopic";
        new Consumer(exchangeName, queueName, routingKey+".*", BuiltinExchangeType.TOPIC).start();
        new Consumer(exchangeName, queueName, routingKey+".#", BuiltinExchangeType.TOPIC).start();
        new Consumer(exchangeName+ "_direct", queueName, routingKey+".0", BuiltinExchangeType.DIRECT).start();
        for (int i = 0; i < 3; i++) {
            new Producter(exchangeName, routingKey +"."+i, BuiltinExchangeType.TOPIC).start();
        }
        for (int i = 0; i < 3; i++) {
            new Producter(exchangeName, routingKey +".second."+i, BuiltinExchangeType.TOPIC).start();
        }
    }
    static class Producter extends Thread{
        //交换器，路由键
        private String exchange,routingKey;
        private BuiltinExchangeType type;

        public Producter(String exchange, String routingKey, BuiltinExchangeType type){
            this.exchange = exchange;
            this.routingKey = routingKey;
            this.type = type;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                CommonTest c = new CommonTest(exchange, type,routingKey);
                for (int i = 0; i < 1; i++) {
                    String msg = Thread.currentThread().getName() + "--hello"+i;
                    c.sendMessage(msg);
                    System.out.println(Thread.currentThread().getName()+ "--->send message:" +msg);
                }
                c.destroy();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer extends Thread{
        //交换器，路由键
        private String exchange,routingKey,queuName;
        private BuiltinExchangeType type;

        public Consumer(String exchange, String queuName, String routingKey, BuiltinExchangeType type){
            this.exchange = exchange;
            this.queuName = queuName;
            this.routingKey = routingKey;
            this.type = type;
        }

        @Override
        public void run() {
            try {
                CommonTest c = new CommonTest(exchange, type,routingKey);
                c.setQueuName(queuName);
                c.consumMessage(new DefaultConsumer(c.getChannel()){
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        System.err.println(Thread.currentThread().getName()+"--> message: " + new String(body));
                    }
                });
                long time = System.currentTimeMillis();
                while (System.currentTimeMillis() - time < 3000){

                }
                c.destroy();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
