package com.flink.serializer;

import com.flink.avroDAO.StockAvroBean;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;

public class KafkaUtil {

    public static void kafkaProducer(String topic, StockAvroBean stockAvroBean) {


        KafkaProducer<String, StockAvroBean> kafkaProducer = new KafkaProducer<>(AvroProducer.initConfig());
        ProducerRecord<String,StockAvroBean> producerRecord=new ProducerRecord
                (       topic,
                        0,
                        "a",
                        stockAvroBean);
        kafkaProducer.send(producerRecord, (metadata, exception) -> {
            if (exception==null){
                System.out.println("消息的主题："+metadata.topic());
                System.out.println("消息的分区："+metadata.partition());
                System.out.println("消息的偏移量："+metadata.offset());
            }else {
                System.out.println("发送消息异常");
            }
        });

    }

    static void kafkaConsumer(String topic) {
        KafkaConsumer<String, StockAvroBean> avroBeanKafkaConsumer = new KafkaConsumer<>(AvroConsumer.initConfig());
        //消费者订阅主题
        avroBeanKafkaConsumer.subscribe(Arrays.asList(topic));


        while (true) {
            //批量拉取主题消息,每1秒拉取一次
            ConsumerRecords<String, StockAvroBean> poll = avroBeanKafkaConsumer.poll(1000);

            for (ConsumerRecord<String, StockAvroBean> record : poll) {
                System.out.println(record.topic() + "\t"
                        + record.partition() + "\t"
                        + record.offset() + "\t"
                        + record.value() + "\t"
                        + record.key() + "\t");
                System.out.println("自定义反序列化得到得数据=" + record.value().getUser());


            }
        }
    }
}
