package com.cxz.headline.module.news.main;

import com.cxz.headline.base.mvp.IPresenter;
import com.cxz.headline.base.mvp.IView;
import com.cxz.headline.bean.news.NewsChannelBean;

import java.util.List;

/**
 * Created by chenxz on 2017/12/10.
 */

public interface NewsMainContract {

    interface View extends IView {
        void updateChannel();
    }

    interface Presenter extends IPresenter {
        List<NewsChannelBean> getChannelBeans(boolean isEnable);

        void initChannelData();
    }

}
