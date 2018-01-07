package com.cxz.headline.widget.helper;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by chenxz on 2017/12/16.
 */

public class SwipeRefreshHelper {

    private SwipeRefreshHelper() {
    }

    /**
     * 初始化
     *
     * @param swipeRefreshLayout
     * @param listener
     */
    public static void init(SwipeRefreshLayout swipeRefreshLayout, SwipeRefreshLayout.OnRefreshListener listener) {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(listener);
    }

    /**
     * 初始化，关联AppBarLayout，处理滑动冲突
     *
     * @param swipeRefreshLayout
     * @param appBarLayout
     * @param listener
     */
    public static void init(final SwipeRefreshLayout swipeRefreshLayout, AppBarLayout appBarLayout, SwipeRefreshLayout.OnRefreshListener listener) {
        init(swipeRefreshLayout, listener);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });
    }

    /**
     * 是否能刷新
     *
     * @param swipeRefreshLayout
     * @param isRefresh
     */
    public static void canRefresh(SwipeRefreshLayout swipeRefreshLayout, boolean isRefresh) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(isRefresh);
        }
    }

    /**
     * 控制刷新
     *
     * @param swipeRefreshLayout
     * @param isRefresh
     */
    public static void controlRefresh(SwipeRefreshLayout swipeRefreshLayout, boolean isRefresh) {
        if (swipeRefreshLayout != null) {
            if (isRefresh != swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(isRefresh);
            }
        }
    }

}
