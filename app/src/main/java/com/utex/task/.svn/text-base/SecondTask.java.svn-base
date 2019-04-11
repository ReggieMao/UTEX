package com.utex.task;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Demon on 2018/6/7.
 * 读秒任务
 */
public class SecondTask {

    /**
     * 注册
     */
    private static int registerSecond = 60;

    /**
     * 忘记密码
     */
    private static int forgetSecond = 60;

    /**
     * 激活账号
     */
    private static int activateSecond = 60;

    /**
     * 绑定手机
     */
    private static int bindPhoneSecond = 60;

    private final static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                    //注册
                    if (registerSecond > 0) {
                        registerSecond--;
                        handler.sendEmptyMessageDelayed(0, 1000);
                        //推到订阅监听中
                    }
                    if (secondListener != null) {
                        secondListener.upDateView(registerSecond);
                    }
                    break;
                case 1:
                    //忘记密码
                    if (forgetSecond > 0) {
                        forgetSecond--;
                        handler.sendEmptyMessageDelayed(1, 1000);
                        //推到订阅监听中
                    }
                    if (secondListener != null) {
                        secondListener.upDateForgetPwdView(forgetSecond);
                    }

                    break;
                case 2:
                    //激活账号
                    if (activateSecond > 0) {
                        activateSecond--;
                        handler.sendEmptyMessageDelayed(2, 1000);
                        //推到订阅监听中
                    }
                    if (secondListener != null) {
                        secondListener.upDateActivateView(activateSecond);
                    }
                    break;
                case 3:
                    if (bindPhoneSecond > 0) {
                        bindPhoneSecond--;
                        handler.sendEmptyMessageDelayed(3, 1000);
                        //推到订阅监听中
                    }
                    if (secondListener != null) {
                        secondListener.upDateBindPhoneView(bindPhoneSecond);
                    }
                    break;
            }

        }
    };

    private static SecondListener secondListener;


    public static void setListener(SecondListener secondListener) {
        SecondTask.secondListener = secondListener;
    }

    /**
     * 0 注册
     * 1 忘记密码
     * 2 激活账号
     *
     * @param type
     */
    public static void startTask(int type) {
        switch (type) {
            case 0:
                registerSecond = 60;
                break;
            case 1:
                forgetSecond = 60;
                break;
            case 2:
                activateSecond = 60;
                break;
            case 3:
                bindPhoneSecond = 60;
                break;
        }
        handler.sendEmptyMessage(type);
    }

    public interface SecondListener {
        void upDateView(int second);

        void upDateForgetPwdView(int second);

        void upDateActivateView(int second);

        void upDateBindPhoneView(int second);
    }

    public static int getRegisterSecond() {
        return registerSecond;
    }

    public static void setRegisterSecond(int registerSecond) {
        SecondTask.registerSecond = registerSecond;
    }

    public static int getForgetSecond() {
        return forgetSecond;
    }

    public static void setForgetSecond(int forgetSecond) {
        SecondTask.forgetSecond = forgetSecond;
    }

    public static int getActivateSecond() {
        return activateSecond;
    }

    public static void setActivateSecond(int activateSecond) {
        SecondTask.activateSecond = activateSecond;
    }
}
