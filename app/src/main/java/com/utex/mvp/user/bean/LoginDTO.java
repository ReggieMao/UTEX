package com.utex.mvp.user.bean;

/**
 * Created by Demon on 2018/6/2.
 */
public class LoginDTO {

    private String username;

    private String login_type;

    private String pass_code;

    private String return_to;

    private String login_from;

    public LoginDTO() {

    }

    public LoginDTO(String username, String login_type, String pass_code, String return_to) {
        this.username = username;
        this.login_type = login_type;
        this.pass_code = pass_code;
        this.return_to = return_to;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public String getPass_code() {
        return pass_code;
    }

    public void setPass_code(String pass_code) {
        this.pass_code = pass_code;
    }

    public String getReturn_to() {
        return return_to;
    }

    public void setReturn_to(String return_to) {
        this.return_to = return_to;
    }

    public String getLogin_from() {
        return login_from;
    }

    public void setLogin_from(String login_from) {
        this.login_from = login_from;
    }
}
