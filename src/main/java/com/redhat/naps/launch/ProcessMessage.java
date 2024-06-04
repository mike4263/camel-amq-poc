package com.redhat.naps.launch;

import ca.uhn.hl7v2.model.Message;
import org.apache.camel.Exchange;

public class ProcessMessage {

    @SuppressWarnings()
    public Message parseMessage(Message msg, Exchange e) {
        e.getIn().setHeader("queue", "ORM");
     //( ( (GenericComposite) ( (Varies) ((GenericSegment) ((GenericMessage.V23) msg).structures.get("MSH").get(0).getMessage().get("MSH")).fields.get(8).get(0) ).data ).components.get(0) )

        return msg;
    }
}
