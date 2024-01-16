package com.ohj.aicollectionbackend.qianfan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hipopaaaaa
 * @create 2023/12/22 17:07
 * 千帆授权响应体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QianfanAuthResponse {
    private String refreshToken;
    private String expiresIn;
    private String sessionKey;
    private String accessToken;
    private String scope;
    private String sessionSecret;
}
