package com.flink.chapter05;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Auther ChenShuHong
 * @Date 2022-04-01 16:55
 */
public class SourceCustomTest {

  public static void main(String[] args) throws Exception {
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    //DataStreamSource<Event> custom = env.addSource(new ClickSource());
    DataStreamSource<Integer> custom = env.addSource(new ParallelCustomSource()).setParallelism(4);
    custom.print();
    env.execute();
  }

}
