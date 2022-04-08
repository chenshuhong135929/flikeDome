package com.flink.chapter05;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Calendar;
import java.util.Random;

/**
 * @Auther ChenShuHong
 * @Date 2022-04-01 16:56
 */
public class ClickSource implements SourceFunction<Event> {

  private  Boolean runing = true;

  @Override
  public void run(SourceContext<Event> sourceContext) throws Exception {

    Random random = new Random();
    //随机生成数据
    String[] users = {"Mary","Alice","Cary"};
    String[] urls ={"./home","./cart",".fav","./prod?id=100",".prod?id=10"};



    //声明生成数据
    while(true){
        sourceContext.collect(new Event(users[random.nextInt(2)],urls[random.nextInt(4)], Calendar.getInstance().getTimeInMillis()));
        Thread.sleep(1000L);
    }



  }

  @Override
  public void cancel() {
    runing=false;
  }
}
