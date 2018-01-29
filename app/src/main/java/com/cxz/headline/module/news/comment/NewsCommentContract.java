package com.cxz.headline.module.news.comment;

import com.cxz.headline.base.mvp.IPresenter;
import com.cxz.headline.base.mvp.IView;
import com.cxz.headline.bean.news.NewsCommentBean;

import java.util.List;

/**
 * Created by chenxz on 2018/1/12.
 */

public interface NewsCommentContract {

    interface View extends IView{
        void updateCommentList(List<NewsCommentBean.DataBean.CommentBean> commentBeans);
    }

    interface Presenter extends IPresenter{
        void loadCommentList(String... groupId_itemId);

        void loadMoreCommentList();
    }

}
