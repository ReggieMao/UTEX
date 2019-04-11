package com.utex.mvp.mine.bean;

/**
 * Created by Demon on 2018/7/25.
 */
public class LoginVerificationDTO {

    /**
     * 1 开
     * 0 关
     */
    private Integer tel_status;

    /**
     * 1 开
     * 0 关
     */
    private Integer google_status;

    private Integer email_status;

    public Integer getEmail_status() {
        return email_status;
    }

    public void setEmail_status(Integer email_status) {
        this.email_status = email_status;
    }

    public Integer getTel_status() {
        return tel_status;
    }

    public void setTel_status(Integer tel_status) {
        this.tel_status = tel_status;
    }

    public Integer getGoogle_status() {
        return google_status;
    }

    public void setGoogle_status(Integer google_status) {
        this.google_status = google_status;
    }
}
