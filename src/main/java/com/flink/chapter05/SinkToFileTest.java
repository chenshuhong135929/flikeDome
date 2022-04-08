package com.flink.chapter05;

import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;

import java.util.concurrent.TimeUnit;

/**
 * @Auther ChenShuHong
 * @Date 2022-04-07 10:37
 */
public class SinkToFileTest {
  public static void main(String[] args) throws  Exception{
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.setParallelism(4);
    DataStreamSource<Event> stream = env.fromElements(    new Event("bob", "./3", 9000),
        new Event("Mary", "./4", 10000),
        new Event("Mary", "./cart", 1000),
        new Event("bob", "./1", 1000),
        new Event("bob", "./2", 8000));

    StreamingFileSink<String> streamingFileSink = StreamingFileSink.<String>forRowFormat(new Path("./output"), new SimpleStringEncoder<>("UTF-8"))
        .withRollingPolicy(DefaultRollingPolicy.builder()
            .withMaxPartSize(1024*1024*1024)
        .withRolloverInterval(TimeUnit.MINUTES.toMicros(15))
        .withInactivityInterval(TimeUnit.MINUTES.toMicros(5))
        .build()).build();

    stream.map(data->data.toString()).addSink(streamingFileSink);

   env.execute();
  }
}
