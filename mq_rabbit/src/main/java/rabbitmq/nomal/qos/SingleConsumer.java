package rabbitmq.nomal.qos;

import com.rabbitmq.client.*;
import rabbitmq.nomal.TestProducter;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SingleConsumer {
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
            channel.basicConsume(queueName,false, new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body);
                    System.out.println("Received["+envelope.getRoutingKey()
                            +"]"+message);
                    this.getChannel().basicAck(envelope.getDeliveryTag(), false);
                }
            });
            long time = System.currentTimeMillis();
            while (System.currentTimeMillis() - time < 1500){

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
