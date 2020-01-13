package com.zzyi.senmu.cloud.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 状态结果类
 * @author zzyi
 * @version 1.0.0
 * @date 2020/1/13 14:04
 */
@Data
@AllArgsConstructor
public class StatueResult {
    /**
     * 状态码
     */
    private int statueCode;
    /**
     * 消息
     */
    private String message;

    /**
     * 返回默认的成功消息
     * @return 成功消息
     */
    public static StatueResult success() {
        return new StatueResult(200,"操作成功");
    }

    /**
     * 返回自定义的成功消息
     * @param message 自定义消息
     * @return 成功消息
     */
    public static StatueResult success(String message) {
        return new StatueResult(200,message);
    }

    /**
     * 返回自定义的失败消息
     * @param message 自定义的消息
     * @return 失败消息
     */
    public static StatueResult fail(String message) {
        return new StatueResult(500,message);
    }


    /**
     * 返回默认的失败消息
     * @return 失败消息
     */
    public static StatueResult fail() {
        return new StatueResult(500,"服务器内部错误");
    }

}
