package nomal;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KProducter {
    public static void main(String[] args) {
        Properties pro = new Properties();
        //服务地址
        pro.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        //key序列化方式
        pro.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //value序列化方式
        pro.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(pro);
        try {
            producer.send(new ProducerRecord<String, String>("hello_kafka", "testkey", "testvalue"));
            System.out.println("message send success");
        }finally {
            producer.close();
        }

    }
}
