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
import com.cxz.recyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chenxz on 2017/12/10.
 */

public class ArticleFragment extends BaseFragment<ArticlePresenter> implements ArticleContract.View, PullLoadMoreRecyclerView.PullLoadMoreListener {

    private static String CATEGORY_ID = "categoryId";

    @BindView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
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
        mPullLoadMoreRecyclerView.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
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

        mPullLoadMoreRecyclerView.setLinearLayout();
        mPullLoadMoreRecyclerView.getRecyclerView().addItemDecoration(new SpaceItemDecoration(mContext));
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
    }

    @Override
    protected void lazyLoad() {
        showLoading();
        mPresenter.loadNewsArticleList(categoryId, TimeUtil.getCurrentTimeStamp(), true);
    }

    @Override
    public void updateNewsArticleList(List<NewsMultiArticleDataBean> lists) {
        hideLoading();
        if (mAdapter == null) {
            mAdapter = new ArticleMultiListAdapter(mContext, lists);
            mPullLoadMoreRecyclerView.setAdapter(mAdapter);
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
        mPresenter.loadNewsArticleList(categoryId, TimeUtil.getCurrentTimeStamp(), true);
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMoreNewsArticleList(categoryId, TimeUtil.getCurrentTimeStamp(), true);
    }

}
