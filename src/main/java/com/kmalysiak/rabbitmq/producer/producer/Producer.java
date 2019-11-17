package com.kmalysiak.rabbitmq.producer.producer;

import com.kmalysiak.rabbitmq.producer.ProducerApplication;
import com.kmalysiak.rabbitmq.producer.configuration.MessageConfiguration;
import com.kmalysiak.rabbitmq.producer.configuration.QueueConfiguration;
import com.kmalysiak.rabbitmq.producer.message.MessageFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Scope("prototype")
public class Producer extends Thread {

    private static Logger logger = LoggerFactory
            .getLogger(ProducerApplication.class);
    SimpleDateFormat dateFormat;
    @Autowired
    private MessageFactory messageFactory;
    @Autowired
    private QueueConfiguration queueConfiguration;
    @Autowired
    private MessageConfiguration messageConfiguration;
    private ConnectionFactory factory = new ConnectionFactory();
    @Setter
    private int threadId;

    @PostConstruct
    private void initPayload() {
        logger.info("Started thread: " + threadId);
        dateFormat = new SimpleDateFormat(messageConfiguration.getDateFormat());
    }

    @Override
    public void run() {
        factory.setHost(queueConfiguration.getHost());

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueConfiguration.getName(), false, false, false, null);
            for (int i = 0; i < messageConfiguration.getCount(); i++) {
                channel.basicPublish("", queueConfiguration.getName(), null, messageFactory.getMessage());
                logger.info(" [x] Sent message from thread " + threadId + "' at:" + dateFormat.format(new Date(System.currentTimeMillis())));
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
}
