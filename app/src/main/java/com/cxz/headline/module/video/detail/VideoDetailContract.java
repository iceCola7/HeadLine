package com.cxz.headline.module.video.detail;

import com.cxz.headline.base.mvp.IPresenter;
import com.cxz.headline.base.mvp.IView;
import com.cxz.headline.bean.video.VideoDetailBean;

import java.util.List;

/**
 * Created by chenxz on 2018/1/11.
 */

public interface VideoDetailContract {

    interface View extends IView {
        void setVideoUrl(String url);
    }

    interface Presenter extends IPresenter {
        void loadVideoDetailData(String videoId);
    }

}
