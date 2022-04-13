package com.flink.serializer;

import com.flink.avroDAO.StockAvroBean;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
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
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Arrays;
import java.util.Properties;

/**
 * @Auther ChenShuHong
 * @Date 2022-04-08 11:08
 * 单机方式：创建一个主题
 *      kafka-topics.sh --create --zookeeper 192.168.1.4:2181 --replication-factor 1 --partitions 1 --topic mykafka
 *      #运行一个生产者
 *      kafka-console-producer.sh --broker-list 192.168.1.4:9092 --topic mykafka
 *      #运行一个消费者
 *      kafka-console-consumer.sh  --bootstrap-server kafka:9092  --from-beginning --topic mykafka
 */
public class Test {
  public static void main(String[] args) throws Exception {

    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    Properties properties = new Properties();
    properties.setProperty("bootstrap.servers","192.168.1.4:9092");
    properties.setProperty("group.id","consumer-group");
    DataStreamSource<ObjectNode> kafkaStream = env.addSource(new FlinkKafkaConsumer<>("clicks", new JSONKeyValueDeserializationSchema(true), properties)).setParallelism(5);

    kafkaStream.print();
    //用flink进行转换处理
    SingleOutputStreamOperator<StockAvroBean> result = kafkaStream.map((MapFunction<ObjectNode, StockAvroBean>) value -> {
      JsonNode data = value.get("value");
      long timestamp = data.get("timestamp").asLong();
       kafkaProducer("avro-kafka", new StockAvroBean(data.get("user").asText(), data.get("url").asText(), timestamp));
      return new StockAvroBean(data.get("user").asText(), data.get("url").asText(), timestamp);
    });


   // kafkaConsumer("avro-kafka");
    env.execute();
  }

  private static void kafkaProducer(String topic,StockAvroBean stockAvroBean) {


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
      ConsumerRecords<String,StockAvroBean> poll = avroBeanKafkaConsumer.poll(1000);

        for (ConsumerRecord<String, StockAvroBean> record : poll) {
          System.out.println(record.topic() + "\t"
                  + record.partition() + "\t"
                  + record.offset() + "\t"
                  + record.value() + "\t"
                  + record.key() + "\t");
          System.out.println("自定义反序列化得到得数据="+record.value().getUser());


        }
      }
  }




    // 创建kafka的生产者
  //   KafkaProducer<String, StockAvroBean> userBehaviorProducer = new KafkaProducer<String, StockAvroBean>(props);
    // 循环遍历数据

    //  ProducerRecord<String, StockAvroBean> producerRecord = new ProducerRecord<String, StockAvroBean>("UserBehaviorKafka", StockAvroBean);
    //  userBehaviorProducer.send(producerRecord);



  /**
   * json序列号
   * @throws Exception
   */
  private static void jsonSerializationTest() throws Exception {
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    DataStreamSource<ObjectNode> kafkaStream = env.addSource(new FlinkKafkaConsumer<>("clicks",  new JSONKeyValueDeserializationSchema(true),   AvroConsumer.initConfig())).setParallelism(5);
    kafkaStream.print();
    //用flink进行转换处理
    SingleOutputStreamOperator<StockAvroBean> result = kafkaStream.map((MapFunction<ObjectNode, StockAvroBean>) value -> {
      JsonNode data = value.get("value");
      long timestamp = data.get("timestamp").asLong();
      return new StockAvroBean( data.get("user").asText(),  data.get("url").asText(),timestamp);
    });

    AvroSerializationSchema<StockAvroBean> schemaSerialzation = AvroSerializationSchema.forSpecific(StockAvroBean.class);
    result.addSink( new FlinkKafkaProducer<>("events",schemaSerialzation, AvroProducer.initConfig()));


//{ "user": "nihao", "url": "/nihao", "timestamp": "1000" }

    AvroDeserializationSchema<StockAvroBean> schemaDeserialization = AvroDeserializationSchema.forSpecific(StockAvroBean.class);
    DataStreamSource<StockAvroBean> dataStreamSource = env.addSource(new FlinkKafkaConsumer<>("events", schemaDeserialization,  AvroProducer.initConfig())).setParallelism(5);
    dataStreamSource.print("序列号出来的数据");
    env.execute();
  }




}
