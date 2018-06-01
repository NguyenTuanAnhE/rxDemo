package com.rxandroid.anhnt.rxdemo;

public interface BasePresenter<T> {

    void setView(T view);

    void onStart();

    void onStop();
}
