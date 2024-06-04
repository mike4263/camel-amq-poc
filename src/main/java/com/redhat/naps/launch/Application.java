package com.redhat.naps.launch;

import ca.uhn.hl7v2.AcknowledgmentCode;
import ca.uhn.hl7v2.ErrorCode;
import ca.uhn.hl7v2.HL7Exception;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.hl7.HL7DataFormat;
import org.apache.camel.component.mllp.MllpInvalidMessageException;
import org.apache.camel.spi.DataFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.camel.component.hl7.HL7.ack;
import static org.apache.camel.component.hl7.HL7.messageConforms;


@ApplicationScoped
public class Application extends RouteBuilder {

    static SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    static String hl7MessageTemplate
            = "MSH|^~\\&|REQUESTING|ICE|INHOUSE|RTH00|<MESSAGE_TIMESTAMP>||ORM^O01|<MESSAGE_CONTROL_ID>|D|2.3|||AL|NE|||" + '\r'
            + "PID|1||ICE999999^^^ICE^ICE||Testpatient^Testy^^^Mr||19740401|M|||123 Barrel Drive^^^^SW18 4RT|||||2||||||||||||||"
            + '\r'
            + "NTE|1||Free text for entering clinical details|" + '\r'
            + "PV1|1||^^^^^^^^Admin Location|||||||||||||||NHS|" + '\r'
            + "ORC|NW|213||175|REQ||||20080808093202|ahsl^^Administrator||G999999^TestDoctor^GPtests^^^^^^NAT|^^^^^^^^Admin Location | 819600|200808080932||RTH00||ahsl^^Administrator||"
            + '\r'
            + "OBR|1|213||CCOR^Serum Cortisol ^ JRH06|||200808080932||0.100||||||^|G999999^TestDoctor^GPtests^^^^^^NAT|819600|ADM162||||||820|||^^^^^R||||||||"
            + '\r'
            + "OBR|2|213||GCU^Serum Copper ^ JRH06 |||200808080932||0.100||||||^|G999999^TestDoctor^GPtests^^^^^^NAT|819600|ADM162||||||820|||^^^^^R||||||||"
            + '\r'
            + "OBR|3|213||THYG^Serum Thyroglobulin ^JRH06|||200808080932||0.100||||||^|G999999^TestDoctor^GPtests^^^^^^NAT|819600|ADM162||||||820|||^^^^^R||||||||"
            + '\r'
            + '\n';

    public static String getHL7Message() {
        String tmpMessage = hl7MessageTemplate.replaceFirst("<MESSAGE_TIMESTAMP>", timestampFormat.format(new Date()));
        return tmpMessage.replaceFirst("<MESSAGE_CONTROL_ID>", String.format("%05d", 1));
    }

    @Override
    public void configure() throws Exception {
        DataFormat hl7 = new HL7DataFormat();

        //https://github.com/jeffkurian/hl7-camel-mllp-demo1/blob/master/src/main/java/com/example/demo/MyMllpRouter.java

        onException(HL7Exception.class)
                .log("Handling Exception ")
                .transform(ack(AcknowledgmentCode.AE,"Error tranforming he message", ErrorCode.UNSUPPORTED_MESSAGE_TYPE))
                .handled(true)
                .end()  ;

        // produces messages to kafka
        from("timer:foo?period={{timer.period}}&delay={{timer.delay}}")
                .routeId("FromTimer2Kafka")
                .setBody(simple(getHL7Message()))
                .to("kafka:{{kafka.topic.name}}")
                .log("Message correctly sent to the topic!");

        // kafka consumer
        from("kafka:{{kafka.topic.name}}")
                .routeId("FromKafka2MLLP")
                .log("Received message from topic")
                .to("mllp://8088")
                .log("Received ACK from MLLP");


        from("mllp://8088?autoAck=true")
                .routeId("MLLP Consumer")
                .log("MLLP Received message ")
                .unmarshal(hl7)
                .validate(messageConforms())
                .bean(ProcessMessage.class, "parseMessage")
                .choice()
                    .when(header("queue").isEqualTo("ORM"))
                        .log("Routing to ORM Queue")
                    .otherwise()
                        .throwException(MllpInvalidMessageException.class, "Message type not supported")
                    .end()
                .transform(ack(AcknowledgmentCode.AA))
                .end();
    }
}
