package com.graph.demo.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * kafka测试 生产者
 * @ClassName KafkaProducer
 * @Description TODO
 * @Author liuwanlin
 * @Date 2019/7/10 16:32
 **/
public class KafkaProducerDemo {
    public static void main(String[] args) {
        String topicName = "kafka_demo";

        Properties props  = new Properties();
        // 设置配置信息
        // 指定序列化数据的对象，不然会导致序列化和传递的数据类型不一致
//        props.put("serializer.class","kafka.serializer.StringEncoder");
        // 指定kafka broker对应的主机，格式为 host:port,host:port,...
//        props.put("metadata.broker.list","192.168.199.117:9092");
        //Assign localhost id
        props .put("bootstrap.servers","192.168.199.117:9092");

        //Set acknowledgements for producer requests
        //所有follower都响应了才认为消息提交成功，即"committed"
        props.put("acks", "all");
        //If the request fails, the producer can automatically retry,
        //retries = MAX 无限重试，直到你意识到出现了问题:
        props.put("retries", 0);
        //Specify buffer size in config
        //producer将试图批处理消息记录，以减少请求次数.默认的批量处理消息字节数
        //batch.size当批量的数据大小达到设定值后，就会立即发送，不顾下面的linger.ms
        props.put("batch.size", 16384);
        //Reduce the no of requests less than 0
        //延迟1ms发送，这项设置将通过增加小的延迟来完成--即，不是立即发送一条记录，producer将会等待给定的延迟时间以允许其他消息记录发送，这些消息记录可以批量处理
        props.put("linger.ms", 1);
        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        //producer可以用来缓存数据的内存大小。
        props.put("buffer.memory", 33554432);
        //指定序列化
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        Producer<String,String> producer = new KafkaProducer<String, String>(props);

        for (int i =0; i<10; i++){
            producer.send(new ProducerRecord<String, String>(topicName, Integer.toString(i), Integer.toString(i)));
            System.out.println("message send successful....");
            producer.close();
        }
    }
}
