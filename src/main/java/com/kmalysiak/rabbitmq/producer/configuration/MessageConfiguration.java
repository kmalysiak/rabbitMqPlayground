package com.kmalysiak.rabbitmq.producer.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "message")
@Getter
@Setter
public class MessageConfiguration {
    Integer count;
    Integer size;
    String charset;
    String dateFormat;
}