package com.cxz.headline.module.news.detail;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.cxz.headline.R;
import com.cxz.headline.app.App;
import com.cxz.headline.base.BaseActivity;
import com.cxz.headline.bean.news.NewsMultiArticleDataBean;
import com.cxz.headline.callbacks.AppBarStateChangeListener;
import com.cxz.headline.common.Constant;
import com.cxz.headline.di.component.DaggerNewsDetailActivityComponent;
import com.cxz.headline.di.module.NewsDetailActivityModule;
import com.cxz.headline.module.news.comment.NewsCommentActivity;
import com.cxz.headline.util.SettingUtil;
import com.cxz.headline.util.ShareUtil;
import com.cxz.headline.util.imageloader.ImageLoader;
import com.cxz.headline.util.imageloader.ImageOptions;
import com.cxz.headline.util.imageloader.glide.GlideImageOptions;
import com.cxz.headline.widget.helper.StatusBarHelper;

import butterknife.BindView;

public class NewsDetailActivity extends BaseActivity<NewsDetailPresenter> implements NewsDetailContract.View {

    private static final String BEAN = "NewsMultiArticleDataBean";
    private static final String IMG = "img";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.scrollView)
    NestedScrollView mScrollView;
    @BindView(R.id.loading_progress)
    ContentLoadingProgressBar loadingProgressBar;

    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView iv_header;
    AppBarLayout appBarLayout;

    private NewsMultiArticleDataBean dataBean;
    private String imgUrl;
    private String title;
    private boolean bHasImg;
    private String shareUrl;
    private String groupId;
    private String itemId;

    public static void launch(NewsMultiArticleDataBean dataBean) {
        launch(dataBean, "");
    }

    public static void launch(NewsMultiArticleDataBean dataBean, String imgUrl) {
        App.getContext().startActivity(new Intent(App.getContext(), NewsDetailActivity.class)
                .putExtra(IMG, imgUrl)
                .putExtra(BEAN, dataBean)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public void showLoading() {
        loadingProgressBar.show();
    }

    @Override
    public void hideLoading() {
        loadingProgressBar.hide();
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (mRefreshLayout != null)
                    mRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    protected void initInject() {
        DaggerNewsDetailActivityComponent
                .builder()
                .appComponent(App.getAppComponent())
                .newsDetailActivityModule(new NewsDetailActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected int attachLayoutRes() {
        imgUrl = getIntent().getStringExtra(IMG);
        bHasImg = !TextUtils.isEmpty(imgUrl);
        return bHasImg ? R.layout.activity_news_detail_img : R.layout.activity_news_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        dataBean = intent.getParcelableExtra(BEAN);
        groupId = String.valueOf(dataBean.getGroup_id());
        itemId = String.valueOf(dataBean.getItem_id());

        mPresenter.loadDetailData(dataBean);

        title = dataBean.getTitle();
        shareUrl = dataBean.getShare_url();

        initToolBar(mToolbar, true, "");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScrollView.scrollTo(0, 0);
            }
        });

        initWebClient();

        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                hideLoading();
            }
        });

        int color = SettingUtil.getInstance().getColor();
        loadingProgressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        loadingProgressBar.show();

        mRefreshLayout.setColorSchemeColors(color);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(true);
                    }
                });
                mPresenter.loadDetailData(dataBean);
            }
        });

        if (bHasImg) {

            StatusBarHelper.immersiveStatusBar(this);
            StatusBarHelper.setHeightAndPadding(this, mToolbar);

            collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
            iv_header = findViewById(R.id.iv_header);
            appBarLayout = findViewById(R.id.app_bar_layout);

            ImageOptions options = GlideImageOptions.builder()
                    .url(imgUrl)
                    .placeholder(R.mipmap.ic_default_pic)
                    .imageView(iv_header)
                    .build();
            ImageLoader.getInstance().loadImage(this, options);

            //通过CollapsingToolbarLayout修改字体颜色
            collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
            collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色

            appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
                @Override
                public void onStateChanged(AppBarLayout appBarLayout, State state) {
                    if (state == State.EXPANDED) {
                        // 展开状态
                        collapsingToolbarLayout.setTitle("");
                        mToolbar.setBackgroundColor(Color.TRANSPARENT);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        }
                    } else if (state == State.COLLAPSED) {
                        // 折叠状态

                    } else {
                        // 中间状态
                        collapsingToolbarLayout.setTitle(title);
                        mToolbar.setBackgroundColor(SettingUtil.getInstance().getColor());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        }
                    }
                }
            });
        } else {
            mToolbar.setTitle(title);
        }

    }

    private void initWebClient() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        // 缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        settings.setBuiltInZoomControls(false);
        // 缓存
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启DOM storage API功能
        settings.setDomStorageEnabled(true);
        // 开启application Cache功能
        settings.setAppCacheEnabled(true);
        // 判断是否为无图模式
        //settings.setBlockNetworkImage(SettingUtil.getInstance().getIsNoPhotoMode());
        // 不调用第三方浏览器即可进行页面反应
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!TextUtils.isEmpty(url)) {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                hideLoading();
                super.onPageFinished(view, url);
            }
        });

        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
                return false;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 90) {
                    hideLoading();
                } else {
                    showLoading();
                }
            }
        });
    }

    @Override
    public void showWebView(String url, boolean flag) {
        if (flag) {
            mWebView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        } else {
            if (shareUrl.contains("temai.snssdk.com")) {
                mWebView.getSettings().setUserAgentString(Constant.USER_AGENT_PC);
            }
            mWebView.loadUrl(shareUrl);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_open_comment) {
            // TODO: 2018/1/7
            if (null != groupId)
                NewsCommentActivity.launch(groupId, itemId);
            return true;
        } else if (id == R.id.action_open_media_home) {
            // TODO: 2018/1/7
            return true;
        } else if (id == R.id.action_share) {
            ShareUtil.send(this, title + "\n" + shareUrl);
            return true;
        } else if (id == R.id.action_open_in_browser) {
            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(shareUrl)));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
