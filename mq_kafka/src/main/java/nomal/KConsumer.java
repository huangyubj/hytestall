package nomal;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Collections;
import java.util.Properties;

public class KConsumer {
    public static void main(String[] args) {
        Properties pro = new Properties();
        pro.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        pro.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        pro.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        pro.put(ConsumerConfig.GROUP_ID_CONFIG, "test1");
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(pro);
        try {
            consumer.subscribe(Collections.singletonList("hello_kafka"));
            while (true){
                ConsumerRecords<String, String> records = consumer.poll(500);
                for (ConsumerRecord<String, String> record: records) {
                    System.out.println("headers->" + record.headers());;
                    System.out.println("key->" + record.key());;
                    System.out.println("value->" + record.value());
                    System.out.println("offset->" + record.offset());
                    System.out.println("topic->" + record.topic());
                    System.out.println("timestamp->" + record.timestamp());
                    System.out.println("serializedKeySize->" + record.serializedKeySize());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            consumer.close();
        }
    }
}
