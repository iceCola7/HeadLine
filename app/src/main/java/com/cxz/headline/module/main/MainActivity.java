package com.cxz.headline.module.main;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.cxz.headline.R;
import com.cxz.headline.app.App;
import com.cxz.headline.base.BaseActivity;
import com.cxz.headline.di.component.DaggerMainActivityComponent;
import com.cxz.headline.di.module.MainActivityModule;
import com.cxz.headline.module.news.main.NewsMainFragment;
import com.cxz.headline.module.video.VideoMainFragment;
import com.cxz.headline.widget.helper.BottomNavigationViewHelper;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_NEWS = 0x01;
    private static final int FRAGMENT_PHOTO = 0x02;
    private static final int FRAGMENT_VIDEO = 0x03;
    private static final int FRAGMENT_MEDIA = 0x04;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    private NewsMainFragment mNewsMainFragment;
    private VideoMainFragment mVideoMainFragment;
    private int mPosition = -1;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        mToolbar.inflateMenu(R.menu.menu_activity_main);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_news:
                        showFragment(FRAGMENT_NEWS);
                        break;
                    case R.id.action_video:
                        showFragment(FRAGMENT_VIDEO);
                        break;
                }
                return true;
            }
        });

        initDrawerLayout();

        if (savedInstanceState != null) {

        } else {
            showFragment(FRAGMENT_NEWS);
        }
    }

    @Override
    protected void initInject() {
        DaggerMainActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }

    private void initDrawerLayout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            //将侧边栏顶部延伸至status bar
            mDrawerLayout.setFitsSystemWindows(true);
            //将主页面顶部延伸至status bar
            mDrawerLayout.setClipToPadding(false);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void showFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);
        mPosition = index;
        switch (index) {
            case FRAGMENT_NEWS:
                mToolbar.setTitle(R.string.app_name);
                if (mNewsMainFragment == null) {
                    mNewsMainFragment = NewsMainFragment.newInstance();
                    ft.add(R.id.container, mNewsMainFragment, NewsMainFragment.class.getName());
                } else {
                    ft.show(mNewsMainFragment);
                }
                break;
            case FRAGMENT_VIDEO:
                mToolbar.setTitle(R.string.title_video);
                if (mVideoMainFragment == null) {
                    mVideoMainFragment = VideoMainFragment.newInstance();
                    ft.add(R.id.container, mVideoMainFragment, VideoMainFragment.class.getName());
                } else {
                    ft.show(mVideoMainFragment);
                }
                break;
        }
        ft.commit();
    }

    private void hideFragment(FragmentTransaction ft) {
        // 如果不为空，就先隐藏起来
        if (mNewsMainFragment != null) {
            ft.hide(mNewsMainFragment);
        }
        if (mVideoMainFragment != null) {
            ft.hide(mVideoMainFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_activity_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            // TODO: 2017/11/25
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
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
}
