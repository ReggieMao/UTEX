package com.utex.widget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.utex.R;
import com.utex.listener.ETtextChangeListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Demon on 2018/7/23.
 * 验证码输入view
 */
public class CodeInputView extends RelativeLayout implements View.OnKeyListener {

    @BindView(R.id.et_code_1)
    EditText etCode1;

    @BindView(R.id.et_code_2)
    EditText etCode2;

    @BindView(R.id.et_code_3)
    EditText etCode3;

    @BindView(R.id.et_code_4)
    EditText etCode4;

    @BindView(R.id.et_code_5)
    EditText etCode5;

    @BindView(R.id.et_code_6)
    EditText etCode6;

    public CodeInputView(Context context) {
        this(context, null);
    }

    public CodeInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CodeInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.item_code_layout, this, false);
        ButterKnife.bind(this, rootView);
        addView(rootView);

        etCode1.addTextChangedListener(new ETtextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(etCode1.getText())) {
                    changeEnable(2);
                }
            }
        });

        etCode2.addTextChangedListener(new ETtextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                super.onTextChanged(charSequence, i, i1, i2);
                if (!TextUtils.isEmpty(etCode2.getText())) {
                    changeEnable(3);
                }
            }
        });

        etCode3.addTextChangedListener(new ETtextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                super.onTextChanged(charSequence, i, i1, i2);
                if (!TextUtils.isEmpty(etCode3.getText())) {
                    changeEnable(4);
                }
            }
        });

        etCode4.addTextChangedListener(new ETtextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                super.onTextChanged(charSequence, i, i1, i2);
                if (!TextUtils.isEmpty(etCode4.getText())) {
                    changeEnable(5);
                }
            }
        });

        etCode5.addTextChangedListener(new ETtextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                super.onTextChanged(charSequence, i, i1, i2);
                if (!TextUtils.isEmpty(etCode5.getText())) {
                    changeEnable(6);
                }
            }
        });

        etCode6.addTextChangedListener(new ETtextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                super.onTextChanged(charSequence, i, i1, i2);

                if (!TextUtils.isEmpty(etCode6.getText()) && codeInputViewListener != null) {
                    //直接请求接口
                    codeInputViewListener.activate(etCode1.getText().toString() + etCode2.getText().toString() + etCode3.getText().toString() + etCode4.getText().toString()
                            + etCode5.getText().toString() + etCode6.getText().toString());
                }
            }
        });

        etCode1.setOnKeyListener(this);
        etCode2.setOnKeyListener(this);
        etCode3.setOnKeyListener(this);
        etCode4.setOnKeyListener(this);
        etCode5.setOnKeyListener(this);
        etCode6.setOnKeyListener(this);


        Activity activity = (Activity) getContext();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        etCode1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        changeEnable(1);

    }


    private void changeEnable(int position) {
        setAllEnabledFalse();
        switch (position) {
            case 1:
                etCode1.setFocusable(true);
                etCode1.setFocusableInTouchMode(true);
                etCode1.requestFocus();
                etCode1.findFocus();
                break;
            case 2:
                etCode2.setFocusable(true);
                etCode2.setFocusableInTouchMode(true);
                etCode2.requestFocus();
                etCode2.findFocus();
                break;
            case 3:
                etCode3.setFocusable(true);
                etCode3.setFocusableInTouchMode(true);
                etCode3.requestFocus();
                etCode3.findFocus();
                break;
            case 4:
                etCode4.setFocusable(true);
                etCode4.setFocusableInTouchMode(true);
                etCode4.requestFocus();
                etCode4.findFocus();
                break;
            case 5:
                etCode5.setFocusable(true);
                etCode5.setFocusableInTouchMode(true);
                etCode5.requestFocus();
                etCode5.findFocus();
                break;
            case 6:
                etCode6.setFocusable(true);
                etCode6.setFocusableInTouchMode(true);
                etCode6.requestFocus();
                etCode6.findFocus();
                break;
        }


    }

    private void setAllEnabledFalse() {
        etCode1.setFocusable(false);
        etCode2.setFocusable(false);
        etCode3.setFocusable(false);
        etCode4.setFocusable(false);
        etCode5.setFocusable(false);
        etCode6.setFocusable(false);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_UP) {

            if (etCode1.isFocusable()) {
                //代表删除这个
                etCode1.setText("");
                return true;
            }

            if (etCode2.isFocusable()) {
                //代表删除这个
                etCode1.setText("");
                changeEnable(1);
                return true;
            }

            if (etCode3.isFocusable()) {
                //代表删除这个
                etCode2.setText("");
                changeEnable(2);
                return true;
            }

            if (etCode4.isFocusable()) {
                //代表删除这个
                etCode3.setText("");
                changeEnable(3);
                return true;
            }

            if (etCode5.isFocusable()) {
                //代表删除这个
                etCode4.setText("");
                changeEnable(4);
                return true;
            }

            if (etCode6.isFocusable()) {
                //代表删除这个
                etCode5.setText("");
                changeEnable(5);
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    private CodeInputViewListener codeInputViewListener;

    public void setCodeInputViewListener(CodeInputViewListener codeInputViewListener) {
        this.codeInputViewListener = codeInputViewListener;
    }

    public void clearData() {
        etCode1.setText("");
        etCode2.setText("");
        etCode3.setText("");
        etCode4.setText("");
        etCode5.setText("");
        etCode6.setText("");
        changeEnable(1);
    }


    public interface CodeInputViewListener {
        void activate(String code);
    }

}
