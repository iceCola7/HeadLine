package com.cxz.headline.module.news.comment;

import com.cxz.headline.base.BaseObserver;
import com.cxz.headline.base.mvp.BasePresenter;
import com.cxz.headline.base.mvp.IModel;
import com.cxz.headline.bean.news.NewsCommentBean;
import com.cxz.headline.http.RetrofitHelper;
import com.cxz.headline.http.service.NewsService;
import com.cxz.headline.util.RxUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

/**
 * Created by chenxz on 2018/1/12.
 */

public class NewsCommentPresenter extends BasePresenter<IModel, NewsCommentContract.View> implements NewsCommentContract.Presenter {

    private String groupId;
    private String itemId;
    private int offset = 0;

    @Inject
    public NewsCommentPresenter(NewsCommentContract.View view) {
        super(view);
    }

    @Override
    public void loadCommentList(String... groupId_itemId) {
        try {
            if (null == this.groupId) {
                this.groupId = groupId_itemId[0];
            }
            if (null == this.itemId) {
                this.itemId = groupId_itemId[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        RetrofitHelper.getInstance().obtainRetrofitService(NewsService.class)
                .getNewsComment(groupId, offset)
                .compose(RxUtil.<NewsCommentBean>rxSchedulerTransformer())
                .map(new Function<NewsCommentBean, List<NewsCommentBean.DataBean.CommentBean>>() {
                    @Override
                    public List<NewsCommentBean.DataBean.CommentBean> apply(NewsCommentBean newsCommentBean) throws Exception {
                        List<NewsCommentBean.DataBean.CommentBean> data = new ArrayList<>();
                        for (NewsCommentBean.DataBean bean : newsCommentBean.getData()) {
                            data.add(bean.getComment());
                        }
                        return data;
                    }
                })
                .compose(mView.<List<NewsCommentBean.DataBean.CommentBean>>bindToLife())
                .subscribe(new BaseObserver<List<NewsCommentBean.DataBean.CommentBean>>(mView) {
                    @Override
                    public void onNext(List<NewsCommentBean.DataBean.CommentBean> commentBeans) {
                        mView.updateCommentList(commentBeans);
                    }
                });
    }

    @Override
    public void loadMoreCommentList() {
        offset += 20;
        loadCommentList();
    }
}
