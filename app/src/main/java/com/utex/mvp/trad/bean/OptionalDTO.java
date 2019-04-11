package com.utex.mvp.trad.bean;

/**
 * Created by Demon on 2018/7/5.
 */
public class OptionalDTO {

    private Integer user_id;

    private String coin_market_id;

    private String coin_market_code;

    public OptionalDTO() {

    }

    public OptionalDTO(Integer user_id, String coin_market_id, String coin_market_code) {
        this.user_id = user_id;
        this.coin_market_id = coin_market_id;
        this.coin_market_code = coin_market_code;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getCoin_market_id() {
        return coin_market_id;
    }

    public void setCoin_market_id(String coin_market_id) {
        this.coin_market_id = coin_market_id;
    }

    public String getCoin_market_code() {
        return coin_market_code;
    }

    public void setCoin_market_code(String coin_market_code) {
        this.coin_market_code = coin_market_code;
    }
}

