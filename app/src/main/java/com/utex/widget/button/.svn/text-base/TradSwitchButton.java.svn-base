package com.utex.widget.button;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.utex.R;
import com.utex.utils.Utils;


/**
 * QQ Group 274306954
 */
public class TradSwitchButton extends View {
    private final int DEFAULT_COLOR_PRIMARY = 0xFFC8CCC9;
    private final int DEFAULT_COLOR_PRIMARY_DARK = 0xFFC8CCC9;
    private final float RATIO_ASPECT = 0.68f;
    private final float ANIMATION_SPEED = 0.1f; // (0,1]

    private static final int STATE_SWITCH_ON = 4; // you change value you die
    private static final int STATE_SWITCH_ON2 = 3;
    private static final int STATE_SWITCH_OFF2 = 2;
    private static final int STATE_SWITCH_OFF = 1;

    private final AccelerateInterpolator interpolator = new AccelerateInterpolator(2);
    private final Paint paint = new Paint();
    private final Path sPath = new Path();
    private final Path bPath = new Path();
    private final RectF bRectF = new RectF();
    private final float textSize;
    private float sAnim, bAnim;
    private RadialGradient shadowGradient;

    private int state;
    private int lastState;
    private boolean isCanVisibleDrawing = false;
    private OnClickListener mOnClickListener;
    private int colorPrimary;
    private int colorPrimaryDark;
    private boolean hasShadow;
    private boolean isOpened;

    private int mWidth, mHeight;
    private int actuallyDrawingAreaLeft;
    private int actuallyDrawingAreaRight;
    private int actuallyDrawingAreaTop;
    private int actuallyDrawingAreaBottom;

    private float sWidth, sHeight;
    private float sLeft, sTop, sRight, sBottom;
    private float sCenterX, sCenterY;
    private float sScale;

    private float bOffset;
    private float bRadius, bStrokeWidth;
    private float bWidth;
    private float bLeft, bTop, bRight, bBottom;
    private float bOnLeftX, bOn2LeftX, bOff2LeftX, bOffLeftX;

    private float shadowReservedHeight;
    /**
     * 按钮大小
     */
    private int barSize;
    /**
     * 开关
     * true 打开
     * false 关闭
     */
    private boolean isOpen = false;

    public TradSwitchButton(Context context) {
        this(context, null);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public TradSwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwitchView);
        colorPrimary = a.getColor(R.styleable.SwitchView_primaryColor, DEFAULT_COLOR_PRIMARY);
        colorPrimaryDark = a.getColor(R.styleable.SwitchView_primaryColorDark, DEFAULT_COLOR_PRIMARY_DARK);
        hasShadow = a.getBoolean(R.styleable.SwitchView_hasShadow, true);
        isOpened = a.getBoolean(R.styleable.SwitchView_isOpened, false);
        textSize = a.getDimension(R.styleable.SwitchView_textSize, 30);
        state = isOpened ? STATE_SWITCH_ON : STATE_SWITCH_OFF;
        lastState = state;
        a.recycle();

        if (colorPrimary == DEFAULT_COLOR_PRIMARY && colorPrimaryDark == DEFAULT_COLOR_PRIMARY_DARK) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    TypedValue primaryColorTypedValue = new TypedValue();
                    context.getTheme().resolveAttribute(android.R.attr.colorPrimary, primaryColorTypedValue, true);
                    if (primaryColorTypedValue.data > 0) colorPrimary = primaryColorTypedValue.data;
                    TypedValue primaryColorDarkTypedValue = new TypedValue();
                    context.getTheme().resolveAttribute(android.R.attr.colorPrimaryDark, primaryColorDarkTypedValue, true);
                    if (primaryColorDarkTypedValue.data > 0)
                        colorPrimaryDark = primaryColorDarkTypedValue.data;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setColor(int newColorPrimary, int newColorPrimaryDark) {
        colorPrimary = newColorPrimary;
        colorPrimaryDark = newColorPrimaryDark;
        invalidate();
    }

    public void setShadow(boolean shadow) {
        hasShadow = shadow;
        invalidate();
    }

    public boolean isOpened() {
        return isOpened;
    }


    public void setOpened(boolean isOpened) {
        isOpen = isOpened;
        this.isOpened = isOpened;
//        switchListener.switchState(isOpen);
        state = STATE_SWITCH_ON;

        postInvalidate();

    }

    public void toggleSwitch(boolean isOpened) {
        int wishState = isOpened ? STATE_SWITCH_ON : STATE_SWITCH_OFF;
        if (wishState == state) {
            return;
        }

        if ((wishState == STATE_SWITCH_ON && (state == STATE_SWITCH_OFF || state == STATE_SWITCH_OFF2))
                || (wishState == STATE_SWITCH_OFF && (state == STATE_SWITCH_ON || state == STATE_SWITCH_ON2))) {
            sAnim = 1;
        }
        bAnim = 1;
        refreshState(wishState);
    }

