package com.cxz.headline.module.video;

import android.os.Bundle;

import com.cxz.headline.R;
import com.cxz.headline.app.App;
import com.cxz.headline.base.BaseFragment;
import com.cxz.headline.di.component.DaggerVideoMainFragmentComponent;
import com.cxz.headline.di.module.VideoMainFragmentModule;

/**
 * Created by chenxz on 2018/1/7.
 */

public class VideoMainFragment extends BaseFragment<VideoMainPresenter> implements VideoMainContract.View {

    private static VideoMainFragment instance;

    public static VideoMainFragment newInstance() {
        if (instance == null) {
            instance = new VideoMainFragment();
        }
        return instance;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_video_main;
    }

    @Override
    protected void initInject() {
        DaggerVideoMainFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .videoMainFragmentModule(new VideoMainFragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void lazyLoad() {

    }
}
