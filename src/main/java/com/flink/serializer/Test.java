package com.flink.serializer;

import com.flink.avroDAO.StockAvroBean;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.formats.avro.AvroDeserializationSchema;
import org.apache.flink.formats.avro.AvroSerializationSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.util.serialization.JSONKeyValueDeserializationSchema;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.Properties;

/**
 * @Auther ChenShuHong
 * @Date 2022-04-08 11:08
 */
public class Test {
  public static void main(String[] args) throws Exception {

    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();


  /*  DataStreamSource<ObjectNode> kafkaStream = env.addSource(new FlinkKafkaConsumer<>("clicks",  new JSONKeyValueDeserializationSchema(true),   AvroConsumer.initConfig())).setParallelism(5);

    kafkaStream.print();
    //用flink进行转换处理
    SingleOutputStreamOperator<StockAvroBean> result = kafkaStream.map((MapFunction<ObjectNode, StockAvroBean>) value -> {
      JsonNode data = value.get("value");
      long timestamp = data.get("timestamp").asLong();
      return new StockAvroBean( data.get("user").asText(),  data.get("url").asText(),timestamp);
    });


    AvroSerializationSchema<StockAvroBean> schemaSerialzation = AvroSerializationSchema.forSpecific(StockAvroBean.class);
    result.addSink( new FlinkKafkaProducer<>("events",schemaSerialzation, AvroProducer.initconfig()));


//{ "user": "nihao", "url": "/nihao", "timestamp": "1000" }

    AvroDeserializationSchema<StockAvroBean> schemaDeserialization = AvroDeserializationSchema.forSpecific(StockAvroBean.class);
    DataStreamSource<StockAvroBean> dataStreamSource = env.addSource(new FlinkKafkaConsumer<>("events", schemaDeserialization,  AvroProducer.initconfig())).setParallelism(5);
    dataStreamSource.print("序列号出来的数据");
    env.execute();*/
    DataStreamSource<ObjectNode> kafkaStream = env.addSource(new FlinkKafkaConsumer<>("clicks",  new JSONKeyValueDeserializationSchema(true),   AvroConsumer.initConfig())).setParallelism(5);

    kafkaStream.print();
    //用flink进行转换处理
    SingleOutputStreamOperator<StockAvroBean> result = kafkaStream.map((MapFunction<ObjectNode, StockAvroBean>) value -> {
      JsonNode data = value.get("value");
      long timestamp = data.get("timestamp").asLong();
      return new StockAvroBean( data.get("user").asText(),  data.get("url").asText(),timestamp);
    });



    // 创建kafka的生产者
  //   KafkaProducer<String, StockAvroBean> userBehaviorProducer = new KafkaProducer<String, StockAvroBean>(props);
    // 循环遍历数据

    //  ProducerRecord<String, StockAvroBean> producerRecord = new ProducerRecord<String, StockAvroBean>("UserBehaviorKafka", StockAvroBean);
    //  userBehaviorProducer.send(producerRecord);

    }



/*    KafkaConsumer<String, StockAvroBean> avroBeanKafkaConsumer = new KafkaConsumer<>(AvroProducer.initconfig());
    avroBeanKafkaConsumer.subscribe(Arrays.asList("UserBehaviorKafka"));
    while (true) {
      ConsumerRecords<String, StockAvroBean> poll = avroBeanKafkaConsumer.poll(1000);
      for (ConsumerRecord<String, StockAvroBean> stringStockConsumerRecord : poll) {
        System.out.println(stringStockConsumerRecord.value());
      }
    }*/
   /* AvroSerializer<StockAvroBean> avroSerializer  =new AvroSerializer();
    result.addSink( new FlinkKafkaProducer<StockAvroBean>(AvroProducer.initconfig()));
    AvorDeserializer<StockAvroBean>  avorDeserializer = new AvorDeserializer();
    StockAvroBean event = avorDeserializer.deserialize("avro-kafka", events);*/
 // }
}
