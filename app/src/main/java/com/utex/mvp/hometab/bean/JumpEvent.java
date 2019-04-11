package com.utex.mvp.hometab.bean;

/**
 * Created by Demon on 2018/7/10.
 */
public class JumpEvent {

    private Integer status;

    public JumpEvent() {

    }

    public JumpEvent(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
