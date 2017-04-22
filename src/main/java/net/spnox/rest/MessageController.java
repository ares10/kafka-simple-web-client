package net.spnox.rest;

import net.spnox.storage.MessageBufferStorage;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    private MessageBufferStorage bufferRepo;

    @RequestMapping("/topic/{topic}/messages")
    public Object getMessages(@PathVariable(value="topic") String topic) {
        Object[] records = this.bufferRepo.getTopicBuffer(topic).toArray();
        ArrayUtils.reverse(records);
        return records;
    }
}
