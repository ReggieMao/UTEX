package com.utex.mvp.mine.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.utex.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PictureActivity extends Activity {

    @BindView(R.id.pv_picture)
    PhotoView pvPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);

        String path = getIntent().getStringExtra("path");

        Glide.with(this)
                .load(path)
                .into(pvPicture);

        pvPicture.enable();
    }


    @OnClick({R.id.rl_picture, R.id.pv_picture})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pv_picture:
            case R.id.rl_picture:
                finish();
                break;
        }
    }
}
