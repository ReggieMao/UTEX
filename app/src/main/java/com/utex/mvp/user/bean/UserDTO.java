package com.utex.mvp.user.bean;

/**
 * Created by Demon on 2018/6/2.
 */
public class UserDTO {

    public static final String EMAIL = "EMAIL";
    public static final String TEL = "TEL";

    public static final String TOKEN_TYPE_FORGET_PASSWORD_TOKEN = "FORGET_PASSWORD_TOKEN";

    public static final String TOKEN_TYPE_FREEZE_TOKEN = "FREEZE_TOKEN";

    public static final String TOKEN_TYPE_UNFREEZE_TOKEN = "UNFREEZE_TOKEN";


    private String username;

    private String token;

    private String pass_code;

    private String password;

    private String invitor_id;

    private String challenge;

    private String seccode;

    private String validate;

    private Integer status;

    private String re_password;

    private String re_new_password;

    private String new_password;

    private String user_from;

    private String register_type;

    private String country_code;

    private String code_carrier;

    private String token_type;

    private String carrier;


    public UserDTO() {

    }

    public UserDTO(String username, String pass_code, String password) {
        this.username = username;
        this.pass_code = pass_code;
        this.password = password;
    }

    public UserDTO(String username, String pass_code, String password, String invitor_id) {
        this.username = username;
        this.pass_code = pass_code;
        this.password = password;
        this.invitor_id = invitor_id;
    }

    public UserDTO(String email, String code) {
        this.username = email;
        this.pass_code = code;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getCode_carrier() {
        return code_carrier;
    }

    public void setCode_carrier(String code_carrier) {
        this.code_carrier = code_carrier;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getRegister_type() {
        return register_type;
    }

    public void setRegister_type(String register_type) {
        this.register_type = register_type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_from() {
        return user_from;
    }

    public void setUser_from(String user_from) {
        this.user_from = user_from;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass_code() {
        return pass_code;
    }

    public void setPass_code(String pass_code) {
        this.pass_code = pass_code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInvitor_id() {
        return invitor_id;
    }

    public void setInvitor_id(String invitor_id) {
        this.invitor_id = invitor_id;
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

    public String getRe_password() {
        return re_password;
    }

    public void setRe_password(String re_password) {
        this.re_password = re_password;
    }

    public String getRe_new_password() {
        return re_new_password;
    }

    public void setRe_new_password(String re_new_password) {
        this.re_new_password = re_new_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

}
