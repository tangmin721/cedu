package com.yanxiu.ce.core.score.dto;

import java.io.Serializable;

/**
 * 返回更多的验证消息及结果
 * Created by tangmin on 2016.09.20.
 */
public class CheckScoreDto implements Serializable{
    private Boolean result;
    private String msg;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
