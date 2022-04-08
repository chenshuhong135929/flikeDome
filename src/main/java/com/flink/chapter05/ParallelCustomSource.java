package com.flink.chapter05;

import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;

import java.util.Random;


/**
 * @Auther ChenShuHong
 * @Date 2022-04-01 17:40
 * 自定义并行的SourceFunction
 */
public class ParallelCustomSource implements ParallelSourceFunction<Integer> {


  private boolean running =true;



  @Override
  public void run(SourceContext<Integer> sourceContext) throws Exception {

    while (running){
      sourceContext.collect(new Random().nextInt(100));
      Thread.sleep(1000l);
    }

  }

  @Override
  public void cancel() {

    running=false;

  }
}
