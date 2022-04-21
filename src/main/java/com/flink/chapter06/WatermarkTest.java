package com.flink.chapter06;

import com.flink.chapter05.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.time.Duration;

public class WatermarkTest {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(4);
        env.getConfig().setAutoWatermarkInterval(100);
        DataStreamSource<Event> stream = env.fromElements(    new Event("bob", "./3", 9000),
                new Event("Mary", "./4", 10000),
                new Event("Mary", "./cart", 1000),
                new Event("bob", "./1", 1000),
                new Event("bob", "./2", 8000));
        stream
        //有序流的watermark生成
                  /*      .assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forMonotonousTimestamps()
                                .withTimestampAssigner((SerializableTimestampAssigner<Event>) (o, l) -> o.getTimestamp()))*/
                //乱序流Watermark生成
                        .assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ofSeconds(2)).
                                withTimestampAssigner((SerializableTimestampAssigner<Event>) (event, l) -> event.getTimestamp()));

        stream.print();

        env.execute();

    }

}
