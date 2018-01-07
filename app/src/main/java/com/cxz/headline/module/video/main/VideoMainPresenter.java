package com.cxz.headline.module.video.main;

import com.cxz.headline.base.mvp.BasePresenter;
import com.cxz.headline.base.mvp.IModel;

import javax.inject.Inject;

/**
 * Created by chenxz on 2018/1/7.
 */

public class VideoMainPresenter extends BasePresenter<IModel, VideoMainContract.View> implements VideoMainContract.Presenter {

    @Inject
    public VideoMainPresenter(VideoMainContract.View view) {
        super(view);
    }

}
