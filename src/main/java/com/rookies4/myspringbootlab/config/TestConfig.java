package com.rookies4.myspringbootlab.config;

import com.rookies4.myspringbootlab.env.MyEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {
    @Bean
    public MyEnvironment myEnvironment() {
        MyEnvironment env = new MyEnvironment();
        env.setMode("개발환경");
        return env;
    }
}