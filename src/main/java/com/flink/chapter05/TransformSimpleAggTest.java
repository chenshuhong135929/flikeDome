package com.flink.chapter05;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Auther ChenShuHong
 * @Date 2022-04-02 11:04
 */
public class TransformSimpleAggTest  {
  public static void main(String[] args)throws  Exception {
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.setParallelism(1);
    DataStreamSource<Event> stream = env.fromElements(    new Event("bob", "./3", 9000),
                                                          new Event("Mary", "./4", 10000),
                                                          new Event("Mary", "./cart", 1000),
                                                          new Event("bob", "./1", 1000),
                                                          new Event("bob", "./2", 8000));
    //提取按键分组之后进行聚合，提取当前最近访问的数据
    stream.keyBy((Event e) -> e.getUser()).max("timestamp").print("max");
    stream.keyBy((Event e) -> e.getUser()).maxBy("timestamp").print("maxBy");
    env.execute();
  }
}
