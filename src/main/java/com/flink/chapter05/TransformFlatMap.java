package com.flink.chapter05;

import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @Auther ChenShuHong
 * @Date 2022-04-02 10:40
 */
public class TransformFlatMap {

  public static void main(String[] args) throws  Exception{
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.setParallelism(1);
    DataStreamSource<Event> stream = env.fromElements(new Event("Mary", "./cart", 1000), new Event("bob", "./cart", 1000));
    SingleOutputStreamOperator<String> result = stream.flatMap((Event x, Collector<String> y) -> {
      y.collect(x.getUser());
      y.collect(x.getUrl());
    }).returns(new TypeHint<String>() {
      @Override
      public TypeInformation<String> getTypeInfo() {
        return super.getTypeInfo();
      }
    });
    result.print();
    env.execute();
  }
}
