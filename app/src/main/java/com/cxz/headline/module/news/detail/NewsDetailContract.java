package com.cxz.headline.module.news.detail;

import com.cxz.headline.base.mvp.IModel;
import com.cxz.headline.base.mvp.IPresenter;
import com.cxz.headline.base.mvp.IView;
import com.cxz.headline.bean.news.NewsMultiArticleDataBean;

/**
 * Created by chenxz on 2018/1/6.
 */

public interface NewsDetailContract {


    interface View extends IView {
        void showWebView(String url, boolean flag);
    }

    interface Presenter extends IPresenter {
        void loadDetailData(NewsMultiArticleDataBean bean);
    }

    interface Model extends IModel {

    }

}
