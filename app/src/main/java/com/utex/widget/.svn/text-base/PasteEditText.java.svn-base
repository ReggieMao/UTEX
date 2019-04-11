package com.utex.widget;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.utex.widget.popuwindow.PastePopuwindow;

/**
 * Created by Demon on 2018/8/7.
 */
public class PasteEditText extends AppCompatEditText {
    public PasteEditText(Context context) {
        super(context);
        init();
    }

    public PasteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PasteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        setOnLongClickListener(new EditTextOnLongClickCallBack());
    }

    class EditTextOnLongClickCallBack implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(final View view) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                //版本大于等于21则显示系统的
                PastePopuwindow pastePopuwindow = new PastePopuwindow(view.getContext(), view);
                pastePopuwindow.setPasteListener(new PastePopuwindow.PasteListener() {
                    @Override
                    public void onclick(String str) {
                        EditText editText = (EditText) view;
                        editText.setText(str);
                    }
                });
            }
            return false;
        }
    }


}
