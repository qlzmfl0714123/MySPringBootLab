package com.rookies4.MySpringbootLab;

import com.rookies4.MySpringbootLab.config.MyPropProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MyPropProperties.class)
public class MySpringbootLabApplication {
    public static void main(String[] args) {
        SpringApplication.run(MySpringbootLabApplication.class, args);
    }
}
