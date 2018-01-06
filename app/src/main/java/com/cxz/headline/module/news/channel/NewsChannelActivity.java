package com.cxz.headline.module.news.channel;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.cxz.headline.R;
import com.cxz.headline.adapter.news.NewsChannelAdapter;
import com.cxz.headline.app.App;
import com.cxz.headline.base.BaseActivity;
import com.cxz.headline.bean.news.NewsChannelBean;
import com.cxz.headline.di.component.DaggerNewsChannelActivityComponent;
import com.cxz.headline.di.module.NewsChannelActivityModule;
import com.cxz.headline.widget.helper.ItemDragHelperCallback;

import java.util.List;

import butterknife.BindView;

public class NewsChannelActivity extends BaseActivity<NewsChannelPresenter> implements NewsChannelContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private NewsChannelAdapter mAdapter;

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
        return R.layout.activity_news_channel;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initToolBar(mToolbar, true, getResources().getString(R.string.select_channel));

        List<NewsChannelBean> enableBeans = mPresenter.getChannelBeans(true);
        List<NewsChannelBean> disableBeans = mPresenter.getChannelBeans(false);

        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(manager);

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);

        mAdapter = new NewsChannelAdapter(this, touchHelper, enableBeans, disableBeans);

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = mAdapter.getItemViewType(position);
                return viewType == NewsChannelAdapter.TYPE_MY || viewType == NewsChannelAdapter.TYPE_OTHER ? 1 : 4;
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnMyChannelItemClickListener(new NewsChannelAdapter.OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                // TODO: 2018/1/6
            }
        });
    }

    @Override
    protected void initInject() {
        DaggerNewsChannelActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .newsChannelActivityModule(new NewsChannelActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.saveChannelData(mAdapter.getMyChannelItems(), mAdapter.getOtherChannelItems());
    }

}
