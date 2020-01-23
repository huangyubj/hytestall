package rabbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class MainApplication {


	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

}
