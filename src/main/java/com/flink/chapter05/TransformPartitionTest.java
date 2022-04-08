package com.flink.chapter05;

import org.apache.flink.api.common.functions.Partitioner;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

/**
 * @Auther ChenShuHong
 * @Date 2022-04-02 17:15
 * 分区
 */
public class TransformPartitionTest {

  public static void main(String[] args) throws Exception {
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.setParallelism(1);
    DataStreamSource<Event> stream = env.fromElements(    new Event("bob", "./3", 9000),
        new Event("Mary", "./4", 10000),
        new Event("Mary", "./cart", 1000),
        new Event("bob", "./1", 1000),
        new Event("bob", "./2", 8000));

    //随机分区
 //   stream.shuffle().print().setParallelism(4);

    //轮询分区
  //  stream.rebalance().print().setParallelism(4);

    //rescale重缩放分区
    env.addSource(new RichParallelSourceFunction<Integer>() {
      @Override
      public void run(SourceContext<Integer> sourceContext) throws Exception {
        for(int i =0;i<8;i++){
          //将奇偶数分发送到0号和1号并行分区
          if(i % 2 == getRuntimeContext().getIndexOfThisSubtask()){
            sourceContext.collect(i);
          }

        }
      }

      @Override
      public void cancel() {

      }
    }).setParallelism(2).rescale().print().setParallelism(4);

    //广播
   // stream.broadcast().print().setParallelism(4);
    //全局分区
   // stream.global().print().setParallelism(4);
    //自定义分区
    env.fromElements(1, 2, 3, 4, 5, 6).partitionCustom((Partitioner<Integer>) (key, numPartitions) -> key & 2, (KeySelector<Integer, Integer>) value -> value).print().setParallelism(4);

    env.execute();


  }

}
