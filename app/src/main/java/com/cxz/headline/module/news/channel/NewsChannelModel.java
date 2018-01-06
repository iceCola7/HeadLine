package com.cxz.headline.module.news.channel;

import com.cxz.headline.base.mvp.BaseModel;
import com.cxz.headline.di.scope.ActivityScope;

import javax.inject.Inject;

/**
 * Created by chenxz on 2018/1/6.
 */

@ActivityScope
public class NewsChannelModel extends BaseModel implements NewsChannelContract.Model {

    @Inject
    public NewsChannelModel() {
    }

}
