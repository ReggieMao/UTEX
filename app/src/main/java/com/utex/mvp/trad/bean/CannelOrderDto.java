package com.utex.mvp.trad.bean;

/**
 * Created by Demon on 2018/6/12.
 */
public class CannelOrderDto {

    private int user_id;

    private String market;

    private String user_order_id;

    public CannelOrderDto() {

    }

    public CannelOrderDto(int user_id, String market, String user_order_id) {
        this.user_id = user_id;
        this.market = market;
        this.user_order_id = user_order_id;
    }
}
