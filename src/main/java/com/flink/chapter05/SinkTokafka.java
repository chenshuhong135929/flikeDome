package com.flink.chapter05;

import com.flink.avroDAO.StockAvroBean;
import com.flink.serializer.AvroSerializer;
import org.apache.avro.Schema;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.formats.avro.AvroDeserializationSchema;
import org.apache.flink.formats.avro.AvroSerializationSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.flink.streaming.util.serialization.JSONKeyValueDeserializationSchema;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Properties;

/**
 * @Auther ChenShuHong
 * @Date 2022-04-07 11:05
 */
public class SinkTokafka {
  public static void main(String[] args) throws Exception {

    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    Properties properties = new Properties();
    properties.setProperty("bootstrap.servers","192.168.1.4:9092");
    properties.setProperty("group.id","consumer-group");
//"clicks",new SimpleStringSchema(),properties
    DataStreamSource<ObjectNode> kafkaStream = env.addSource(new FlinkKafkaConsumer<>("clicks",  new JSONKeyValueDeserializationSchema(true),  properties)).setParallelism(5);

    kafkaStream.print();
    //用flink进行转换处理
    SingleOutputStreamOperator<StockAvroBean> result = kafkaStream.map((MapFunction<ObjectNode, StockAvroBean>) value -> {
      JsonNode data = value.get("value");
      long timestamp = data.get("timestamp").asLong();
      return new StockAvroBean( data.get("user").asText(),  data.get("url").asText(),timestamp);
    });


    AvroSerializationSchema<StockAvroBean> schemaSerialzation = AvroSerializationSchema.forSpecific(StockAvroBean.class);
    result.addSink( new FlinkKafkaProducer<StockAvroBean>("events",schemaSerialzation, properties));

//{ "user": "nihao", "url": "/nihao", "timestamp": "1000" }

   AvroDeserializationSchema<StockAvroBean> schemaDeserialization = AvroDeserializationSchema.forSpecific(StockAvroBean.class);
    DataStreamSource<StockAvroBean> dataStreamSource = env.addSource(new FlinkKafkaConsumer<StockAvroBean>("events", schemaDeserialization,  properties)).setParallelism(5);
    dataStreamSource.print("序列号出来的数据");
    env.execute();
  }



  }
