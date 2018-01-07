package com.cxz.headline.module.video.article;

import com.cxz.headline.base.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * Created by chenxz on 2018/1/7.
 */

public class VideoArticlePresenter extends BasePresenter<VideoArticleContract.Model,VideoArticleContract.View> implements VideoArticleContract.Presenter {

    @Inject
    public VideoArticlePresenter(VideoArticleContract.Model model, VideoArticleContract.View view){
        super(model, view);
    }

}
