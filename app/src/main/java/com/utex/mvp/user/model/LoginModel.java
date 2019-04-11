package com.utex.mvp.user.model;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.utex.bean.UserDO;
import com.utex.core.UTEXApplication;
import com.utex.data.ApiService;
import com.utex.db.ExUserDao;
import com.utex.mvp.user.bean.GTCodeVO;
import com.utex.mvp.user.bean.GtCodeDTO;
import com.utex.mvp.user.bean.LoginDTO;
import com.utex.mvp.user.bean.LoginPreDTO;
import com.utex.mvp.user.bean.OptionVO;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.mvp.user.bean.UserVO;
import com.utex.mvp.user.presenter.ILoginPresenter;
import com.utex.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Demon on 2018/8/3.
 */
public class LoginModel implements ILoginModel {

    @Override
    public void requestToken(ApiService apiService, final ILoginPresenter iLoginPresenter) {
        final UserDO query = ExUserDao.query();
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(query.getEmail());
        userDTO.setToken(query.getToken());
        apiService.requestToken(userDTO).enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.code() == 200) {
                    String token = response.body().getString("token");
                    query.setToken(token);
                    ExUserDao.insertUser(query);
                    UTEXApplication.setLoginUser(query);
                    UTEXApplication.setToken(query.getToken());
                    UTEXApplication.setUsername(query.getEmail());
                    iLoginPresenter.requestTokenSuccess();
                } else {
                    iLoginPresenter.requestTokenFail();
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                iLoginPresenter.requestTokenFail();
            }
        });
    }

    @Override
    public void getUserInfo(ApiService apiService, final ILoginPresenter iLoginPresenter) {
        apiService.getUserInfo()
                .enqueue(new Callback<UserVO>() {
                    @Override
                    public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                        try {
                            if (response.code() == 200) {
                                //插入数据库
                                UserDO loginUser = UTEXApplication.getLoginUser();
                                UserDO data = response.body().getData();
                                data.setToken(loginUser.getToken());
                                data.setEmail(loginUser.getEmail());
                                data.setUsername(loginUser.getUsername());
                                ExUserDao.insertUser(data);
                                UTEXApplication.setLoginUser(data);
                                UTEXApplication.setToken(data.getToken());
                                UTEXApplication.setUsername(data.getUsername());

                                iLoginPresenter.getUserInfoSuccess();
                            } else {
                                iLoginPresenter.getUserInfoFail();
                            }
                        } catch (Exception e) {
                            e.getMessage();
                            iLoginPresenter.getUserInfoFail();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserVO> call, Throwable t) {
                        iLoginPresenter.getUserInfoFail();
                    }
                });
    }

    @Override
    public void getGTCode(ApiService apiService, String account, ILoginPresenter iLoginPresenter) {
        GtCodeDTO gtCodeDTO = new GtCodeDTO(account);

        apiService.getGTCode(gtCodeDTO)
                .enqueue(new Callback<GTCodeVO>() {
                    @Override
                    public void onResponse(Call<GTCodeVO> call, Response<GTCodeVO> response) {
                        iLoginPresenter.getGTCodeSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<GTCodeVO> call, Throwable t) {
                        Log.e("TAG", String.valueOf(t));
                    }
                });
    }

    @Override
    public void loginPre(ApiService apiService, LoginPreDTO loginPreDTO, ILoginPresenter iLoginPresenter) {
        apiService.loginPre(loginPreDTO)
                .enqueue(new Callback<UserVO>() {
                    @Override
                    public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                        if (response.code() == 200) {
                            UserDO data = response.body().getData();
//                            data.setEmail(sendCodeDTO.getUsername());
                            data.setToken(response.body().getToken());
                            iLoginPresenter.loginPreSuccess(response.body());
                        } else {
                            String str = Utils.doErrorBody(response.errorBody());
                            iLoginPresenter.loginPreError(str);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserVO> call, Throwable t) {
                        iLoginPresenter.loginPreError(String.valueOf(""));
                    }
                });
    }

    @Override
    public void login(ApiService apiService, String username, String user_type, String passCode, ILoginPresenter iLoginPresenter) {
        LoginDTO loginDTO = new LoginDTO(username, user_type, passCode, "");
        loginDTO.setLogin_from("app");
        apiService.login(loginDTO)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            String token = response.body().getString("token");
                            iLoginPresenter.loginSuccess(token);
                        } else {
                            String str = Utils.doErrorBody(response.errorBody());
                            iLoginPresenter.loginError(str);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iLoginPresenter.loginError("");
                    }
                });
    }

    @Override
    public void sendLoginCode(ApiService apiService, ILoginPresenter iLoginPresenter) {
        apiService.sendLoginCode()
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.code() == 200) {
                            //发送验证码成功
                            iLoginPresenter.sendLoginCodeSuccess();
                        } else {
                            String str = Utils.doErrorBody(response.errorBody());
                            iLoginPresenter.sendLoginCodeError(str);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        iLoginPresenter.sendLoginCodeError("");
                    }
                });
    }

    @Override
    public void getOption(ApiService apiService, ILoginPresenter iLoginPresenter) {
        apiService.getOption()
                .enqueue(new Callback<OptionVO>() {
                    @Override
                    public void onResponse(Call<OptionVO> call, Response<OptionVO> response) {
                        if (response.code() == 200) {
                            iLoginPresenter.getOptionSuccess(response.body().getData());
                        } else {
                            iLoginPresenter.getOptionError();
                        }
                    }

                    @Override
                    public void onFailure(Call<OptionVO> call, Throwable t) {
                        iLoginPresenter.getOptionError();
                    }
                });
    }

}
