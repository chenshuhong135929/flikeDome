package com.flink.serializer;
import com.flink.avroDAO.StockAvroBean;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.commons.lang3.StringUtils;

/**
 * @Auther ChenShuHong
 * @Date 2022-04-08 10:22
 */

public  enum TopicEnum {
    STOCK_AVOR("avro-kafka",new StockAvroBean()); //实例

    private String topic;
    private SpecificRecordBase record;

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public SpecificRecordBase getRecord() {
    return record;
  }

  public void setRecord(SpecificRecordBase record) {
    this.record = record;
  }
    private TopicEnum(String topic,SpecificRecordBase record){
      this.topic = topic;
      this.record = record;
    }

    public static TopicEnum getTopicEnum(String topicName){
      if (topicName.isEmpty()){
        return null;
      }

      for (TopicEnum topicEnum : values()){
        if (StringUtils.equalsIgnoreCase(topicEnum.getTopic(),topicName)){
          return topicEnum;
        }
      }
      return null;
    }
}
