package com.cxz.headline.module.news.channel;

import android.os.Bundle;
import android.view.View;

import com.cxz.headline.R;
import com.cxz.headline.app.App;
import com.cxz.headline.base.BaseFragment;
import com.cxz.headline.di.component.DaggerChannelFragmentComponent;
import com.cxz.headline.di.module.ChannelFragmentModule;

/**
 * Created by chenxz on 2017/12/10.
 */

public class ChannelFragment extends BaseFragment<ChannelPresenter> implements ChannelContract.View {

    private static String CHANNEL_ID = "channelId";
    private String channelId;

    public static ChannelFragment newInstance(String channelId) {
        ChannelFragment fragment = new ChannelFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CHANNEL_ID, channelId);
        fragment.setArguments(bundle);
        return fragment;
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
        return R.layout.fragment_channel;
    }

    @Override
    protected void initInject() {
        DaggerChannelFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .channelFragmentModule(new ChannelFragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        channelId = getArguments().getString(CHANNEL_ID, "");
    }
}
