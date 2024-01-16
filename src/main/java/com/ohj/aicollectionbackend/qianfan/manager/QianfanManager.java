package com.ohj.aicollectionbackend.qianfan.manager;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.ohj.aicollectionbackend.qianfan.config.QianfanConfig;
import com.ohj.aicollectionbackend.qianfan.listener.QianfanChatListener;
import com.ohj.aicollectionbackend.qianfan.model.QianfanAuthResponse;
import com.ohj.aicollectionbackend.qianfan.model.QianfanRequest;
import com.ohj.aicollectionbackend.qianfan.utils.QianfanAuthUtils;
import com.ohj.aicollectionbackend.spark.config.SparkConfig;
import com.ohj.aicollectionbackend.spark.listener.SparkChatListener;
import com.ohj.aicollectionbackend.spark.model.SparkRequest;
import com.ohj.aicollectionbackend.spark.utils.SparkAuthUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
@Slf4j
public class QianfanManager {

    @Resource
    QianfanConfig qianfanConfig;

    private static final Map<Object, SseEmitter> SSE_CACHE = new ConcurrentHashMap<>();

    public QianfanChatListener doChat(long userId, String question, StringBuilder answer) {
        // 构建鉴权url
        String authUrl = QianfanAuthUtils.getAuthUrl(qianfanConfig.getApiKey(), qianfanConfig.getApiSecret());
        if (authUrl == null) {
            throw new RuntimeException("authUrl 生成失败 !");
        }
        // 获取access_token
        String accessToken = JSONUtil.toBean(HttpUtil.createPost(authUrl)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .execute().body(), QianfanAuthResponse.class).getAccessToken();
        if (accessToken == null) {
            throw new RuntimeException("accessToken 获取失败!");
        }

        OkHttpClient client = new OkHttpClient.Builder().build();
        // 构建聊天请求
        QianfanRequest chatRequest = buildChatRequest(userId, question);
        System.out.println(JSONUtil.toJsonStr(chatRequest));
        // 构建websocket请求
        Request request = new Request.Builder().url(authUrl).build();
        QianfanChatListener qianfanChat = new QianfanChatListener(answer);
        // 向千帆发送请求
        // 发起websocket请求
        WebSocket webSocket = client.newWebSocket(request, qianfanChat);
        webSocket.send(JSONUtil.toJsonStr(chatRequest));
        // 返回与讯飞的聊天连接
        return qianfanChat;
    }

    /**
     * 获取SseEmitter对象
     *
     * @param key 关键
     * @return {@link SseEmitter}
     */
    public SseEmitter getConn(Object key) {
        final SseEmitter sseEmitter = SSE_CACHE.get(key);
        if (sseEmitter != null) {
            return sseEmitter;
        } else {
            // 设置连接超时时间，需要配合配置项 spring.mvc.async.request-timeout: 600000 一起使用
            final SseEmitter emitter = new SseEmitter(600000L);
            // 注册超时回调，超时后触发
            emitter.onTimeout(() -> {
                log.info("连接已超时，正准备关闭，key = {}", key);
                SSE_CACHE.remove(key);
            });
            // 注册完成回调，调用 emitter.complete() 触发
            emitter.onCompletion(() -> {
                log.info("连接已关闭，正准备释放，key = {}", key);
                SSE_CACHE.remove(key);
                log.info("连接已释放，key = {}", key);
            });
            // 注册异常回调，调用 emitter.completeWithError() 触发
            emitter.onError(throwable -> {
                log.error("连接已异常，正准备关闭，key = {}", key, throwable);
                SSE_CACHE.remove(key);
            });
            SSE_CACHE.put(key, emitter);
            return emitter;
        }
    }

    /**
     * 构建聊天请求
     *
     * @param userId   用户id
     * @param question 问题
     * @return {@link SparkRequest}
     */
    private QianfanRequest buildChatRequest(long userId, String question) {
        return QianfanRequest.builder()
                .user_id(String.valueOf(userId))
                .message(QianfanRequest.Chat.builder()
                        .role("user")
                        .content(question).build()).build();
    }

}