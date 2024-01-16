package com.ohj.aicollectionbackend.qianfan.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Hipopaaaaa
 * @create 2023/12/22 16:45
 * 千帆模型请求体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QianfanRequest {

    //todo 目前一次请求只能发送一个问题
    private Chat message;

    private String user_id;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Chat{
        private String role;
        private String content;
    }
}
