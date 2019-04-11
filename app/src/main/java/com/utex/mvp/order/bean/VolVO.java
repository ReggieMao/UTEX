package com.utex.mvp.order.bean;

import java.util.List;

/**
 * Created by Demon on 2018/7/2.
 */
public class VolVO {

    /**
     * data : [{"amount":1,"base_id":3,"deal":1,"deal_id":6,"fee":0.001,"id":1065,"market":"eos_usdt","order_id":3,"price":1,"quote_id":4,"side":2,"time":1527128485000,"user_id":10004}]
     * page : {"first_item_index":0,"page":1,"page_count":106,"page_size":10,"total":1051}
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
         * page_count : 106
         * page_size : 10
         * total : 1051
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
         * amount : 1
         * base_id : 3
         * deal : 1
         * deal_id : 6
         * fee : 0.001
         * id : 1065
         * market : eos_usdt
         * order_id : 3
         * price : 1
         * quote_id : 4
         * side : 2
         * time : 1527128485000
         * user_id : 10004
         */

        private String amount;
        private int base_id;
        private String deal;
        private int deal_id;
        private String fee;
        private int id;
        private String market;
        private int order_id;
        private String price;
        private int quote_id;
        private int side;
        private long time;
        private int user_id;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getBase_id() {
            return base_id;
        }

        public void setBase_id(int base_id) {
            this.base_id = base_id;
        }

        public String getDeal() {
            return deal;
        }

        public void setDeal(String deal) {
            this.deal = deal;
        }

        public int getDeal_id() {
            return deal_id;
        }

        public void setDeal_id(int deal_id) {
            this.deal_id = deal_id;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMarket() {
            return market;
        }

        public void setMarket(String market) {
            this.market = market;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getQuote_id() {
            return quote_id;
        }

        public void setQuote_id(int quote_id) {
            this.quote_id = quote_id;
        }

        public int getSide() {
            return side;
        }

        public void setSide(int side) {
            this.side = side;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
