package com.cxz.xrecyclerview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxz.xrecyclerview.helper.RecyclerViewOnScroll;
import com.cxz.xrecyclerview.helper.SwipeRefreshLayoutOnRefresh;

public class XRecyclerView extends LinearLayout {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PullLoadMoreListener mPullLoadMoreListener;
    private boolean hasMore = true;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;
    private LinearLayout mFooterView;
    private Context mContext;
    private TextView loadMoreText;

    public XRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public XRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pull_loadmore_layout, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_blue_dark, android.R.color.holo_orange_dark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayoutOnRefresh(this));

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setVerticalScrollBarEnabled(true);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
        mRecyclerView.addOnScrollListener(new RecyclerViewOnScroll(this));
        //Solve IndexOutOfBoundsException exception
        mRecyclerView.setOnTouchListener(
                new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return isRefresh || isLoadMore;
                    }
                }
        );

        mFooterView = (LinearLayout) view.findViewById(R.id.footer_layout);
        loadMoreText = (TextView) view.findViewById(R.id.load_more_tv);
        mFooterView.setVisibility(View.GONE);
        this.addView(view);

    }

    /**
     * LinearLayoutManager
     */
    public void setLinearLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * GridLayoutManager
     */
    public void setGridLayout(int spanCount) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, spanCount);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }

    /**
     * StaggeredGridLayoutManager
     */
    public void setStaggeredGridLayout(int spanCount) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount, LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }


    public void setPullRefreshEnable(boolean enable) {
        mSwipeRefreshLayout.setEnabled(enable);
    }

    public boolean getPullRefreshEnable() {
        return mSwipeRefreshLayout.isEnabled();
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mRecyclerView.getLayoutManager();
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public void loadMore() {
        if (mPullLoadMoreListener != null && hasMore) {
            mFooterView.animate()
                    .translationY(0)
                    .setDuration(300)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            mFooterView.setVisibility(View.VISIBLE);
                        }
                    })
                    .start();
            invalidate();
            mPullLoadMoreListener.onLoadMore();
        }
    }

    public void setPullLoadMoreCompleted() {
        isRefresh = false;
        setRefreshing(isRefresh);

        isLoadMore = false;
        mFooterView.animate()
                .translationY(mFooterView.getHeight())
                .setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    public void setRefreshing(final boolean isRefreshing) {
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(isRefreshing);
            }
        });
    }

    public void setOnPullLoadMoreListener(PullLoadMoreListener listener) {
        mPullLoadMoreListener = listener;
    }

    public void refresh() {
        mRecyclerView.setVisibility(View.VISIBLE);
        if (mPullLoadMoreListener != null) {
            mPullLoadMoreListener.onRefresh();
        }
    }

    public void scrollToTop() {
        mRecyclerView.scrollToPosition(0);
    }

    public void setFooterViewText(CharSequence text) {
        loadMoreText.setText(text);
    }

    public void setFooterViewText(int resId) {
        loadMoreText.setText(resId);
    }

    public void setFooterViewTextColor(int color) {
        loadMoreText.setTextColor(ContextCompat.getColor(mContext, color));
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            mRecyclerView.setAdapter(adapter);
        }
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setIsLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setIsRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public interface PullLoadMoreListener {
        public void onRefresh();

        public void onLoadMore();
    }
}
