package com.utex.mvp.home.bean;

import java.util.List;

/**
 * Created by Demon on 2018/6/4.
 */
public class NoticeVo {
    
    /**
     * data : [{"country_type":"zh","link":"xin","status":1,"title":"xin","weight":6},{"country_type":"zh","link":"23423","status":1,"title":"3242","weight":5},{"country_type":"zh","link":"1231231","status":1,"title":"31231","weight":2}]
     * message :
     */

    private String message;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * country_type : zh
         * link : xin
         * status : 1
         * title : xin
         * weight : 6
         */

        private String country_type;
        private String link;
        private int status;
        private String title;
        private int weight;

        public String getCountry_type() {
            return country_type;
        }

        public void setCountry_type(String country_type) {
            this.country_type = country_type;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
}
