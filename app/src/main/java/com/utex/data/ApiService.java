package com.utex.data;

import com.alibaba.fastjson.JSONObject;
import com.utex.mvp.asset.bean.Asset;
import com.utex.mvp.block.bean.WithdrawalDTO;
import com.utex.mvp.home.bean.BannerVo;
import com.utex.mvp.home.bean.CoinListVo;
import com.utex.mvp.home.bean.NoticeVo;
import com.utex.mvp.hometab.bean.CheckAppVersionDTO;
import com.utex.mvp.hometab.bean.CheckAppVersionVO;
import com.utex.mvp.marketdetail.bean.CoinBaseVO;
import com.utex.mvp.mine.bean.BindGoogleDTO;
import com.utex.mvp.mine.bean.BindTelDTO;
import com.utex.mvp.mine.bean.DistributeRecordVO;
import com.utex.mvp.mine.bean.GoogleVO;
import com.utex.mvp.mine.bean.IdentitySumbitDTO;
import com.utex.mvp.mine.bean.LoginVerificationDTO;
import com.utex.mvp.mine.bean.PhoneCountryNumVO;
import com.utex.mvp.mine.bean.WithdrawalFeeVo;
import com.utex.mvp.order.bean.DepositWithdrawalVO;
import com.utex.mvp.order.bean.OrderManageVO;
import com.utex.mvp.order.bean.StationVO;
import com.utex.mvp.order.bean.VolVO;
import com.utex.mvp.trad.bean.CannelOrderDto;
import com.utex.mvp.trad.bean.Entrust;
import com.utex.mvp.trad.bean.OptionalDTO;
import com.utex.mvp.trad.bean.OrderDTo;
import com.utex.mvp.user.bean.GTCodeVO;
import com.utex.mvp.user.bean.GtCodeDTO;
import com.utex.mvp.user.bean.LoginDTO;
import com.utex.mvp.user.bean.LoginPreDTO;
import com.utex.mvp.user.bean.OptionVO;
import com.utex.mvp.user.bean.SendCodeDTO;
import com.utex.mvp.user.bean.UserDTO;
import com.utex.mvp.user.bean.UserVO;
import com.utex.mvp.user.bean.UserVerifyVO;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by admin on 2017/4/21.
 * 接口
 */
public interface ApiService {

    /**
     * 获取币种列表
     */
    @Headers({"baseUrl:asset"})
    @GET("api/coin/coinMarketDecimal")
    Call<CoinListVo> getCoinList();

    /**
     * 获取banner
     */
    @Headers({"baseUrl:platform"})
    @GET("api/banner/{countryId}/")
    Call<BannerVo> getBanner(@Path("countryId") String countryId);

    /**
     * 获取资产列表,未登陆不能查看
     */
    @Headers({"baseUrl:asset"})
    @GET("api/balance/user/assets")
    Call<Asset> getAssetList();

    @Headers({"baseUrl:user"})
    @POST("api/gt/code/")
    Call<GTCodeVO> getGTCode(@Body GtCodeDTO gtCodeDTO);

    /**
     * 发送验证码
     */
    @Headers({"baseUrl:user"})
    @POST("api/codeSend")
    Call<JSONObject> sendCode(@Body SendCodeDTO sendCodeDTO);

    /**
     * 去注册
     */
    @Headers({"baseUrl:user"})
    @POST("api/user/register/")
    Call<UserVO> register(@Body UserDTO userDTO);

    /**
     * pre登陆
     */
    @Headers({"baseUrl:user"})
    @POST("api/user/prelogin/")
    Call<UserVO> loginPre(@Body LoginPreDTO loginPreDTO);

    /**
     * 获取用户信息
     */
    @Headers({"baseUrl:user"})
    @GET("api/user/info/")
    Call<UserVO> getUserInfo();

    /**
     * 获取公告
     */
    @Headers({"baseUrl:platform"})
    @GET("api/notice/{countryId}/")
    Call<NoticeVo> getNotice(@Path("countryId") String countryId);

    /**
     * 获取委托
     */
    @Headers({"baseUrl:asset"})
    @GET("api/orders/entrustList")
    Call<Entrust> getCurrMarketEntrust(@Query("state") Integer state, @Query("side") Integer side, @Query("market") String coinMarketCode);

    /**
     * 限价订单提交
     */
    @Headers({"baseUrl:asset"})
    @POST("api/orders/limitOrder")
    Call<JSONObject> submitDirection0(@Body OrderDTo orderDTo);

    /**
     * 市价订单提交
     */
    @Headers({"baseUrl:asset"})
    @POST("api/orders/marketOrder")
    Call<JSONObject> submitDirection1(@Body OrderDTo orderDTo);

    /**
     * 撤销一个订单
     */
    @Headers({"baseUrl:asset"})
    @PUT("api/orders/cancelOrder")
    Call<JSONObject> cannelOrder(@Body CannelOrderDto cannelOrderDto);

