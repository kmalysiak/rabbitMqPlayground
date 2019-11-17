package com.kmalysiak.rabbitmq.producer;


import com.kmalysiak.rabbitmq.producer.configuration.AppConfiguration;
import com.kmalysiak.rabbitmq.producer.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {

    private static Logger logger = LoggerFactory
            .getLogger(ProducerApplication.class);

    @Autowired
    ApplicationContext ctx;

    @Autowired
    AppConfiguration appConfiguration;

    public static void main(String[] args) {
        new SpringApplicationBuilder(ProducerApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }


    @Override
    public void run(String... args) {
        logger.info("Starting ...");
        for (int i = 0; i < appConfiguration.getThreadsCount(); i++) {
            Producer producer = (Producer) ctx.getBean("producer");
            producer.setThreadId(i);
            producer.start();
        }
    }
}

