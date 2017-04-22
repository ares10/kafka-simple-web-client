package net.spnox.storage;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class MessageBufferStorage {
    private Map<String, Buffer> topicData;

    @Value("${client.buffer-size}")
    private int bufferSize;

    public MessageBufferStorage() {
        this.topicData = Collections.synchronizedMap(new HashMap<>());
    }

    public void addTopic(String topicName) {
        this.topicData.put(topicName,
                BufferUtils.synchronizedBuffer(new CircularFifoBuffer(bufferSize)));
    }

    public void removeTopic(String topicName) {
        this.topicData.remove(topicName);
    }

    public Buffer getTopicBuffer(String topicName) {
        if (!this.topicData.containsKey(topicName)) {
            addTopic(topicName);
        }

        return topicData.get(topicName);
    }

}
