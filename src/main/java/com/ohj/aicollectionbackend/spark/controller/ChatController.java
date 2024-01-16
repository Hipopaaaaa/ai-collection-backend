package com.ohj.aicollectionbackend.spark.controller;


import com.ohj.aicollectionbackend.spark.listener.SparkChatListener;
import com.ohj.aicollectionbackend.spark.manager.SparkManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * 官网：
 * https://console.xfyun.cn/app/myapp
 */
@RestController
@Slf4j
public class ChatController {

    @Resource
    SparkManager sparkManager;

    @GetMapping(value = "/test/spark", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter chat(@RequestParam("question") String question) {
        long userId = 132;
        final SseEmitter emitter = sparkManager.getConn(userId);
        // 异步发消息
        CompletableFuture.runAsync(()->{
            StringBuilder answerCache = new StringBuilder();
            // 获取到与讯飞的聊天链接
            SparkChatListener sparkChatListener = sparkManager.doChat(userId, question, answerCache);
            int lastIdx = 0, len = 0;
            try {
                while (!sparkChatListener.getWsCloseFlag()) {
                    if(lastIdx < (len = answerCache.length())){
                        emitter.send(answerCache.substring(lastIdx, len).getBytes());
                        lastIdx = len;
                    }
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return emitter;
    }

}