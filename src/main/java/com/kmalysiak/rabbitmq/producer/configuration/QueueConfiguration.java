package com.kmalysiak.rabbitmq.producer.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "queue")
@Getter
@Setter
public class QueueConfiguration {
    String name;
    String host;
}
