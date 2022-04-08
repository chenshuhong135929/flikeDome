package com.flink.serializer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @Auther ChenShuHong
 * @Date 2022-04-08 10:26
 */
public class AvroProducer {
  private static final String BROKER_LIST = "192.168.1.4:9092";
  private String[] topics  ;

  public AvroProducer(String[] topics){
    this.topics = topics;
  }

  public static Properties initconfig(){
    Properties config = new Properties();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BROKER_LIST);//broker_list
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class.getName());
    config.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,AvroPartition.class.getName()); //自定义的分区准则
    return config;
  }
}
