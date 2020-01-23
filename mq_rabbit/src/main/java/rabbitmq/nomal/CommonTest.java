package rabbitmq.nomal;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class CommonTest {
    //交换器，路由键
    private String exchange,routingKey,queuName;
    private BuiltinExchangeType exchangeType;
    //主机、端口、虚拟主机、名称、密码
    private String host,virtualHost,username,password;
    private int port;
    private boolean autoDel = true;
    //发送失败返回
    private boolean mandatory = false;

    private  Connection connection = null;
    private Channel channel = null;

    public CommonTest(){
        this.host="127.0.0.1";
        this.port=5672;
        this.virtualHost="testhost";
        this.username="admin";
        this.password="admin";
    }

    public CommonTest(String exchange, BuiltinExchangeType exchangeType, String routingKey) throws IOException, TimeoutException {
        this();
        this.exchange = exchange;
        this.exchangeType = exchangeType;
        this.routingKey = routingKey;
        init();
    }

    public void init() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setVirtualHost(virtualHost);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        connection = factory.newConnection();
        channel = connection.createChannel();
    }
    public void sendMessage(String msg) throws IOException {
        channel.exchangeDeclare(exchange, exchangeType, false, autoDel, null);
        channel.basicPublish(exchange, routingKey, mandatory,false, null, msg.getBytes());
    }
    public void consumMessage(Consumer consumer) throws IOException {
        channel.exchangeDeclare(exchange, exchangeType, false, autoDel, null);
        channel.queueDeclare(queuName,false,false, autoDel, null);
        channel.queueBind(queuName, exchange, routingKey, null);
        System.out.println("waiting for message........");
        channel.basicConsume(queuName, consumer);
    }

    public void destroy(){
        try {
            if(channel != null)
                channel.close();
            if(connection != null)
                connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void setQueuName(String queuName) {
        this.queuName = queuName;
    }

    public Connection getConnection() {
        return connection;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setExchangeType(BuiltinExchangeType exchangeType) {
        this.exchangeType = exchangeType;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
