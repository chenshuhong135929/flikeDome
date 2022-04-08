package com.flink.serializer;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;

/**
 * @Auther ChenShuHong
 * @Date 2022-04-08 10:25
 */
public class AvroConsumer {
  private static final String BOOTSTRAP_SERVER = "192.168.1.4:9092";


  public static Properties initConfig(){
    Properties config = new Properties();
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVER);
    config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);
    config.put(ConsumerConfig.GROUP_ID_CONFIG,"avro-group");
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AvorDeserializer.class.getName());
    return config;
  }
}