    /**
     * 登陆发送验证码
     */
    @Headers({"baseUrl:user"})
    @POST("api/user/login/sender/")
    Call<JSONObject> sendLoginCode();

    /**
     * 登录
     */
    @Headers({"baseUrl:user"})
    @POST("api/user/login/")
    Call<JSONObject> login(@Body LoginDTO loginDTO);

    /**
     * 获取当前委托
     */
    @Headers({"baseUrl:asset"})
    @GET("api/orders/entrustList/")
    Call<OrderManageVO> getCurrEntrust(@Query("state") Integer state, @Query("market") String market, @Query("side") Integer side, @Query("page") int page);

    /**
     * 获取历史委托
     */
    @Headers({"baseUrl:asset"})
    @GET("api/orders/entrustList/")
    Call<OrderManageVO> getHostoryEntrust(@Query("state") Integer state, @Query("market") String market, @Query("side") Integer side, @Query("page") int page);

    /**
     * 成交记录接口
     */
    @Headers({"baseUrl:asset"})
    @GET("api/deals/list")
    Call<VolVO> getMineVol(@Query("market") String market, @Query("side") Integer side, @Query("page") Integer page, @Query("page_size") Integer pageSize);

    /**
     * 充值记录
     */
    @Headers({"baseUrl:asset"})
    @GET("api/dw/deposits/")
    Call<DepositWithdrawalVO> getDepositList(@Query("status") Integer status, @Query("coin_code") String coinCode, @Query("page") int page);

    /**
     * 提现记录
     */
    @Headers({"baseUrl:asset"})
    @GET("api/dw/withdrawals/")
    Call<DepositWithdrawalVO> getWithdrawalList(@Query("status") Integer status, @Query("coin_code") String coinCode, @Query("page") int page);

    /**
     * 获取币种信息
     */
    @Headers({"baseUrl:asset"})
    @GET("api/coin/coinBase/{coinCode}")
    Call<CoinBaseVO> getCoinInfo(@Path("coinCode") String coinCode);

    /**
     * 获取充值地址
     */
    @Headers({"baseUrl:asset"})
    @GET("api/dw/getDepositAddress/{coinCode}")
    Call<JSONObject> getDepositAddress(@Path("coinCode") String coinCode);

    /**
     * 获取提现地址
     */
    @Headers({"baseUrl:asset"})
    @GET("api/dw/getWithdrawalAddress/{coinCode}")
    Call<JSONObject> getWithdrawalAddress(@Path("coinCode") String coinCode);

    /**
     * 邮箱发送
     */
    @Headers({"baseUrl:user"})
    @POST("api/withdrawal/email/sender/")
    Call<JSONObject> sendWithdrawalEmail(@Body JSONObject jsonObject);

    /**
     * 发送ssm验证码
     */
    @Headers({"baseUrl:user"})
    @POST("api/withdrawal/sms/sender/")
    Call<JSONObject> sendWithdrawalSms(@Body JSONObject jsonObject);

    /**
     * 提现
     */
    @Headers({"baseUrl:asset"})
    @POST("api/dw/withdrawalOrder")
    Call<JSONObject> withdrawal(@Body WithdrawalDTO withdrawalDTO);

    /**
     * 添加自选
     */
    @Headers({"baseUrl:asset"})
    @POST("api/optional")
    Call<JSONObject> addOptional(@Body OptionalDTO optionalDTO);

    /**
     * 取消自选
     */
    @Headers({"baseUrl:asset"})
    @DELETE("api/optional/{coinMarketCode}")
    Call<JSONObject> cannelOptional(@Path("coinMarketCode") String coinMarketCode);

    /**
     * 获取自选列表
     */
    @Headers({"baseUrl:asset"})
    @GET("api/optional/list")
    Call<OptionVO> getOption();

    /**
     * 忘记密码第一步
     */
    @Headers({"baseUrl:user"})
    @POST("api/app/forget/password/")
    Call<JSONObject> forgetPwdNext(@Body UserDTO userDTO);

    /**
     * 忘记密码第二步
     */
    @Headers({"baseUrl:user"})
    @POST("api/user/forget/password/")
    Call<JSONObject> forgetPwdComplete(@Body UserDTO userDTO);

    /**
     * 修改密码
     */
    @Headers({"baseUrl:user"})
    @POST("api/user/password/")
    Call<JSONObject> confirmResetPwd(@Body UserDTO userDTO);

    /**
     * 获取上传图片 所需要参数
     */
    @Headers({"baseUrl:user"})
    @GET("api/oss/")
    Call<JSONObject> getPictureParam();

    /**
     * 上传图片
     */
    @Multipart
    @Headers({"baseUrl:other"})
    @POST("https://futures.utex.sg")
    Call<JSONObject> uploadPicture(@Part("description") RequestBody description, @Part MultipartBody.Part file);

    /**
     * 激活账号
     */
    @Headers({"baseUrl:user"})
    @POST("api/app/user/activate/")
    Call<JSONObject> activate(@Body UserDTO userDTO);

