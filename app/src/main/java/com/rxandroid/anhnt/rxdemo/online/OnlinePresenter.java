package com.rxandroid.anhnt.rxdemo.online;

public class OnlinePresenter implements OnlineContract.Presenter {
    private OnlineContract.View mView;

    public OnlinePresenter() {

    }

    @Override
    public void setView(OnlineContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
