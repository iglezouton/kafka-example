import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;


public class BasicProducer {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        Producer<Long, String> producer = new KafkaProducer<>(props, new LongSerializer(), new StringSerializer());

        for (int i = 0; i < 10; i++)
            producer.send(new ProducerRecord<>("test", 1L, Integer.toString(i)));

        producer.close();

    }
}
