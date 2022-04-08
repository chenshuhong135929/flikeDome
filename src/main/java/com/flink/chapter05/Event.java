package com.flink.chapter05;

import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecord;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.flink.formats.avro.typeutils.AvroSerializer;

import java.sql.Timestamp;

/**
 * @Auther ChenShuHong
 * @Date 2022-04-01 14:10
 */

public class Event {
  private String user ;

  private String url ;

  long   timestamp;

  public Event() {

  }


  public Event(String user, String url, long timestamp) {
    this.user = user;
    this.url = url;
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "Event{" +
        "user='" + user + '\'' +
        ", url='" + url + '\'' +
        ", timestamp=" + new Timestamp(timestamp)  +
        '}';
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

}
