import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;


public class BasicProducer {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "b-1.eg-training-kafka.d470cv.c3.kafka.eu-west-1.amazonaws.com:9092," +
                "b-2.eg-training-kafka.d470cv.c3.kafka.eu-west-1.amazonaws.com:9092");
        props.put("acks", "1"); //all
        props.put("max.block.ms", "3000");

        Producer<Long, String> producer = new KafkaProducer<>(props, new LongSerializer(), new StringSerializer());

        for (int i = 0; i < 10; i++) {
            // Warning: We're not using the returned Future (Fire-and-forget)
            //producer.send(new ProducerRecord<>("nacho.gonzalez", 1L, Integer.toString(i)));
            // Synchronous send
            try {
                RecordMetadata record = producer.send(new ProducerRecord<>("nacho.gonzalez", 1L, Integer.toString(i))).get();
                System.out.printf("Producer Record:(partition=%d, offset=%d)\n", record.partition(), record.offset());
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            // Asynchronous send
        }
        producer.close();
        System.out.println("Finished");

    }
}
