package serialization;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;


public class AvroProducer {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "1"); //all
        props.put("max.block.ms", "3000");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroSerializer");
        props.put("schema.registry.url", "host:port");

        Producer<String, Customer> producer = new KafkaProducer<>(props);

        for (int i = 0; i < 10; i++) {
            try {
                RecordMetadata record = producer.send(new ProducerRecord<>("test", new Customer(i, "name" + i))).get();
                System.out.printf("Producer Record:(partition=%d, offset=%d)\n", record.partition(), record.offset());
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        producer.close();
        System.out.println("Finished");

    }
}
