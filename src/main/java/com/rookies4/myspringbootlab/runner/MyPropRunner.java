package com.rookies4.myspringbootlab.runner;

import com.rookies4.myspringbootlab.config.MyPropProperties;
import com.rookies4.myspringbootlab.env.MyEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyPropRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(MyPropRunner.class);

    // @Value로 개별 프로퍼티 로딩
    @Value("${myprop.username}")
    private String username;

    @Value("${myprop.port}")
    private int port;

    // @ConfigurationProperties 바인딩 객체 주입
    @Autowired
    private MyPropProperties props;

    // 활성 프로파일에 따라 생성되는 환경 Bean (없어도 구동되게 optional)
    @Autowired(required = false)
    private MyEnvironment env;

    @Override
    public void run(String... args) {
        // 1) @Value 로딩 확인
        log.debug("Loaded via @Value -> myprop.username={}", username);
        log.info("Loaded via @Value -> myprop.port={}", port);

        // 2) @ConfigurationProperties 로딩 확인
        log.debug("Loaded via @ConfigurationProperties -> username={}", props.getUsername());
        log.info("Loaded via @ConfigurationProperties -> port={}", props.getPort());

        // 3) 활성 프로파일에서 주입된 환경 Bean 확인(선택적)
        if (env != null) {
            log.info("Active MyEnvironment mode = {}", env.getMode());
        } else {
            log.info("No MyEnvironment bean (no matching profile active).");
        }
    }
}
