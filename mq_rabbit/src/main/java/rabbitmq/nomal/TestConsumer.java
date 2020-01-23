package rabbitmq.nomal;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TestConsumer {
    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(5672);
        factory.setHost("127.0.0.1");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("testhost");
        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
//            channel.exchangeDeclare(TestProducter.EXCHANGE, BuiltinExchangeType.DIRECT);
            String queueName = "testqueue";
            channel.queueDeclare(queueName,false,false,false,null);
            String routingKey = "error";
            channel.queueBind(queueName, TestProducter.EXCHANGE, routingKey);
            System.out.println("waiting for message........");
            channel.basicConsume(queueName, new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body);
                    System.out.println("Received["+envelope.getRoutingKey()
                            +"]"+message);
                }
            });
            long time = System.currentTimeMillis();
            while (System.currentTimeMillis() - time < 3500){

            }
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
