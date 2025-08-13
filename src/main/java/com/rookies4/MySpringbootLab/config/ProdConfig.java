package com.rookies4.MySpringbootLab.config;

import com.rookies4.MySpringbootLab.env.MyEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdConfig {
    @Bean
    public MyEnvironment myEnvironment() {
        MyEnvironment env = new MyEnvironment();
        env.setMode("운영환경");
        return env;
    }
}