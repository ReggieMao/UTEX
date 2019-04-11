package com.utex.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.utex.R;
import com.utex.listener.ETtextChangeListener;
import com.utex.mvp.trad.view.ETFilter;
import com.utex.utils.ArithmeticUtil;
import com.utex.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SnappingStepper extends FrameLayout {

    /**
     * 数量
     */
    double mGoodsNumber = 1;

    int mMaxCount;

    @BindView(R.id.tv_number)
    EditText tvNumber;

    private UpdateGoodsNumberListener mUpdateGoodsNumberListener;

    private int scale;

    public SnappingStepper(Context context) {
        this(context, null);
    }

    public SnappingStepper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SnappingStepper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.item_car_number_add_sub_layout, this, false);
        ButterKnife.bind(this, rootView);
        addView(rootView);
        tvNumber.addTextChangedListener(new ETtextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                super.onTextChanged(charSequence, i, i1, i2);
                if (charSequence.toString().length() >= 2 && charSequence.toString().startsWith("0") && !charSequence.toString().contains(".")) {
                    tvNumber.setText(charSequence.toString().substring(1));
                    tvNumber.setSelection(1);
                }

                if (charSequence.toString().contains(".")) {
                    String[] split = charSequence.toString().split("\\.");
                    if (split.length == 2) {
                        if (split[0].length() >= 2 && split[0].toString().startsWith("0")) {
                            tvNumber.setText(charSequence.toString().substring(1));
                            tvNumber.setSelection(1);
                        }
                    }
                }

                try {
                    mGoodsNumber = Double.parseDouble(tvNumber.getText().toString());
                } catch (Exception e) {
                    mGoodsNumber = 0;
                }


                mUpdateGoodsNumberListener.updateGoodsNumber(mGoodsNumber);
            }
        });

        tvNumber.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
    }

    @Override
    public boolean hasFocus() {
        return tvNumber.hasFocus();
    }

    @OnClick({R.id.tv_add, R.id.tv_sub})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                addNumber();
                break;
            case R.id.tv_sub:
                subNumber();
                break;
        }
        updateGoodsNumber();
    }

    /**
     * 更新商品数量
     */
    public void updateGoodsNumber() {
        tvNumber.setText(Utils.eliminateZero(mGoodsNumber));
        if (mUpdateGoodsNumberListener != null) {
            mUpdateGoodsNumberListener.updateGoodsNumber(mGoodsNumber);
        }
    }

    public void addNumber() {
        try {
            String[] split = Utils.eliminateZero(mGoodsNumber).split("\\.");
            int length = split[1].length();
            mGoodsNumber = ArithmeticUtil.add(mGoodsNumber, ArithmeticUtil.div(1, Math.pow(10, length)));
        } catch (Exception e) {
            ++mGoodsNumber;
        }

        if (mUpdateGoodsNumberListener != null) {
            mUpdateGoodsNumberListener.update(mGoodsNumber);
        }

    }

    public void subNumber() {
        try {
            String[] split = Utils.eliminateZero(mGoodsNumber).split("\\.");
            int length = split[1].length();
            mGoodsNumber = ArithmeticUtil.sub(mGoodsNumber, ArithmeticUtil.div(1, Math.pow(10, length)));
        } catch (Exception e) {
            mGoodsNumber = (mGoodsNumber - 1 < 1) ? 1 : mGoodsNumber - 1;
        }

        if (mUpdateGoodsNumberListener != null) {
            mUpdateGoodsNumberListener.update(mGoodsNumber);
        }
    }

    /**
     * 获取商品数量
     *
     * @return
     */
    public double getGoodsNumber() {
        return mGoodsNumber;
    }

    public void setGoodsNumber(double goodsNumber) {
        mGoodsNumber = goodsNumber;
        updateGoodsNumber();
    }

    public void setUpdateGoodsNumberListener(UpdateGoodsNumberListener listener) {
        mUpdateGoodsNumberListener = listener;
    }

    public void setETLength(int length, double max_amount) {
        this.scale = length;
        tvNumber.setFilters(new InputFilter[]{new ETFilter(scale, max_amount)});
        tvNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.x10));
    }


    /**
     * 更新商品数量监听器
     */
    public interface UpdateGoodsNumberListener {
        void updateGoodsNumber(double number);

        void update(double mGoodsNumber);
    }
}