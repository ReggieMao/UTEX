package com.utex.bean;

import java.io.Serializable;
import java.util.List;

public class TestDo implements Serializable {

    private Integer id;

    private String method;

    private Param params;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Param getParams() {
        return params;
    }

    public void setParams(Param params) {
        this.params = params;
    }

    public static class Param {

        private String market;

        private List<String> marketlist;

        private Integer period;

        private Integer limit;

        private String prec;

        private Long starttime;

        private Long endtime;

        public Param(String market) {
            this.market = market;
        }

        public Param() {

        }

        public Long getStarttime() {
            return starttime;
        }

        public void setStarttime(Long starttime) {
            this.starttime = starttime;
        }

        public Long getEndtime() {
            return endtime;
        }

        public void setEndtime(Long endtime) {
            this.endtime = endtime;
        }

        public String getMarket() {
            return market;
        }

        public void setMarket(String market) {
            this.market = market;
        }

        public List<String> getMarketlist() {
            return marketlist;
        }

        public void setMarketlist(List<String> marketlist) {
            this.marketlist = marketlist;
        }

        public Integer getPeriod() {
            return period;
        }

        public void setPeriod(Integer period) {
            this.period = period;
        }

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public String getPrec() {
            return prec;
        }

        public void setPrec(String prec) {
            this.prec = prec;
        }
    }
}