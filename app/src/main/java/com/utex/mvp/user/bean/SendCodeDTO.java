package com.utex.mvp.user.bean;

/**
 * Created by Demon on 2018/6/2.
 */
public class SendCodeDTO {

    public static final String LOGIN_TYPE_EMAIL = "EMAIL";

    public static final String LOGIN_TYPE_TEL = "TEL";


    private String username;

    private String password;

    private String challenge;

    private String seccode;

    private String validate;

    private Integer status;

    private String code_type;

    private String return_to;

    private String tel;

    private String login_from;

    /**
     * TEL 手机
     * EMAIL 邮箱
     */
    private String carrier;

    private String country_code;

    public SendCodeDTO() {

    }

    public SendCodeDTO(String username, String challenge, String seccode, String validate, Integer status, String code_type) {
        this.username = username;
        this.challenge = challenge;
        this.seccode = seccode;
        this.validate = validate;
        this.status = status;
        this.code_type = code_type;
    }

    public SendCodeDTO(String username, String password, String challenge, String seccode, String validate, Integer status, String return_to) {
        this.username = username;
        this.password = password;
        this.challenge = challenge;
        this.seccode = seccode;
        this.validate = validate;
        this.status = status;
        this.return_to = return_to;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getLogin_from() {
        return login_from;
    }

    public void setLogin_from(String login_from) {
        this.login_from = login_from;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReturn_to() {
        return return_to;
    }

    public void setReturn_to(String return_to) {
        this.return_to = return_to;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getSeccode() {
        return seccode;
    }

    public void setSeccode(String seccode) {
        this.seccode = seccode;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode_type() {
        return code_type;
    }

    public void setCode_type(String code_type) {
        this.code_type = code_type;
    }
}
