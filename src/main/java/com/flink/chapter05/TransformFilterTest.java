package com.flink.chapter05;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Auther ChenShuHong
 * @Date 2022-04-02 10:34
 */
public class TransformFilterTest {
  public static void main(String[] args) throws Exception {
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.setParallelism(1);
    DataStreamSource<Event> stream = env.fromElements(new Event("Mary", "./cart", 1000), new Event("bob", "./cart", 1000));
    SingleOutputStreamOperator<Event> result = stream.filter(event -> event.getUser().equals("Mary"));
    result.print();
    env.execute();

  }

  static class Myfilter implements FilterFunction <Event> {

    @Override
    public boolean filter(Event o) throws Exception {
      return o.getUser().equals("Mary");
    }
  }
}
