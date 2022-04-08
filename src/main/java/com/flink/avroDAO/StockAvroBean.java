/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.flink.avroDAO;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class StockAvroBean extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -4967820120916587097L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"StockAvroBean\",\"namespace\":\"com.flink.avroDAO\",\"fields\":[{\"name\":\"user\",\"type\":\"string\"},{\"name\":\"url\",\"type\":\"string\"},{\"name\":\"timestamp\",\"type\":\"long\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<StockAvroBean> ENCODER =
      new BinaryMessageEncoder<StockAvroBean>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<StockAvroBean> DECODER =
      new BinaryMessageDecoder<StockAvroBean>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<StockAvroBean> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<StockAvroBean> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<StockAvroBean>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this StockAvroBean to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a StockAvroBean from a ByteBuffer. */
  public static StockAvroBean fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.CharSequence user;
  @Deprecated public java.lang.CharSequence url;
  @Deprecated public long timestamp;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public StockAvroBean() {}

  /**
   * All-args constructor.
   * @param user The new value for user
   * @param url The new value for url
   * @param timestamp The new value for timestamp
   */
  public StockAvroBean(java.lang.CharSequence user, java.lang.CharSequence url, java.lang.Long timestamp) {
    this.user = user;
    this.url = url;
    this.timestamp = timestamp;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return user;
    case 1: return url;
    case 2: return timestamp;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: user = (java.lang.CharSequence)value$; break;
    case 1: url = (java.lang.CharSequence)value$; break;
    case 2: timestamp = (java.lang.Long)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'user' field.
   * @return The value of the 'user' field.
   */
  public java.lang.CharSequence getUser() {
    return user;
  }

  /**
   * Sets the value of the 'user' field.
   * @param value the value to set.
   */
  public void setUser(java.lang.CharSequence value) {
    this.user = value;
  }

  /**
   * Gets the value of the 'url' field.
   * @return The value of the 'url' field.
   */
  public java.lang.CharSequence getUrl() {
    return url;
  }

  /**
   * Sets the value of the 'url' field.
   * @param value the value to set.
   */
  public void setUrl(java.lang.CharSequence value) {
    this.url = value;
  }

  /**
   * Gets the value of the 'timestamp' field.
   * @return The value of the 'timestamp' field.
   */
  public java.lang.Long getTimestamp() {
    return timestamp;
  }

  /**
   * Sets the value of the 'timestamp' field.
   * @param value the value to set.
   */
  public void setTimestamp(java.lang.Long value) {
    this.timestamp = value;
  }

  /**
   * Creates a new StockAvroBean RecordBuilder.
   * @return A new StockAvroBean RecordBuilder
   */
  public static com.flink.avroDAO.StockAvroBean.Builder newBuilder() {
    return new com.flink.avroDAO.StockAvroBean.Builder();
  }

  /**
   * Creates a new StockAvroBean RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new StockAvroBean RecordBuilder
   */
  public static com.flink.avroDAO.StockAvroBean.Builder newBuilder(com.flink.avroDAO.StockAvroBean.Builder other) {
    return new com.flink.avroDAO.StockAvroBean.Builder(other);
  }

  /**
   * Creates a new StockAvroBean RecordBuilder by copying an existing StockAvroBean instance.
   * @param other The existing instance to copy.
   * @return A new StockAvroBean RecordBuilder
   */
  public static com.flink.avroDAO.StockAvroBean.Builder newBuilder(com.flink.avroDAO.StockAvroBean other) {
    return new com.flink.avroDAO.StockAvroBean.Builder(other);
  }

  /**
   * RecordBuilder for StockAvroBean instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<StockAvroBean>
    implements org.apache.avro.data.RecordBuilder<StockAvroBean> {

    private java.lang.CharSequence user;
    private java.lang.CharSequence url;
    private long timestamp;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.flink.avroDAO.StockAvroBean.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.user)) {
        this.user = data().deepCopy(fields()[0].schema(), other.user);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.url)) {
        this.url = data().deepCopy(fields()[1].schema(), other.url);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.timestamp)) {
        this.timestamp = data().deepCopy(fields()[2].schema(), other.timestamp);
        fieldSetFlags()[2] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing StockAvroBean instance
     * @param other The existing instance to copy.
     */
    private Builder(com.flink.avroDAO.StockAvroBean other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.user)) {
        this.user = data().deepCopy(fields()[0].schema(), other.user);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.url)) {
        this.url = data().deepCopy(fields()[1].schema(), other.url);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.timestamp)) {
        this.timestamp = data().deepCopy(fields()[2].schema(), other.timestamp);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'user' field.
      * @return The value.
      */
    public java.lang.CharSequence getUser() {
      return user;
    }

    /**
      * Sets the value of the 'user' field.
      * @param value The value of 'user'.
      * @return This builder.
      */
    public com.flink.avroDAO.StockAvroBean.Builder setUser(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.user = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'user' field has been set.
      * @return True if the 'user' field has been set, false otherwise.
      */
    public boolean hasUser() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'user' field.
      * @return This builder.
      */
    public com.flink.avroDAO.StockAvroBean.Builder clearUser() {
      user = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'url' field.
      * @return The value.
      */
    public java.lang.CharSequence getUrl() {
      return url;
    }

    /**
      * Sets the value of the 'url' field.
      * @param value The value of 'url'.
      * @return This builder.
      */
    public com.flink.avroDAO.StockAvroBean.Builder setUrl(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.url = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'url' field has been set.
      * @return True if the 'url' field has been set, false otherwise.
      */
    public boolean hasUrl() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'url' field.
      * @return This builder.
      */
    public com.flink.avroDAO.StockAvroBean.Builder clearUrl() {
      url = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'timestamp' field.
      * @return The value.
      */
    public java.lang.Long getTimestamp() {
      return timestamp;
    }

    /**
      * Sets the value of the 'timestamp' field.
      * @param value The value of 'timestamp'.
      * @return This builder.
      */
    public com.flink.avroDAO.StockAvroBean.Builder setTimestamp(long value) {
      validate(fields()[2], value);
      this.timestamp = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'timestamp' field has been set.
      * @return True if the 'timestamp' field has been set, false otherwise.
      */
    public boolean hasTimestamp() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'timestamp' field.
      * @return This builder.
      */
    public com.flink.avroDAO.StockAvroBean.Builder clearTimestamp() {
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public StockAvroBean build() {
      try {
        StockAvroBean record = new StockAvroBean();
        record.user = fieldSetFlags()[0] ? this.user : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.url = fieldSetFlags()[1] ? this.url : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.timestamp = fieldSetFlags()[2] ? this.timestamp : (java.lang.Long) defaultValue(fields()[2]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<StockAvroBean>
    WRITER$ = (org.apache.avro.io.DatumWriter<StockAvroBean>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<StockAvroBean>
    READER$ = (org.apache.avro.io.DatumReader<StockAvroBean>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
