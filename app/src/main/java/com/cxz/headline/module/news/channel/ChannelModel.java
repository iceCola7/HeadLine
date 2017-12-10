package com.cxz.headline.module.news.channel;

import com.cxz.headline.base.mvp.BaseModel;
import com.cxz.headline.di.scope.FragmentScope;

import javax.inject.Inject;

/**
 * Created by chenxz on 2017/12/10.
 */
@FragmentScope
public class ChannelModel extends BaseModel implements ChannelContract.Model {

    @Inject
    public ChannelModel() {
    }

}
