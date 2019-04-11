package com.utex.mvp.mine.bean;

/**
 * Created by Demon on 2018/7/25.
 */
public class GoogleVO {

    /**
     * data : {"url":"http://xxx.com","secret":"ASDFDSDSFDS"}
     * message :
     */

    private DataBean data;
    private String message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * url : http://xxx.com
         * secret : ASDFDSDSFDS
         */

        private String url;
        private String secret;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }
}
