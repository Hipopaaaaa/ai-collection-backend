package com.ohj.aicollectionbackend.qianfan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "qianfan")
@Data
public class QianfanConfig {


    private String apiKey;

    private String apiSecret;

    /**
     * 模型
     */
    private String path;

}