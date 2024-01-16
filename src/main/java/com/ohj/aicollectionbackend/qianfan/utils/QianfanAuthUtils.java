package com.ohj.aicollectionbackend.qianfan.utils;

/**
 * @author Hipopaaaaa
 * @create 2023/12/22 16:27
 */
public class QianfanAuthUtils {

    /**
     * 拼接获取access_token链接
     * @param apiKey
     * @param apiSecret
     * @return
     */
    public static String getAuthUrl(String apiKey,String apiSecret){
        return String.format("https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=%s&client_secret=%s&grant_type=client_credentials", apiKey, apiSecret);
    }

    /**
     * 拼接请求url
     * @param path
     * @return
     */
    public static String getRequestUrl(String path){
        return String.format("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/&s",path);
    }

}