    public void refreshState(int newState) {
        if (!isOpened && newState == STATE_SWITCH_ON) {
            isOpened = true;
            if (switchListener != null) {
                switchListener.switchState(isOpen);
            }
        } else if (isOpened && newState == STATE_SWITCH_OFF) {
            isOpened = false;
            if (switchListener != null) {
                switchListener.switchState(isOpen);
            }

        }
        lastState = state;
        state = newState;
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int resultWidth;
        if (widthMode == MeasureSpec.EXACTLY) {
            resultWidth = widthSize;
        } else {
            resultWidth = (int) (56 * getResources().getDisplayMetrics().density + 0.5f)
                    + getPaddingLeft() + getPaddingRight();
            if (widthMode == MeasureSpec.AT_MOST) {
                resultWidth = Math.min(resultWidth, widthSize);
            }
        }

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int resultHeight;
        if (heightMode == MeasureSpec.EXACTLY) {
            resultHeight = heightSize;
        } else {
            int selfExpectedResultHeight = (int) (resultWidth * RATIO_ASPECT) + getPaddingTop() + getPaddingBottom();
            resultHeight = selfExpectedResultHeight;
            if (heightMode == MeasureSpec.AT_MOST) {
                resultHeight = Math.min(resultHeight, heightSize);
            }
        }

        setMeasuredDimension(resultWidth, resultHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        isCanVisibleDrawing = mWidth > getPaddingLeft() + getPaddingRight() && mHeight > getPaddingTop() + getPaddingBottom();

        if (isCanVisibleDrawing) {
            int actuallyDrawingAreaWidth = mWidth - getPaddingLeft() - getPaddingRight();
            int actuallyDrawingAreaHeight = mHeight - getPaddingTop() - getPaddingBottom();

            if (actuallyDrawingAreaWidth * RATIO_ASPECT < actuallyDrawingAreaHeight) {
                actuallyDrawingAreaLeft = getPaddingLeft();
                actuallyDrawingAreaRight = mWidth - getPaddingRight();
                int heightExtraSize = (int) (actuallyDrawingAreaHeight - actuallyDrawingAreaWidth * RATIO_ASPECT);
                actuallyDrawingAreaTop = getPaddingTop() + heightExtraSize / 2;
                actuallyDrawingAreaBottom = getHeight() - getPaddingBottom() - heightExtraSize / 2;
            } else {
                int widthExtraSize = (int) (actuallyDrawingAreaWidth - actuallyDrawingAreaHeight / RATIO_ASPECT);
                actuallyDrawingAreaLeft = getPaddingLeft() + widthExtraSize / 2;
                actuallyDrawingAreaRight = getWidth() - getPaddingRight() - widthExtraSize / 2;
                actuallyDrawingAreaTop = getPaddingTop();
                actuallyDrawingAreaBottom = getHeight() - getPaddingBottom();
            }

            shadowReservedHeight = (int) ((actuallyDrawingAreaBottom - actuallyDrawingAreaTop) * 0.09f);
            sLeft = actuallyDrawingAreaLeft;
            sTop = actuallyDrawingAreaTop + shadowReservedHeight;
            sRight = actuallyDrawingAreaRight;
            sBottom = actuallyDrawingAreaBottom - shadowReservedHeight;

            sWidth = sRight - sLeft;
            sHeight = sBottom - sTop;
            sCenterX = (sRight + sLeft) / 2;
            sCenterY = (sBottom + sTop) / 2;

            bLeft = sLeft;
            bTop = sTop;
            bBottom = sBottom;
            bWidth = sBottom - sTop;
            bRight = sLeft + bWidth;
            final float halfHeightOfS = bWidth / 2; // OfB
            bRadius = halfHeightOfS * 0.95f;
            bOffset = bRadius * 0.2f; // offset of switching
            bStrokeWidth = (halfHeightOfS - bRadius) * 2;
            bOnLeftX = sRight - bWidth;
            bOn2LeftX = bOnLeftX - bOffset;
            bOffLeftX = sLeft;
            bOff2LeftX = bOffLeftX + bOffset;
            sScale = 1 - bStrokeWidth / sHeight;

            sPath.reset();
            RectF sRectF = new RectF();
            sRectF.top = sTop;
            sRectF.bottom = sBottom;
            sRectF.left = sLeft;
            sRectF.right = sLeft + sHeight;
            sPath.arcTo(sRectF, 90, 180);
            sRectF.left = sRight - sHeight;
            sRectF.right = sRight;
            sPath.arcTo(sRectF, 270, 180);
            sPath.close();

            bRectF.left = bLeft;
            bRectF.right = bRight;
            bRectF.top = bTop + bStrokeWidth / 2;
            bRectF.bottom = bBottom - bStrokeWidth / 2;
            float bCenterX = (bRight + bLeft) / 2;
            float bCenterY = (bBottom + bTop) / 2;

            shadowGradient = new RadialGradient(bCenterX, bCenterY, bRadius, 0xff000000, 0x00000000, Shader.TileMode.CLAMP);
        }

        barSize = (mWidth - 5) / 2;
    }

    private void calcBPath(float percent) {
        bPath.reset();
        bRectF.left = bLeft + bStrokeWidth / 2;
        bRectF.right = bRight - bStrokeWidth / 2;
        bPath.arcTo(bRectF, 90, 180);
        bRectF.left = bLeft + percent * bOffset + bStrokeWidth / 2;
        bRectF.right = bRight + percent * bOffset - bStrokeWidth / 2;
        bPath.arcTo(bRectF, 270, 180);
        bPath.close();
    }

    private float calcBTranslate(float percent) {
        float result = 0;
        switch (state - lastState) {
            case 1:
                if (state == STATE_SWITCH_OFF2) {
                    result = bOffLeftX; // off -> off2
                } else if (state == STATE_SWITCH_ON) {
                    result = bOnLeftX - (bOnLeftX - bOn2LeftX) * percent; // on2 -> on
                }
                break;
            case 2:
                if (state == STATE_SWITCH_ON) {
                    result = bOnLeftX - (bOnLeftX - bOffLeftX) * percent; // off2 -> on
                } else if (state == STATE_SWITCH_ON) {
                    result = bOn2LeftX - (bOn2LeftX - bOffLeftX) * percent;  // off -> on2
                }
                break;
            case 3:
                result = bOnLeftX - (bOnLeftX - bOffLeftX) * percent; // off -> on
                break;
            case -1:
                if (state == STATE_SWITCH_ON2) {
                    result = bOn2LeftX + (bOnLeftX - bOn2LeftX) * percent; // on -> on2
                } else if (state == STATE_SWITCH_OFF) {
                    result = bOffLeftX;  // off2 -> off
                }
                break;
            case -2:
                if (state == STATE_SWITCH_OFF) {
                    result = bOffLeftX + (bOn2LeftX - bOffLeftX) * percent;  // on2 -> off
                } else if (state == STATE_SWITCH_OFF2) {
                    result = bOff2LeftX + (bOnLeftX - bOff2LeftX) * percent;  // on -> off2
                }
                break;
            case -3:
                result = bOffLeftX + (bOnLeftX - bOffLeftX) * percent;  // on -> off
                break;
            default: // init
            case 0:
                if (state == STATE_SWITCH_OFF) {
                    result = bOffLeftX; //  off -> off
                } else if (state == STATE_SWITCH_ON) {
                    result = bOnLeftX; // on -> on
                }
                break;
        }
        return result - bOffLeftX;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isCanVisibleDrawing) return;

        //绘制 背景
        paint.setAntiAlias(true);
        final boolean isOn = (state == STATE_SWITCH_ON || state == STATE_SWITCH_ON2);
        // Draw background
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Utils.getResourceColor(getContext(), R.color.bc8ccc9_0a0a0a));
        paint.setStrokeWidth(2);
        canvas.drawRoundRect(new RectF(5, 5, mWidth - 5, mHeight - 5), 50, 50, paint);
        paint.setStyle(Paint.Style.FILL);

