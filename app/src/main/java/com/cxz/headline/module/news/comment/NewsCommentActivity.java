package com.cxz.headline.module.news.comment;

import android.os.Bundle;

import com.cxz.headline.R;
import com.cxz.headline.app.App;
import com.cxz.headline.base.BaseActivity;
import com.cxz.headline.di.component.DaggerNewsCommentActivityComponent;
import com.cxz.headline.di.module.NewsCommentActivityModule;

public class NewsCommentActivity extends BaseActivity<NewsCommentPresenter> implements NewsCommentContract.View {

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
        return R.layout.activity_news_comment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initInject() {
        DaggerNewsCommentActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .newsCommentActivityModule(new NewsCommentActivityModule(this))
                .build()
                .inject(this);
    }
}
