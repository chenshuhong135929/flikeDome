package com.flink.chapter05;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeutils.base.ByteSerializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumerBase;
import org.apache.flink.streaming.connectors.kafka.shuffle.FlinkKafkaShuffleConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Auther ChenShuHong
 * @Date 2022-04-01 14:14
 */
public class SourceTest {

  public static void main(String[] args) throws Exception {
    //创建执行环境
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.setParallelism(1);
    //1，从文件中读取数据
    DataStreamSource<String> stream1 = env.readTextFile("input/clicks.txt");

    //2,可以从集合中读取数据
    ArrayList<Integer> nums= new ArrayList<>();
    nums.add(1);
    nums.add(5);
    DataStreamSource<Integer> numStream  = env.fromCollection(nums);

    List<Event> events = new ArrayList<>();
    events.add(new Event("Mary","./home",1000));
    events.add(new Event("bob","./home",1000));

    DataStreamSource<Event> stream2  = env.fromCollection(events);


    DataStreamSource<Event> stream3 = env.fromElements(new Event("Mary", "./cart", 1000), new Event("bob", "./cart", 1000));


    //4,从socket文本流中读取数据
    DataStreamSource<String> stream4 = env.socketTextStream("localhost", 7777);
/*
    stream1.print("1");
    stream2.print("2");
    stream3.print("3");
    numStream.print("4");
    stream4.print("5");*/


    /**
     * docker run -d --name zookeeper -p 2181:2181 -v /etc/localtime:/etc/localtime wurstmeister/zookeeper
     * docker run -d --name kafka \
     * -p 9092:9092 \
     * -e KAFKA_BROKER_ID=0 \
     * -e KAFKA_ZOOKEEPER_CONNECT=192.168.1.4:2181 \
     * -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.1.4:9092 \
     * -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 wurstmeister/kafka
     *
     *
     *
     * 说明
     * -e KAFKA_BROKER_ID=0 在kafka集群中，每个kafka都有一个BROKER_ID来区分自己
     *
     * -e KAFKA_ZOOKEEPER_CONNECT=10.9.44.11:2181/kafka 配置zookeeper管理kafka的路径10.9.44.11:2181/kafka
     *
     * -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://10.9.44.11:9092 把kafka的地址端口注册给zookeeper
     *
     * -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 配置kafka的监听端口
     *
     * -v /etc/localtime:/etc/localtime 容器时间同步虚拟机的时间
     *
     * #进入容器
     * docker exec -it ${CONTAINER ID} /bin/bash
     * cd opt/bin
     * #单机方式：创建一个主题
     * bin/kafka-topics.sh --create --zookeeper 192.168.1.4:2181 --replication-factor 1 --partitions 1 --topic mykafka
     * #运行一个生产者
     * bin/kafka-console-producer.sh --broker-list 192.168.1.4:9092 --topic mykafka
     * #运行一个消费者
     * bin/kafka-console-consumer.sh  --bootstrap-server kafka:9092  --from-beginning --topic mykafka
     */




    //5，从kafka中读取数据

    Properties properties = new Properties();
    properties.setProperty("bootstrap.servers","47.112.186.155:9092");
    properties.setProperty("group.id","consumer-group");
//"clicks",new SimpleStringSchema(),properties
    DataStreamSource<String> clicks = env.addSource(new FlinkKafkaConsumer<>("clicks", new SimpleStringSchema(),  properties)).setParallelism(5);

    clicks.print();
    env.execute();


  }

}
