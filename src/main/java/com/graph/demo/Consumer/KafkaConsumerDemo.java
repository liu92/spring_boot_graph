package com.graph.demo.Consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @ClassName KafkaConsumerDemo
 * @Description TODO
 * @Author liuwanlin
 * @Date 2019/7/10 16:54
 **/
public class KafkaConsumerDemo {
    public static void main(String[] args) {
        String topicName = "kafka_demo";
        Properties props = new Properties();
        props.put("bootstrap.servers","192.168.199.117:9092");
        props.put("group.id","kafka_demo");
        props.put("enable.auto.commit","true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(props);
        //Kafka Consumer subscribes list of topics here.
        consumer.subscribe(Arrays.asList(topicName));

        System.out.println("Subscribe to topic" + topicName);
        int i =0;
        while (true){
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records){


                // print the offset,key and value for the consumer records.
                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());

            }
        }

    }
}
