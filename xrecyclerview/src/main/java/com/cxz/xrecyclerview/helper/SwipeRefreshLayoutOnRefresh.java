package com.cxz.xrecyclerview.helper;

import android.support.v4.widget.SwipeRefreshLayout;

import com.cxz.xrecyclerview.XRecyclerView;

public class SwipeRefreshLayoutOnRefresh implements SwipeRefreshLayout.OnRefreshListener {
    XRecyclerView mPullLoadMoreRecyclerView;

    public SwipeRefreshLayoutOnRefresh(XRecyclerView pullLoadMoreRecyclerView) {
        this.mPullLoadMoreRecyclerView = pullLoadMoreRecyclerView;
    }

    @Override
    public void onRefresh() {
        if (!mPullLoadMoreRecyclerView.isRefresh()) {
            mPullLoadMoreRecyclerView.setIsRefresh(true);
            mPullLoadMoreRecyclerView.refresh();
        }
    }
}
