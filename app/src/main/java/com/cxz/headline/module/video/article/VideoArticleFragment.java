package com.cxz.headline.module.video.article;

import android.os.Bundle;

import com.cxz.headline.R;
import com.cxz.headline.app.App;
import com.cxz.headline.base.BaseFragment;
import com.cxz.headline.di.component.DaggerVideoArticleFragmentComponent;
import com.cxz.headline.di.module.VideoArticleFragmentModule;

/**
 * Created by chenxz on 2018/1/7.
 */

public class VideoArticleFragment extends BaseFragment<VideoArticlePresenter> implements VideoArticleContract.View {

    private static String CATEGORY_ID = "categoryId";

    private String categoryId;

    public static VideoArticleFragment newInstance(String categoryId) {
        VideoArticleFragment fragment = new VideoArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY_ID, categoryId);
        fragment.setArguments(bundle);
        return fragment;
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
        return R.layout.fragment_article;
    }

    @Override
    protected void initInject() {
        DaggerVideoArticleFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .videoArticleFragmentModule(new VideoArticleFragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        categoryId = getArguments().getString(CATEGORY_ID, "");

    }

    @Override
    protected void lazyLoad() {

    }
}