        sAnim = sAnim - ANIMATION_SPEED > 0 ? sAnim - ANIMATION_SPEED : 0;
        bAnim = bAnim - ANIMATION_SPEED > 0 ? bAnim - ANIMATION_SPEED : 0;
//
        final float dsAnim = interpolator.getInterpolation(sAnim);
        final float dbAnim = interpolator.getInterpolation(bAnim);
        // Draw background animation
        final float scale = sScale * (isOn ? dsAnim : 1 - dsAnim);
        final float scaleOffset = (sRight - sCenterX - bRadius) * (isOn ? 1 - dsAnim : dsAnim);
        canvas.save();
        canvas.scale(scale, scale, sCenterX + scaleOffset, sCenterY);
        paint.setColor(Utils.getResourceColor(getContext(), R.color.bc8ccc9_0a0a0a));
        canvas.drawPath(sPath, paint);
        canvas.restore();
//        // To prepare center bar path
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Utils.getResourceColor(getContext(), R.color.bffffff_13151a));

        canvas.drawRoundRect(new RectF(delX + marLeft + 7, 7, delX + barSize + marLeft, mHeight - 7), 40, 40, paint);

        paint.setColor(Utils.getResourceColor(getContext(), R.color.bafb3b0_42ffffff));
        paint.setTextSize(textSize);
        String text;
        if (isOpen) {
            //打开;
            text = Utils.getResourceString(R.string.ming_xi);
            marLeft = (mWidth - 5) / 2;
        } else {
            text = Utils.getResourceString(R.string.wu_dang);
        }
        canvas.drawText(text, delX + marLeft + 5 + barSize * 0.26f, mHeight * 0.67f, paint);

        if (delX == 0) {
            int color = paint.getColor();
            paint.setColor(Utils.getResourceColor(getContext(), R.color.bffffff_4cffffff));

            if (!isOpen) {
                //开关
                canvas.drawText(Utils.getResourceString(R.string.ming_xi), delX + (mWidth - 5) / 2 + 5 + barSize * 0.26f, mHeight * 0.67f, paint);
            } else {
                canvas.drawText(Utils.getResourceString(R.string.wu_dang), delX + 5 + barSize * 0.26f, mHeight * 0.67f, paint);
            }
            paint.setColor(color);
        }

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(bStrokeWidth * 0.5f);
        paint.setColor(isOn ? colorPrimaryDark : Utils.getResourceColor(getContext(), R.color.bc8ccc9_0a0a0a));
        canvas.restore();

        paint.reset();
        if (sAnim > 0 || bAnim > 0) invalidate();
    }

    private float marLeft = 0;
    float x = 0;
    float delX = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if ((state == STATE_SWITCH_ON || state == STATE_SWITCH_OFF) && (sAnim * bAnim == 0)) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = event.getX();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    //移动的距离
                    delX = event.getX() - x;

                    if (delX + marLeft < 0) {
                        if (isOpen) {
                            //打开的
                            delX = -(mWidth - 5) / 2;
                        } else {
                            delX = 0;
                        }
                    }

                    if (delX + marLeft > (mWidth - 5) / 2) {
                        if (marLeft != (mWidth - 5) / 2) {
                            delX = (mWidth - 5) / 2;
                        }

                        if (marLeft == (mWidth - 5) / 2 && delX > 0) {
                            delX = 0;
                        }

                    }

                    Log.e("TAG", "moveX:" + delX);

                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    lastState = state;

                    if (Math.abs(delX) < 10) {
                        if (isOpen) {
                            isOpen = false;
                            marLeft = 0;
                        } else {
                            isOpen = true;
                            marLeft = (mWidth - 5) / 2;
                        }
                    } else {
                        if (marLeft + delX + barSize / 2 >= (mWidth - 5) / 2) {
                            marLeft = (mWidth - 5) / 2;
                            isOpen = true;
                        } else {
                            isOpen = false;
                            marLeft = 0;
                        }
                    }


                    delX = 0;
                    x = 0;

                    bAnim = 1;
                    if (state == STATE_SWITCH_OFF) {
                        refreshState(STATE_SWITCH_ON);
                        listener.toggleToOn(this);
                    } else if (state == STATE_SWITCH_ON) {
                        refreshState(STATE_SWITCH_OFF);
                        listener.toggleToOff(this);
                    }

                    if (mOnClickListener != null) {
                        mOnClickListener.onClick(this);
                    }
                    break;

            }
        }
        getParent().requestDisallowInterceptTouchEvent(true);
