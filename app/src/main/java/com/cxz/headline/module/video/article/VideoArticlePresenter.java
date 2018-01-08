package com.cxz.headline.module.video.article;

import com.cxz.headline.base.BaseSingleObserver;
import com.cxz.headline.base.mvp.BasePresenter;
import com.cxz.headline.bean.news.NewsMultiArticleBean;
import com.cxz.headline.bean.news.NewsMultiArticleDataBean;
import com.cxz.headline.util.RxUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by chenxz on 2018/1/7.
 */

public class VideoArticlePresenter extends BasePresenter<VideoArticleContract.Model, VideoArticleContract.View> implements VideoArticleContract.Presenter {

    private Gson mGson;

    @Inject
    public VideoArticlePresenter(VideoArticleContract.Model model, VideoArticleContract.View view) {
        super(model, view);
        mGson = new Gson();
    }

    @Override
    public void loadVideoArticleList(String category, String minBehotTime, boolean isUpdate) {
        mModel.loadVideoArticleList(category, minBehotTime, isUpdate)
                .compose(RxUtil.<NewsMultiArticleBean>rxSchedulerTransformer())
                .switchMap(new Function<NewsMultiArticleBean, ObservableSource<NewsMultiArticleDataBean>>() {
                    @Override
                    public ObservableSource<NewsMultiArticleDataBean> apply(NewsMultiArticleBean newsMultiArticleBean) throws Exception {
                        List<NewsMultiArticleDataBean> dataBeans = new ArrayList<>();
                        NewsMultiArticleDataBean articleDataBean;
                        for (NewsMultiArticleBean.DataBean dataBean : newsMultiArticleBean.getData()) {
                            articleDataBean = mGson.fromJson(dataBean.getContent(), NewsMultiArticleDataBean.class);
                            if (articleDataBean.getSource() != null)
                                dataBeans.add(articleDataBean);
                        }
                        return Observable.fromIterable(dataBeans);
                    }
                })
                .toList()
                .compose(mView.<List<NewsMultiArticleDataBean>>bindToLife())
                .subscribe(new BaseSingleObserver<List<NewsMultiArticleDataBean>>(mView) {
                    @Override
                    public void onSuccess(List<NewsMultiArticleDataBean> dataBeans) {
                        mView.updateVideoArticleList(dataBeans);
                    }
                });
    }

    @Override
    public void loadMoreVideoArticleList(String category, String maxBehotTime, boolean isUpdate) {
        mModel.loadMoreVideoArticleList(category, maxBehotTime, isUpdate)
                .compose(RxUtil.<NewsMultiArticleBean>rxSchedulerTransformer())
                .switchMap(new Function<NewsMultiArticleBean, ObservableSource<NewsMultiArticleDataBean>>() {
                    @Override
                    public ObservableSource<NewsMultiArticleDataBean> apply(NewsMultiArticleBean newsMultiArticleBean) throws Exception {
                        List<NewsMultiArticleDataBean> dataBeans = new ArrayList<>();
                        NewsMultiArticleDataBean articleDataBean;
                        for (NewsMultiArticleBean.DataBean dataBean : newsMultiArticleBean.getData()) {
                            articleDataBean = mGson.fromJson(dataBean.getContent(), NewsMultiArticleDataBean.class);
                            if (articleDataBean.getSource() != null)
                                dataBeans.add(articleDataBean);
                        }
                        return Observable.fromIterable(dataBeans);
                    }
                })
                .toList()
                .compose(mView.<List<NewsMultiArticleDataBean>>bindToLife())
                .subscribe(new BaseSingleObserver<List<NewsMultiArticleDataBean>>(mView) {
                    @Override
                    public void onSuccess(List<NewsMultiArticleDataBean> dataBeans) {
                        mView.updateMoreVideoArticleList(dataBeans);
                    }
                });
    }
}
