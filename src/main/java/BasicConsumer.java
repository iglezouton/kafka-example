import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class BasicConsumer {

    public static void main(String[] args) {


        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        // This can also be added in the KafkaConsumer initialization
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Create the consumer using props.
        Consumer<Long, String> consumer = new KafkaConsumer<>(props);

        // Subscribe to the topic.
        consumer.subscribe(Collections.singletonList("test"));

        // Total number of messages to consume before stop
        final int giveUp = 100;
        int noRecordsCount = 0;

        while (true) {
            // Warning: Not error handling
            final ConsumerRecords<Long, String> consumerRecords =
                    consumer.poll(Duration.ofSeconds(1000));

            if (consumerRecords.count() == 0) {
                noRecordsCount++;
                if (noRecordsCount >= giveUp) break;
                else continue;
            }

            consumerRecords.forEach(record -> System.out.printf("Consumer Record:(K=%d, V=%s, partition=%d, offset=%d)\n",
                    record.key(), record.value(),
                    record.partition(), record.offset()));

            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("Finished");

    }
}
