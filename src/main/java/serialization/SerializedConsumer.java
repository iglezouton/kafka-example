package serialization;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import serialization.CustomerSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class SerializedConsumer {

    public static void main(String[] args) {


        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-1");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Create the consumer using props.
        Consumer<String, String> consumer = new KafkaConsumer<>(props);

        // Subscribe to the topic.
        consumer.subscribe(Collections.singletonList("test"));

        // Total number of messages to consume before stop
        final int giveUp = 100;
        int noRecordsCount = 0;

        while (true) {
            // Warning: Not error handling
            final ConsumerRecords<String, String> consumerRecords =
                    consumer.poll(Duration.ofSeconds(100));

            if (consumerRecords.count() == 0) {
                noRecordsCount++;
                if (noRecordsCount >= giveUp) break;
                else continue;
            }

            consumerRecords.forEach(record -> System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",
                    record.key(), record.value(),
                    record.partition(), record.offset()));

            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("Finished");

    }
}
