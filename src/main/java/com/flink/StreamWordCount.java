package com.flink;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * linux  socket 工具  命令 nc -lk  端口
 *
 *
 * flink 分为三部分  client (客户端)  jobManager（任务管理器）   TaskManger（执行）
 *
 *
 */
public class StreamWordCount {
  public static void main(String[] args) throws Exception {

    //1,创建流式执行环境
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    //从参数中提取主机名和端口号
    ParameterTool parameterTool = ParameterTool.fromArgs(args);
    String hostname = parameterTool.get("host");
    Integer port = parameterTool.getInt("port");

    //2,读取文本流
    DataStreamSource<String> lineDataStream = env.socketTextStream(hostname, port);
    //3,转换计算
    SingleOutputStreamOperator<Tuple2<String, Long>> wordAndOneTuple = lineDataStream.flatMap((String line, Collector<Tuple2<String, Long>> out) -> {
      String[] words = line.split(" ");
      for (String word : words) {
        out.collect(Tuple2.of(word, 1L));
      }
    }).returns(Types.TUPLE(Types.STRING, Types.LONG));

    //分组
    KeyedStream<Tuple2<String, Long>, String> wordAndOneKeyedStream = wordAndOneTuple.keyBy(data -> data.f0);
    //求和
    SingleOutputStreamOperator<Tuple2<String, Long>> sum = wordAndOneKeyedStream.sum(1);
    //打印
    sum.print();

    env.execute();

  }

}
