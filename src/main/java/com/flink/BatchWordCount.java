package com.flink;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;

import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;



/**
 * @Auther ChenShuHong
 *@Date 2022-03-25 10:59
 *
 * flink 的数据处理 分为两块，  dataSet （批处理）   dataStream （流处理）  现在dataSet已经被弃用
 *
 *
 * 这样定义就是以批处理的方式运行，默认是用流处理  bin/flink  run -Dexecution.runtime-mode-BATCH  BatchWordCount.jar
 */
public class BatchWordCount {

  public static void main(String[] args) throws Exception {


  }

  /**
   * dataSetExample  批处理示例
   * @throws Exception
   */
  private static void dataSetExample() throws Exception {
    //1,创建执行环境
    ExecutionEnvironment env =ExecutionEnvironment.getExecutionEnvironment();

    //2,从文件读取数据
    DataSource<String> lineDataSource= env.readTextFile("input/words.txt");

    //3,,将每行数据进行分词，转换成二元组类型
    FlatMapOperator<String, Tuple2<String, Long>> wordAndOneTuple = lineDataSource.flatMap((String line, Collector<Tuple2<String, Long>> out) -> {
      //将一行文本进行分词
      String[] words = line.split(" ");
      //将每个单词转换成二元组输出
      for (String word : words) {
        out.collect(Tuple2.of(word, 1L));
      }
    }).returns(Types.TUPLE(Types.STRING, Types.LONG));


    //4,按照word进行分组
    UnsortedGrouping<Tuple2<String, Long>> wordAndOneGroup = wordAndOneTuple.groupBy(0);
    //5,分组内进行集合
    AggregateOperator<Tuple2<String, Long>> sum = wordAndOneGroup.sum(1);
    //6,打印数据
    sum.print();
  }

}
