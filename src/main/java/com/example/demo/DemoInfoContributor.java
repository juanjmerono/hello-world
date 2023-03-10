package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class DemoInfoContributor implements InfoContributor {

    @Value("${app.tomcat.version}")
    private String version;

    @Override
    public void contribute(Info.Builder builder) {

        builder
            .withDetail("spring-boot.version", SpringBootVersion.getVersion())
            .withDetail("tomcat.version", version);
    }
}