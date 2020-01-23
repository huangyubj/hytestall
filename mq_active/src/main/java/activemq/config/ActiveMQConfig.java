package activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class ActiveMQConfig {
//    @Value("${spring.activemq.user}")
    @Value("admin")
    private String username;

//    @Value("${spring.activemq.password}")
    @Value("admin")
    private String password;

//    @Value("${spring.activemq.broker-url}")
    @Value("tcp://localhost:61616")
    private String url;

    @Bean
    public ConnectionFactory getConnectionFactory(){
        return new ActiveMQConnectionFactory(username, password, url);
    }

    @Bean("JmsTopicListenerContainerFactory")
    public JmsListenerContainerFactory getJmsListenerContainerFactory(ConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAutoStartup(true);
        factory.setPubSubDomain(true);
        return factory;
    }

}