//        return super.onTouchEvent(event);
        return true;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        mOnClickListener = l;
    }

    private SwitchListener switchListener;

    public void setSwitchListener(SwitchListener switchListener) {
        this.switchListener = switchListener;
    }

    public interface OnStateChangedListener {
        void toggleToOn(TradSwitchButton view);

        void toggleToOff(TradSwitchButton view);
    }

    private OnStateChangedListener listener = new OnStateChangedListener() {
        @Override
        public void toggleToOn(TradSwitchButton view) {
            toggleSwitch(true);
        }

        @Override
        public void toggleToOff(TradSwitchButton view) {
            toggleSwitch(false);
        }
    };

    public void setOnStateChangedListener(OnStateChangedListener listener) {
        if (listener == null) throw new IllegalArgumentException("empty listener");
        this.listener = listener;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.isOpened = isOpened;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.isOpened = ss.isOpened;
        this.state = this.isOpened ? STATE_SWITCH_ON : STATE_SWITCH_OFF;
        invalidate();
    }

    @SuppressLint("ParcelCreator")
    static final class SavedState extends BaseSavedState {
        private boolean isOpened;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            isOpened = 1 == in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(isOpened ? 1 : 0);
        }
    }

    public interface SwitchListener {
        void switchState(boolean isOpen);
    }
}