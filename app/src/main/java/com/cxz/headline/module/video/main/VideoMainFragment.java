package com.cxz.headline.module.video.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.cxz.headline.R;
import com.cxz.headline.adapter.base.BasePagerAdapter;
import com.cxz.headline.app.App;
import com.cxz.headline.base.BaseFragment;
import com.cxz.headline.di.component.DaggerVideoMainFragmentComponent;
import com.cxz.headline.di.module.VideoMainFragmentModule;
import com.cxz.headline.module.video.article.VideoArticleFragment;
import com.cxz.headline.util.SettingUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenxz on 2018/1/7.
 */

public class VideoMainFragment extends BaseFragment<VideoMainPresenter> implements VideoMainContract.View {

    @BindView(R.id.tab_layout_video)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager_video)
    ViewPager mViewPager;

    private List<Fragment> mFragmentLists;
    private List<String> mTitleLists;
    private BasePagerAdapter mAdapter;
    // 用来存放 Fragment，以便下次直接取
    private HashMap<String, Fragment> mHashMap = new HashMap<>();

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
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setBackgroundColor(SettingUtil.getInstance().getColor());
        String categoryId[] = App.getContext().getResources().getStringArray(R.array.tab_video_id);
        String categoryName[] = App.getContext().getResources().getStringArray(R.array.tab_video_name);
        mFragmentLists = new ArrayList<>();
        for (int i = 0; i < categoryId.length; i++) {
            Fragment fragment = VideoArticleFragment.newInstance(categoryId[i]);
            mFragmentLists.add(fragment);
        }
        mAdapter = new BasePagerAdapter(getChildFragmentManager(), mFragmentLists, categoryName);
        mViewPager.setOffscreenPageLimit(categoryId.length);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (instance != null) {
            instance = null;
        }
    }
}
