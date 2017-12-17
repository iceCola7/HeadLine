package com.cxz.headline.module.news.article;

import android.os.Bundle;

import com.cxz.headline.R;
import com.cxz.headline.adapter.news.ArticleListAdapter;
import com.cxz.headline.app.App;
import com.cxz.headline.base.BaseFragment;
import com.cxz.headline.bean.news.NewsMultiArticleDataBean;
import com.cxz.headline.di.component.DaggerArticleFragmentComponent;
import com.cxz.headline.di.module.ArticleFragmentModule;
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

    private String categoryId;
    private ArticleListAdapter mAdapter;

    public static ArticleFragment newInstance(String categoryId) {
        ArticleFragment fragment = new ArticleFragment();
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
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
    }

    @Override
    protected void lazyLoad() {
        mPullLoadMoreRecyclerView.setRefreshing(true);
        mPresenter.loadNewsArticleList(categoryId, TimeUtil.getCurrentTimeStamp());
    }

    @Override
    public void updateNewsArticleList(List<NewsMultiArticleDataBean> lists) {
        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        if (mAdapter == null) {
            mAdapter = new ArticleListAdapter(getActivity(), R.layout.item_article_list, lists);
            mPullLoadMoreRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.setDatas(lists);
        }
    }

    @Override
    public void updateMoreNewsArticleList(List<NewsMultiArticleDataBean> lists) {
        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        mAdapter.appendDatas(lists);
    }

    @Override
    public void onRefresh() {
        mPullLoadMoreRecyclerView.setRefreshing(true);
        mPresenter.loadNewsArticleList(categoryId, TimeUtil.getCurrentTimeStamp());
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMoreNewsArticleList(categoryId, TimeUtil.getCurrentTimeStamp());
    }

}
