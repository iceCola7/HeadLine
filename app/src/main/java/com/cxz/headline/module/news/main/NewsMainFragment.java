package com.cxz.headline.module.news.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cxz.headline.R;
import com.cxz.headline.adapter.base.BasePagerAdapter;
import com.cxz.headline.app.App;
import com.cxz.headline.base.BaseFragment;
import com.cxz.headline.bean.news.NewsChannelBean;
import com.cxz.headline.database.dao.NewsChannelDao;
import com.cxz.headline.di.component.DaggerNewsMainFragmentComponent;
import com.cxz.headline.di.module.NewsMainFragmentModule;
import com.cxz.headline.module.news.article.ArticleFragment;
import com.cxz.headline.util.SettingUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenxz on 2017/11/25.
 */

public class NewsMainFragment extends BaseFragment<NewsMainPresenter> implements NewsMainContract.View {

    @BindView(R.id.tab_layout_news)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager_news)
    ViewPager mViewPager;
    @BindView(R.id.add_channel_iv)
    ImageView iv_add_channel;
    @BindView(R.id.header_layout)
    LinearLayout mLinearLayout;

    private NewsChannelDao dao;
    private List<Fragment> mFragmentLists;
    private List<String> mTitleLists;
    private BasePagerAdapter mAdapter;
    // 用来存放 Fragment，以便下次直接取
    private HashMap<String, Fragment> mHashMap = new HashMap<>();

    public static NewsMainFragment newInstance(){
        return new NewsMainFragment();
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
        return R.layout.fragment_news_main;
    }

    @Override
    protected void initInject() {
        DaggerNewsMainFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .newsMainFragmentModule(new NewsMainFragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        iv_add_channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/11/25 增加channel
            }
        });
        mLinearLayout.setBackgroundColor(SettingUtil.getInstance().getColor());

        dao = new NewsChannelDao();
        initTabs();
        mAdapter = new BasePagerAdapter(getChildFragmentManager(), mFragmentLists, mTitleLists);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mFragmentLists.size());
    }

    private void initTabs() {
        mFragmentLists = new ArrayList<>();
        mTitleLists = new ArrayList<>();
        List<NewsChannelBean> beans = dao.queryByEnable(true);
        if (beans.size() == 0) {
            dao.addInitData();
            beans = dao.queryByEnable(true);
        }
        for (NewsChannelBean bean : beans) {
            Fragment fragment = null;
            String channelId = bean.getChannelId();
            if (mHashMap.containsKey(channelId)) {
                fragment = mHashMap.get(channelId);
            } else {
                fragment = ArticleFragment.newInstance(channelId);
                mHashMap.put(channelId, fragment);
            }
            mFragmentLists.add(fragment);
            mTitleLists.add(bean.getChannelName());
        }
    }

}
