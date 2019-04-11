package com.utex.mvp.user.bean;

import java.util.List;

/**
 * Created by Demon on 2018/7/6.
 */
public class OptionVO {

    /**
     * data : [{"coin_market_code":"btc_usdt","coin_market_id":62}]
     * success : true
     */

    private boolean success;
    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * coin_market_code : btc_usdt
         * coin_market_id : 62
         */

        private String coin_market_code;
        private int coin_market_id;

        public String getCoin_market_code() {
            return coin_market_code;
        }

        public void setCoin_market_code(String coin_market_code) {
            this.coin_market_code = coin_market_code;
        }

        public int getCoin_market_id() {
            return coin_market_id;
        }

        public void setCoin_market_id(int coin_market_id) {
            this.coin_market_id = coin_market_id;
        }
    }
}
