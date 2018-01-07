package com.cxz.headline.module.video.article;

import com.cxz.headline.base.mvp.BaseModel;
import com.cxz.headline.di.scope.FragmentScope;

import javax.inject.Inject;

/**
 * Created by chenxz on 2018/1/7.
 */

@FragmentScope
public class VideoArticleModel extends BaseModel implements VideoArticleContract.Model {

    @Inject
    public VideoArticleModel() {
    }

}
