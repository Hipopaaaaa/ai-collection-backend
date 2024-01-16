package com.ohj.aicollectionbackend.qianfan.listener;

import cn.hutool.json.JSONUtil;
import com.ohj.aicollectionbackend.spark.model.SparkResponse;
import com.ohj.aicollectionbackend.spark.model.SparkResponse.ContentRoleIndex;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.io.IOException;
import java.util.List;


@Slf4j
@Data
public class QianfanChatListener extends WebSocketListener {
    public StringBuilder totalAnswer = new StringBuilder();

    /**
     * websocket连接关闭标志
     */
    private Boolean wsCloseFlag=false;

    public QianfanChatListener(StringBuilder answer) {
        this.totalAnswer=answer;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        //todo 这里的response需要改为qianfan的response
        //SparkResponse chatResponse = JSONUtil.toBean(text, SparkResponse.class);
        //if (chatResponse.getHeader().getCode() != 0) {
        //    log.info("发生错误，错误码为：" + chatResponse.getHeader().getCode());
        //    log.info("本次请求的sid为：" + chatResponse.getHeader().getSid());
        //    webSocket.close(1000, chatResponse.getHeader().getMessage());
        //}
        //List<ContentRoleIndex> textList = chatResponse.getPayload().getChoices().getText();
        //for (ContentRoleIndex temp : textList) {
        //    totalAnswer.append(temp.getContent());
        //}
        //if (chatResponse.getHeader().getStatus() == 2) {
        //    // 可以关闭连接，释放资源
        //    ContentRoleIndex contentRoleIndex = new ContentRoleIndex();
        //    contentRoleIndex.setRole("assistant");
        //    contentRoleIndex.setContent(totalAnswer.toString());
        //    wsCloseFlag = true;
        //}
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        if (response != null) {
            int code = response.code();
            try {
                log.info("[Spark-chat-websocket]failure code:{} , body{}", code, response.body().string());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}