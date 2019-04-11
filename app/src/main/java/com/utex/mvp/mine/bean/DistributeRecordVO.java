package com.utex.mvp.mine.bean;

import java.util.List;

/**
 * Created by Demon on 2018/11/26.
 */
public class DistributeRecordVO {


    /**
     * data : [{"activity_id":17,"amount":100,"coin_code":"dd","coin_name":"dd","create_time":1529589778000,"en_title":"","id":44,"state":1,"type":1,"user_id":10004,"zh_title":""},{"activity_id":17,"amount":100,"coin_code":"dd","coin_name":"dd","create_time":1529589778000,"en_title":"","id":44,"state":1,"type":1,"user_id":10004,"zh_title":""}]
     * page : {"first_item_index":0,"page":1,"page_count":1,"page_size":10,"total":2}
     * success : true
     */

    private PageBean page;
    private boolean success;
    private List<DataBean> data;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

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

    public static class PageBean {
        /**
         * first_item_index : 0
         * page : 1
         * page_count : 1
         * page_size : 10
         * total : 2
         */

        private int first_item_index;
        private int page;
        private int page_count;
        private int page_size;
        private int total;

        public int getFirst_item_index() {
            return first_item_index;
        }

        public void setFirst_item_index(int first_item_index) {
            this.first_item_index = first_item_index;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPage_count() {
            return page_count;
        }

        public void setPage_count(int page_count) {
            this.page_count = page_count;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public static class DataBean {
        /**
         * activity_id : 17
         * amount : 100
         * coin_code : dd
         * coin_name : dd
         * create_time : 1529589778000
         * en_title :
         * id : 44
         * state : 1
         * type : 1
         * user_id : 10004
         * zh_title :
         */

        private int activity_id;
        private double amount;
        private String coin_code;
        private String coin_name;
        private long create_time;
        private String en_title;
        private int id;
        private int state;
        private int type;
        private int user_id;
        private String zh_title;

        public int getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(int activity_id) {
            this.activity_id = activity_id;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getCoin_code() {
            return coin_code;
        }

        public void setCoin_code(String coin_code) {
            this.coin_code = coin_code;
        }

        public String getCoin_name() {
            return coin_name;
        }

        public void setCoin_name(String coin_name) {
            this.coin_name = coin_name;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public String getEn_title() {
            return en_title;
        }

        public void setEn_title(String en_title) {
            this.en_title = en_title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getZh_title() {
            return zh_title;
        }

        public void setZh_title(String zh_title) {
            this.zh_title = zh_title;
        }
    }
}
