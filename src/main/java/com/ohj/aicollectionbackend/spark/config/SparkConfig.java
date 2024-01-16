package com.ohj.aicollectionbackend.spark.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "spark")
@Data
public class SparkConfig {

    private String appId;

    private String apiKey;

    private String apiSecret;

    private String host;

    private String path;

    private String domain;


}