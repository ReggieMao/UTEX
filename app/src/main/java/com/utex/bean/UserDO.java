package com.utex.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Demon on 2018/6/2.
 */
@Entity
public class UserDO {

    @Id(autoincrement = true)
    private Long exnowId;

    private int uid;

    private int checking_level;
    private String email;
    private String google_auth_url;
    private boolean google_status;
    private String invite_code;
    private String ip;
    private int is_activated;
    private int is_first_login;
    private int is_google_authed;
    private int safe_level;
    private String tel;
    private boolean tel_status;
    private String timestamp;
    private String twice_auth_way;
    private int user_level;
    private int vip;
    private String zendesk_id;
    private String sso_url;
    private String user_type;
    private String username;

    private String token;

    private String uuid;
    private boolean email_status;
    /**
     * 是否可以站内划转
     */
    private boolean transfer_switch;


    public UserDO() {
    }


    @Generated(hash = 58237162)
    public UserDO(Long exnowId, int uid, int checking_level, String email,
            String google_auth_url, boolean google_status, String invite_code, String ip,
            int is_activated, int is_first_login, int is_google_authed, int safe_level,
            String tel, boolean tel_status, String timestamp, String twice_auth_way,
            int user_level, int vip, String zendesk_id, String sso_url, String user_type,
            String username, String token, String uuid, boolean email_status,
                  boolean transfer_switch) {
        this.exnowId = exnowId;
        this.uid = uid;
        this.checking_level = checking_level;
        this.email = email;
        this.google_auth_url = google_auth_url;
        this.google_status = google_status;
        this.invite_code = invite_code;
        this.ip = ip;
        this.is_activated = is_activated;
        this.is_first_login = is_first_login;
        this.is_google_authed = is_google_authed;
        this.safe_level = safe_level;
        this.tel = tel;
        this.tel_status = tel_status;
        this.timestamp = timestamp;
        this.twice_auth_way = twice_auth_way;
        this.user_level = user_level;
        this.vip = vip;
        this.zendesk_id = zendesk_id;
        this.sso_url = sso_url;
        this.user_type = user_type;
        this.username = username;
        this.token = token;
        this.uuid = uuid;
        this.email_status = email_status;
        this.transfer_switch = transfer_switch;
    }


    public boolean isEmail_status() {
        return email_status;
    }

    public void setEmail_status(boolean email_status) {
        this.email_status = email_status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getChecking_level() {
        return checking_level;
    }

    public void setChecking_level(int checking_level) {
        this.checking_level = checking_level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoogle_auth_url() {
        return google_auth_url;
    }

    public void setGoogle_auth_url(String google_auth_url) {
        this.google_auth_url = google_auth_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isGoogle_status() {
        return google_status;
    }

    public void setGoogle_status(boolean google_status) {
        this.google_status = google_status;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getIs_activated() {
        return is_activated;
    }

    public void setIs_activated(int is_activated) {
        this.is_activated = is_activated;
    }

    public int getIs_first_login() {
        return is_first_login;
    }

    public void setIs_first_login(int is_first_login) {
        this.is_first_login = is_first_login;
    }

    public int getIs_google_authed() {
        return is_google_authed;
    }

    public void setIs_google_authed(int is_google_authed) {
        this.is_google_authed = is_google_authed;
    }

    public int getSafe_level() {
        return safe_level;
    }

    public void setSafe_level(int safe_level) {
        this.safe_level = safe_level;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public boolean isTel_status() {
        return tel_status;
    }

    public void setTel_status(boolean tel_status) {
        this.tel_status = tel_status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTwice_auth_way() {
        return twice_auth_way;
    }

    public void setTwice_auth_way(String twice_auth_way) {
        this.twice_auth_way = twice_auth_way;
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public String getZendesk_id() {
        return zendesk_id;
    }

    public void setZendesk_id(String zendesk_id) {
        this.zendesk_id = zendesk_id;
    }

    public String getSso_url() {
        return sso_url;
    }

    public void setSso_url(String sso_url) {
        this.sso_url = sso_url;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public Long getExnowId() {
        return exnowId;
    }

    public void setExnowId(Long exnowId) {
        this.exnowId = exnowId;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "uid=" + uid +
                ", checking_level=" + checking_level +
                ", email='" + email + '\'' +
                ", google_auth_url='" + google_auth_url + '\'' +
                ", google_status=" + google_status +
                ", invite_code='" + invite_code + '\'' +
                ", ip='" + ip + '\'' +
                ", is_activated=" + is_activated +
                ", is_first_login=" + is_first_login +
                ", is_google_authed=" + is_google_authed +
                ", safe_level=" + safe_level +
                ", tel='" + tel + '\'' +
                ", tel_status=" + tel_status +
                ", timestamp='" + timestamp + '\'' +
                ", twice_auth_way='" + twice_auth_way + '\'' +
                ", user_level=" + user_level +
                ", vip=" + vip +
                ", zendesk_id='" + zendesk_id + '\'' +
                ", sso_url='" + sso_url + '\'' +
                ", user_type='" + user_type + '\'' +
                ", token='" + token + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }

    public boolean getGoogle_status() {
        return this.google_status;
    }

    public boolean getTel_status() {
        return this.tel_status;
    }

    public boolean getEmail_status() {
        return this.email_status;
    }

    public boolean getTransfer_switch() {
        return this.transfer_switch;
    }


    public void setTransfer_switch(boolean transfer_switch) {
        this.transfer_switch = transfer_switch;
    }

}
