package com.cxz.headline.module.news.article;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.cxz.headline.R;
import com.cxz.headline.adapter.news.ArticleMultiListAdapter;
import com.cxz.headline.app.App;
import com.cxz.headline.base.BaseFragment;
import com.cxz.headline.bean.news.NewsMultiArticleDataBean;
import com.cxz.headline.di.component.DaggerArticleFragmentComponent;
import com.cxz.headline.di.module.ArticleFragmentModule;
import com.cxz.headline.util.SnackbarUtil;
import com.cxz.headline.util.SpaceItemDecoration;
import com.cxz.headline.util.TimeUtil;
import com.cxz.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chenxz on 2017/12/10.
 */

public class ArticleFragment extends BaseFragment<ArticlePresenter> implements ArticleContract.View, XRecyclerView.PullLoadMoreListener {

    private static String CATEGORY_ID = "categoryId";

    @BindView(R.id.xRecyclerView)
    XRecyclerView mRecyclerView;
    @BindView(R.id.ll_content)
    LinearLayout ll_content;

    private String categoryId;
    private ArticleMultiListAdapter mAdapter;

    public static ArticleFragment newInstance(String categoryId) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY_ID, categoryId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void showLoading() {
        mRecyclerView.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.setPullLoadMoreCompleted();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showErrorMsg(String msg) {
        hideLoading();
        SnackbarUtil.showLong(ll_content, msg, 4);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_article;
    }

    @Override
    protected void initInject() {
        DaggerArticleFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .articleFragmentModule(new ArticleFragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        categoryId = getArguments().getString(CATEGORY_ID, "");

        mRecyclerView.setLinearLayout();
        mRecyclerView.getRecyclerView().addItemDecoration(new SpaceItemDecoration(mContext));
        mRecyclerView.setOnPullLoadMoreListener(this);
    }

    @Override
    protected void lazyLoad() {
        showLoading();
        mPresenter.loadNewsArticleList(categoryId, "0", true);
    }

    @Override
    public void updateNewsArticleList(List<NewsMultiArticleDataBean> lists) {
        hideLoading();
        if (mAdapter == null) {
            mAdapter = new ArticleMultiListAdapter(mContext, lists);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.setDatas(lists);
        }
    }

    @Override
    public void updateMoreNewsArticleList(List<NewsMultiArticleDataBean> lists) {
        hideLoading();
        mAdapter.appendDatas(lists);
    }

    @Override
    public void onRefresh() {
        showLoading();
        mPresenter.loadNewsArticleList(categoryId, "0", true);
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMoreNewsArticleList(categoryId, TimeUtil.getCurrentTimeStamp(), true);
    }

}
