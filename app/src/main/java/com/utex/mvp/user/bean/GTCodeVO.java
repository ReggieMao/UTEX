package com.utex.mvp.user.bean;

/**
 * Created by Demon on 2018/6/2.
 */
public class GTCodeVO {

    /**
     * data : {"challenge":"4a8d94534374287ebf9abb9623f8e43d","gt":"887067dd8ae106c7c3cde20db2db5468","status":1}
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
         * challenge : 4a8d94534374287ebf9abb9623f8e43d
         * gt : 887067dd8ae106c7c3cde20db2db5468
         * status : 1
         */

        private String challenge;
        private String gt;
        private int status;

        public String getChallenge() {
            return challenge;
        }

        public void setChallenge(String challenge) {
            this.challenge = challenge;
        }

        public String getGt() {
            return gt;
        }

        public void setGt(String gt) {
            this.gt = gt;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
