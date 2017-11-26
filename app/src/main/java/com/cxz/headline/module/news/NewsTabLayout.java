package com.cxz.headline.module.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cxz.headline.R;
import com.cxz.headline.util.SettingUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxz on 2017/11/25.
 */

public class NewsTabLayout extends Fragment {

    private static final String TAG = "NewsTabLayout";
    private static NewsTabLayout instance = null;

    @BindView(R.id.tab_layout_news)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager_news)
    ViewPager mViewPager;
    @BindView(R.id.add_channel_iv)
    ImageView iv_add_channel;
    @BindView(R.id.header_layout)
    LinearLayout mLinearLayout;

    public static NewsTabLayout getInstance() {
        if (instance == null)
            instance = new NewsTabLayout();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_tab, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initView() {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        iv_add_channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/11/25 增加channel
            }
        });
        mLinearLayout.setBackgroundColor(SettingUtil.getInstance().getColor());
    }

    private void initData() {
        initTabs();
    }

    private void initTabs() {

    }
}
