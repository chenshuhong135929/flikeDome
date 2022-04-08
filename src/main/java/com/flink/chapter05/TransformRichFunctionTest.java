package com.flink.chapter05;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Auther ChenShuHong
 * @Date 2022-04-02 16:05
 */
public class TransformRichFunctionTest {
  public static void main(String[] args) throws Exception {
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.setParallelism(1);
    DataStreamSource<Event> stream = env.fromElements(    new Event("bob", "./3", 9000),
        new Event("Mary", "./4", 10000),
        new Event("Mary", "./cart", 1000),
        new Event("bob", "./1", 1000),
        new Event("bob", "./2", 8000));

    stream.map(new MyRichMapper()).print();
    env.execute();
  }

  //实现一个自定义的富函数类
  static  class  MyRichMapper extends RichMapFunction<Event,Integer>{
    @Override
    public void open(Configuration parameters) throws Exception {
      super.open(parameters);
      System.out.println("open生命周期被调用"+ getRuntimeContext().getIndexOfThisSubtask());
    }

    @Override
    public Integer map(Event value) throws Exception {
      return value.getUser().length();
    }

    @Override
    public void close() throws Exception {
      super.close();
      System.out.println("close生命周期被调用"+ getRuntimeContext().getIndexOfThisSubtask());

    }
  }

}
