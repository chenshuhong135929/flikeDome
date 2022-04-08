package com.flink.chapter05;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Auther ChenShuHong
 * @Date 2022-04-02 14:11
 */
public class TransformReduceTest {
  public static void main(String[] args)throws Exception {
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.setParallelism(1);
    DataStreamSource<Event> stream = env.fromElements(    new Event("bob", "./3", 9000),
        new Event("Mary", "./4", 10000),
        new Event("Mary", "./cart", 1000),
        new Event("bob", "./1", 1000),
        new Event("bob", "./2", 8000));



      SingleOutputStreamOperator<Tuple2<String, Long>> returns = stream.map((MapFunction<Event, Tuple2<String, Long>>) value -> Tuple2.of(value.getUser(), 1l)).returns(new TypeHint<Tuple2<String, Long>>() {
      @Override
      public TypeInformation<Tuple2<String, Long>> getTypeInfo() {
        return super.getTypeInfo();
      }
    });

    SingleOutputStreamOperator<Tuple2<String, Long>> clicksByUser = returns.keyBy((x) -> x.f0).reduce((ReduceFunction<Tuple2<String, Long>>) (value1, value2) -> Tuple2.of(value1.f0, value1.f1 + value2.f1));


    //,选取当前显示活跃的用户
    clicksByUser.keyBy(data->"key").reduce((ReduceFunction<Tuple2<String, Long>>) (value1, value2) -> value1.f1.compareTo(value2.f1)>0?value1:value2).print();

    env.execute();


  }

}
