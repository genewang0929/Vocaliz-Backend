package com.example.vocaliz.verificationModule.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {

    @Value("${mail.gmail.host}")
    private String host;

    @Value("${mail.gmail.port}") // 使用「:」符號可以加上預設值
    private int port;

    @Value("${mail.auth.enabled}")
    private boolean authEnabled;

    @Value("${mail.starttls.enabled}")
    private boolean starttlsEnabled;

    @Value("${mail.protocol}")
    private String protocol;

    @Value("${mail.gmail.username}")
    private String username;

    @Value("${mail.gmail.password}")
    private String password;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isAuthEnabled() {
        return authEnabled;
    }

    public boolean isStarttlsEnabled() {
        return starttlsEnabled;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}