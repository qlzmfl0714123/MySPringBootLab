package com.rookies4.MySpringbootLab.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "myprop")
public class MyPropProperties {
    private String username;
    private int port;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }
}