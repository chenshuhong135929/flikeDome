package com.flink.serializer;

import com.flink.avroDAO.StockAvroBean;

public class KafkaProducerDemo {
    public static void main(String[] args) {
        KafkaUtil.kafkaProducer("avro-kafka",new StockAvroBean());
    }
}
