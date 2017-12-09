package com.cxz.headline.module.main;

import android.util.Log;

import com.cxz.headline.base.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * Created by chenxz on 2017/12/9.
 */

public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View view) {
        super(model, view);
        Log.e(TAG, "MainPresenter: 构造了" );
    }

}
