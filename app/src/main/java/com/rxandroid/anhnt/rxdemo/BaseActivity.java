package com.rxandroid.anhnt.rxdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.rxandroid.anhnt.rxdemo.utils.ColorUtils;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getFullScreen()) {
            ColorUtils.setFullScreen(this);
        }
        setContentView(getContentLayout());
        ColorUtils.changeStatusBarColor(this, getStatusBarColor());
        initComponents();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeEnable());
            getSupportActionBar().setDisplayShowHomeEnabled(showHomeEnable());
        }
    }

    protected abstract boolean getFullScreen();

    protected abstract boolean showHomeEnable();

    public abstract int getContentLayout();

    public abstract int getStatusBarColor();

    public abstract void initComponents();

}
