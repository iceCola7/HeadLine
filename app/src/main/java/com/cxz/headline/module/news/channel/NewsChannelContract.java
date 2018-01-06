package com.cxz.headline.module.news.channel;

import com.cxz.headline.base.mvp.IModel;
import com.cxz.headline.base.mvp.IPresenter;
import com.cxz.headline.base.mvp.IView;
import com.cxz.headline.bean.news.NewsChannelBean;

import java.util.List;

/**
 * Created by chenxz on 2018/1/6.
 */

public interface NewsChannelContract {

    interface View extends IView {

    }

    interface Presenter extends IPresenter {
        List<NewsChannelBean> getChannelBeans(boolean isEnable);
        void saveChannelData(List<NewsChannelBean> myBeans,List<NewsChannelBean> otherBeans);
    }

    interface Model extends IModel{

    }

}
