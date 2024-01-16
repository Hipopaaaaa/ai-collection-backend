package com.ohj.aicollectionbackend.spark.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SparkRequest {
    private Header header;
    private Parameter parameter;
    private Payload payload;

    @Data
    @Builder
    public static class Header {
        private String app_id;
        private String uid;
    }

    @Data
    @Builder
    public static class Parameter {
        private Chat chat;
    }

    @Data
    @Builder
    public static class Chat {
        private String domain;
        private double temperature;
        private int max_tokens;
        private String chat_id;
    }

    @Data
    @Builder
    public static class Payload {
        private Message message;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message {
        private List<Text> text;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Text {
        String role;
        String content;
    }
}