package com.cxz.headline.callbacks;

import android.support.design.widget.AppBarLayout;

/**
 * Created by chenxz on 2018/1/7.
 * 监听CollapsingToolbarLayout的展开与折叠
 * https://stackoverflow.com/questions/31682310/android-collapsingtoolbarlayout-collapse-listener
 */

public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    private State mCurrentState = State.IDLE;

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED);
            }
            mCurrentState = State.COLLAPSED;
        } else {
            if (mCurrentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE);
            }
            mCurrentState = State.IDLE;
        }
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, State state);

    protected enum State {EXPANDED, COLLAPSED, IDLE}
}