    /**
     * 切换手机状态
     */
    @Headers({"baseUrl:user"})
    @PUT("api/user/safe/tel/")
    Call<JSONObject> switchPhoneStatus(@Body LoginVerificationDTO loginVerificationDTO);

    /**
     * 切换谷歌状态
     */
    @Headers({"baseUrl:user"})
    @PUT("api/user/safe/google/")
    Call<JSONObject> switchGoogleStatus(@Body LoginVerificationDTO loginVerificationDTO);

    /**
     * 获取首次绑定信息
     */
    @Headers({"baseUrl:user"})
    @GET("api/user/safe/google/")
    Call<GoogleVO> getGoogleBindInfo();

    /**
     * 发送激活验证码
     */
    @Headers({"baseUrl:user"})
    @POST("api/app/user/activate/sender/")
    Call<JSONObject> sendActivateEmail(@Body SendCodeDTO sendCodeDTO);

    /**
     * 发送短信验证码
     */
    @Headers({"baseUrl:user"})
    @POST("api/sms/sender/")
    Call<JSONObject> sendTelCode(@Body SendCodeDTO sendCodeDTO);

    /**
     * 绑定手机验证码
     */
    @Headers({"baseUrl:user"})
    @POST("api/user/safe/tel/")
    Call<JSONObject> confirmBindPhone(@Body BindTelDTO bindTelDTO);

    /**
     * 获取国家手机号
     */
    @Headers({"baseUrl:platform"})
    @GET("api/country/")
    Call<PhoneCountryNumVO> getPhoneCountry();

    /**
     * 绑定谷歌
     */
    @Headers({"baseUrl:user"})
    @POST("api/user/safe/google/")
    Call<JSONObject> bindGoogle(@Body BindGoogleDTO bindGoogleDTO);

    /**
     * 获取app参数
     */
    @Headers({"baseUrl:user"})
    @GET("api/app/oss")
    Call<JSONObject> getAppOSSParam();

    /**
     * 中国身份认证
     */
    @Headers({"baseUrl:user"})
    @POST("api/user/china/verification/")
    Call<JSONObject> chinaSumbit(@Body IdentitySumbitDTO identitySumbitDTO);

    /**
     * 其他国家认证
     */
    @Headers({"baseUrl:user"})
    @POST("api/user/other/verification/")
    Call<JSONObject> otherCountrySumbit(@Body IdentitySumbitDTO identitySumbitDTO);

    /**
     * 解锁登录请求
     */
    @Headers({"baseUrl:user"})
    @POST("api/app/user/login/")
    Call<JSONObject> requestToken(@Body UserDTO userDTO);

    /**
     * 解锁切换 使用密码校验
     */
    @Headers({"baseUrl:user"})
    @POST("api/user/checkpassword/")
    Call<JSONObject> checkPassword(@Body UserDTO userDTO);

    /**
     * 检查版本
     */
    @Headers({"baseUrl:platform"})
    @POST("api/checkappversion/")
    Call<CheckAppVersionVO> checkAppVersion(@Body CheckAppVersionDTO checkAppVersionDTO);

    /**
     * 获取汇率
     */
    @Headers({"baseUrl:rate"})
    @GET("public/v1/rate")
    Call<JSONObject> getExchangeRateList();

    /**
     * 取消提现
     */
    @Headers({"baseUrl:asset"})
    @POST("api/dw/cancelWithdrawal/{id}")
    Call<JSONObject> cannelWithdrawal(@Path("id") String id);

    /**
     * 获取手续费提币
     */
    @Headers({"baseUrl:asset"})
    @GET("api/dw/getWithdrawalFee")
    Call<WithdrawalFeeVo> getRateData();

    /**
     * 校验用户名
     */
    @Headers({"baseUrl:user"})
    @POST("api/user/userVerify")
    Call<UserVerifyVO> checkUser(@Body UserDTO userDTO);

    /**
     * 绑定邮箱
     */
    @Headers({"baseUrl:user"})
    @POST("api/user/safe/email")
    Call<JSONObject> bindEmail(@Body JSONObject jsonObject);

    /**
     * 修改邮箱开启关闭
     */
    @Headers({"baseUrl:user"})
    @PUT("api/user/safe/email")
    Call<JSONObject> switchEmailStatus(@Body LoginVerificationDTO loginVerificationDTO);

    /**
     * 用户糖果流水分页列表
     */
    @Headers({"baseUrl:asset"})
    @GET("api/activity/user/flow")
    Call<DistributeRecordVO> getDistributeRecordData(@Query("page") Integer page);

    /**
     * 站内划转
     */
    @Headers({"baseUrl:asset"})
    @POST("api/balance/innerTransfer")
    Call<JSONObject> transfer(@Body JSONObject jsonObject);

    /**
     * 站内记录
     */
    @Headers({"baseUrl:asset"})
    @GET("api/balance/innerTransfers")
    Call<StationVO> getStationList(@Query("page") int page, @Query("side") Integer side, @Query("coin_code") String market);

}
