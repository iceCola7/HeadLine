package com.cxz.headline.module.video.detail;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.cxz.headline.R;
import com.cxz.headline.app.App;
import com.cxz.headline.base.BaseActivity;
import com.cxz.headline.bean.news.NewsMultiArticleDataBean;
import com.cxz.headline.di.component.DaggerVideoDetailActivityComponent;
import com.cxz.headline.di.module.VideoDetailActivityModule;
import com.cxz.headline.module.news.detail.NewsDetailActivity;
import com.cxz.headline.util.SettingUtil;
import com.cxz.headline.util.ShareUtil;
import com.cxz.headline.util.imageloader.ImageLoader;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class VideoDetailActivity extends BaseActivity<VideoDetailPresenter> implements VideoDetailContract.View, SwipeRefreshLayout.OnRefreshListener {

    private static String BEAN = "NewsMultiArticleDataBean";

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.loading_progress)
    ContentLoadingProgressBar loadingProgressBar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.video_player)
    JZVideoPlayerStandard mJZVideoPlayer;

    private String groupId;
    private String itemId;
    private String videoId;
    private String videoTitle;
    private String shareUrl;

    private NewsMultiArticleDataBean dataBean;

    public static void launch(NewsMultiArticleDataBean dataBean) {
        App.getContext().startActivity(new Intent(App.getContext(), NewsDetailActivity.class)
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
    protected int attachLayoutRes() {
        return R.layout.activity_video_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initData();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        int color = SettingUtil.getInstance().getColor();
        loadingProgressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        loadingProgressBar.show();

        mPresenter.loadVideoDetailData(videoId);

        mRefreshLayout.setColorSchemeColors(color);
        mRefreshLayout.setOnRefreshListener(this);

        fab.setBackgroundTintList(ColorStateList.valueOf(color));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtil.send(VideoDetailActivity.this, videoTitle + "\n" + shareUrl);
            }
        });

    }

    private void initData() {
        Intent intent = getIntent();
        dataBean = intent.getParcelableExtra(BEAN);
        if (null != dataBean.getVideo_detail_info()) {
            if (null != dataBean.getVideo_detail_info().getDetail_video_large_image()) {
                String imageUrl = dataBean.getVideo_detail_info().getDetail_video_large_image().getUrl();
                if (!TextUtils.isEmpty(imageUrl)) {
                    ImageLoader.getInstance().loadImage(App.getContext(), ImageLoader.getInstance().getDefaultOptions(imageUrl, mJZVideoPlayer.thumbImageView));
                }
            }
        }
        this.groupId = dataBean.getGroup_id() + "";
        this.itemId = dataBean.getItem_id() + "";
        this.videoId = dataBean.getVideo_id();
        this.videoTitle = dataBean.getTitle();
        this.shareUrl = dataBean.getDisplay_url();
    }

    @Override
    protected void initInject() {
        DaggerVideoDetailActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .videoDetailActivityModule(new VideoDetailActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void setVideoUrl(String url) {

    }
}
