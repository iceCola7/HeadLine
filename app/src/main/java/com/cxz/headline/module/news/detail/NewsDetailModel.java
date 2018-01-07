package com.cxz.headline.module.news.detail;

import com.cxz.headline.base.mvp.BaseModel;
import com.cxz.headline.di.scope.ActivityScope;

import javax.inject.Inject;

/**
 * Created by chenxz on 2018/1/6.
 */

@ActivityScope
public class NewsDetailModel extends BaseModel implements NewsDetailContract.Model {

    @Inject
    public NewsDetailModel() {
    }

}
