package com.utex.mvp.asset.bean;

import java.util.List;

/**
 * Created by Demon on 2018/6/1.
 */
public class Asset {

    /**
     * data : {"asset_user_list":[{"asset_id":1,"available_fund":9999921.46,"coin_code":"gzh","coin_en_name":"bitcoin","coin_logo":"http://quintar1.oss-cn-hongkong.aliyuncs.com/coinImages/bitcoin.png","frozen_fund":12,"open_type":1,"pairs":"btc_usdt,btc_eth","total_fund":9999933.46}],"btc_asset":1.00001002391346E12,"withdrawal_count":0,"withdrawal_limit":3}
     * success : true
     */

    private DataBean data;
    private boolean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * asset_user_list : [{"asset_id":1,"available_fund":9999921.46,"coin_code":"gzh","coin_en_name":"bitcoin","coin_logo":"http://quintar1.oss-cn-hongkong.aliyuncs.com/coinImages/bitcoin.png","frozen_fund":12,"open_type":1,"pairs":"btc_usdt,btc_eth","total_fund":9999933.46}]
         * btc_asset : 1.00001002391346E12
         * withdrawal_count : 0
         * withdrawal_limit : 3
         */

        private double btc_asset;
        private double withdrawal_count;
        private double withdrawal_limit;
        private List<AssetUserListBean> asset_user_list;

        public double getBtc_asset() {
            return btc_asset;
        }

        public void setBtc_asset(double btc_asset) {
            this.btc_asset = btc_asset;
        }

        public double getWithdrawal_count() {
            return withdrawal_count;
        }

        public void setWithdrawal_count(double withdrawal_count) {
            this.withdrawal_count = withdrawal_count;
        }

        public double getWithdrawal_limit() {
            return withdrawal_limit;
        }

        public void setWithdrawal_limit(double withdrawal_limit) {
            this.withdrawal_limit = withdrawal_limit;
        }

        public List<AssetUserListBean> getAsset_user_list() {
            return asset_user_list;
        }

        public void setAsset_user_list(List<AssetUserListBean> asset_user_list) {
            this.asset_user_list = asset_user_list;
        }

        public static class AssetUserListBean {
            /**
             * asset_id : 1
             * available_fund : 9999921.46
             * coin_code : gzh
             * coin_en_name : bitcoin
             * coin_logo : http://quintar1.oss-cn-hongkong.aliyuncs.com/coinImages/bitcoin.png
             * frozen_fund : 12
             * open_type : 1
             * pairs : btc_usdt,btc_eth
             * total_fund : 9999933.46
             */

            private int asset_id;
            private String available_fund;
            private String coin_alias_code;
            private String coin_code;
            private String coin_en_name;
            private String coin_logo;
            private String frozen_fund;
            private int open_type;
            private String pairs;
            private String total_fund;
            private Integer show_decimal;

            public int getAsset_id() {
                return asset_id;
            }

            public void setAsset_id(int asset_id) {
                this.asset_id = asset_id;
            }

            public String getAvailable_fund() {
                return available_fund;
            }

            public void setAvailable_fund(String available_fund) {
                this.available_fund = available_fund;
            }

            public String getCoin_alias_code() {
                return coin_alias_code;
            }

            public void setCoin_alias_code(String coin_alias_code) {
                this.coin_alias_code = coin_alias_code;
            }

            public String getCoin_code() {
                return coin_code;
            }

            public void setCoin_code(String coin_code) {
                this.coin_code = coin_code;
            }

            public String getCoin_en_name() {
                return coin_en_name;
            }

            public void setCoin_en_name(String coin_en_name) {
                this.coin_en_name = coin_en_name;
            }

            public String getCoin_logo() {
                return coin_logo;
            }

            public void setCoin_logo(String coin_logo) {
                this.coin_logo = coin_logo;
            }

            public String getFrozen_fund() {
                return frozen_fund;
            }

            public void setFrozen_fund(String frozen_fund) {
                this.frozen_fund = frozen_fund;
            }

            public int getOpen_type() {
                return open_type;
            }

            public void setOpen_type(int open_type) {
                this.open_type = open_type;
            }

            public String getPairs() {
                return pairs;
            }

            public void setPairs(String pairs) {
                this.pairs = pairs;
            }

            public String getTotal_fund() {
                return total_fund;
            }

            public void setTotal_fund(String total_fund) {
                this.total_fund = total_fund;
            }

            public Integer getShow_decimal() {
                return show_decimal;
            }

            public void setShow_decimal(Integer show_decimal) {
                this.show_decimal = show_decimal;
            }
        }
    }
}
