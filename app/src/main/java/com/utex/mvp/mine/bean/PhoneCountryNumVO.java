package com.utex.mvp.mine.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Demon on 2018/7/30.
 */
public class PhoneCountryNumVO {

    private ArrayList<DataBean> data;

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * area_code : 355
         * country_id : 3
         * name_cn : 阿尔巴尼亚
         * name_en : Albania
         */

        private String area_code;
        private int country_id;
        private String name_cn;
        private String name_en;

        public String getArea_code() {
            return area_code;
        }

        public void setArea_code(String area_code) {
            this.area_code = area_code;
        }

        public int getCountry_id() {
            return country_id;
        }

        public void setCountry_id(int country_id) {
            this.country_id = country_id;
        }

        public String getName_cn() {
            return name_cn;
        }

        public void setName_cn(String name_cn) {
            this.name_cn = name_cn;
        }

        public String getName_en() {
            return name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
        }
    }
}
