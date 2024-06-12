package com.redhat.naps.launch;

import ca.uhn.hl7v2.model.Message;
import org.apache.camel.Exchange;

public class ProcessMessage {
    @SuppressWarnings(value = "Used in Camel Route")
    public Message parseMessage(Message msg, Exchange e) {
        e.getIn().setHeader("queue", "ORM");
        return msg;
    }
}
