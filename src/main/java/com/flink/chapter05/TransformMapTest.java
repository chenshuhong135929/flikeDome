package com.flink.chapter05;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Auther ChenShuHong
 * @Date 2022-04-02 10:20
 */
public class TransformMapTest {
  public static void main(String[] args) throws Exception {
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.setParallelism(1);
    DataStreamSource<Event> stream = env.fromElements(new Event("Mary", "./cart", 1000), new Event("bob", "./cart", 1000));
    SingleOutputStreamOperator<String> result = stream.map(event -> event.getUser());
    result.print();
    env.execute();

  }

  //自定义map
  static class MyMap implements MapFunction <Event,String>{

    @Override
    public String map(Event event) throws Exception {
      return event.getUser();
    }
  }

}
