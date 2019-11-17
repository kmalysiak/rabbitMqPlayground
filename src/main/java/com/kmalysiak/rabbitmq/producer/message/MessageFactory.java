package com.kmalysiak.rabbitmq.producer.message;

import com.kmalysiak.rabbitmq.producer.configuration.MessageConfiguration;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MessageFactory {

    private static org.slf4j.Logger logger = LoggerFactory
            .getLogger(MessageFactory.class);

    @Autowired
    MessageConfiguration messageConfiguration;
    byte[] payload;
    SimpleDateFormat dateFormat;


    @PostConstruct
    private void initPayload() {
        logger.info("Init  payload size: " + messageConfiguration.getSize());
        payload = getPayload(messageConfiguration.getSize());
        dateFormat = new SimpleDateFormat(messageConfiguration.getDateFormat());
    }

    public byte[] getMessage() {
        return ArrayUtils.addAll(getFormattedDate(), payload);
    }


    private byte[] getPayload(int msgSize) {

        StringBuilder sb = new StringBuilder(msgSize);
        for (int i = 0; i < msgSize; i++) {
            sb.append('a');
        }
        try {
            return sb.toString().getBytes(messageConfiguration.getCharset());
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return new byte[]{};
        }

    }

    private byte[] getFormattedDate() {

        try {
            return dateFormat.format(new Date(System.currentTimeMillis())).getBytes(messageConfiguration.getCharset());
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return new byte[]{};
        }

    }
}
