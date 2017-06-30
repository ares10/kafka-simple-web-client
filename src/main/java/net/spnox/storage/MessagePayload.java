package net.spnox.storage;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class MessagePayload {
    private String messageKey;
    private String topic;
    private long offset;
    private int partition;
    @JsonRawValue
    private Object data;

    public MessagePayload(String messageKey, String topic, long offset, int partition, Object data) {
        setMessageKey(messageKey);
        setTopic(topic);
        setOffset(offset);
        setPartition(partition);
        setData(data);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
}
