package com.cxz.headline.module.news.channel;

import com.cxz.headline.base.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * Created by chenxz on 2017/12/10.
 */

public class ChannelPresenter extends BasePresenter<ChannelContract.Model,ChannelContract.View> implements ChannelContract.Presenter {

    @Inject
    public ChannelPresenter(ChannelContract.Model model,ChannelContract.View view){
        super(model, view);

    }

}
