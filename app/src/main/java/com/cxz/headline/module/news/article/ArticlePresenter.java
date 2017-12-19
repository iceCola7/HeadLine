package com.cxz.headline.module.news.article;

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
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by chenxz on 2017/12/10.
 */

public class ArticlePresenter extends BasePresenter<ArticleContract.Model, ArticleContract.View> implements ArticleContract.Presenter {

    private Gson mGson;

    @Inject
    public ArticlePresenter(ArticleContract.Model model, ArticleContract.View view) {
        super(model, view);
        mGson = new Gson();
    }

    @Override
    public void loadNewsArticleList(String category, String minBehotTime) {
        mModel.loadNewsArticleList(category, minBehotTime, true)
                .compose(RxUtil.<NewsMultiArticleBean>rxSchedulerTransformer())
                .switchMap(new Function<NewsMultiArticleBean, ObservableSource<NewsMultiArticleDataBean>>() {
                    @Override
                    public ObservableSource<NewsMultiArticleDataBean> apply(NewsMultiArticleBean newsMultiArticleBean) throws Exception {
                        List<NewsMultiArticleDataBean> dataBeans = new ArrayList<>();
                        for (NewsMultiArticleBean.DataBean dataBean : newsMultiArticleBean.getData()) {
                            dataBeans.add(mGson.fromJson(dataBean.getContent(), NewsMultiArticleDataBean.class));
                        }
                        return Observable.fromIterable(dataBeans);
                    }
                })
                .toList()
                .compose(mView.<List<NewsMultiArticleDataBean>>bindToLife())
                .subscribe(new Consumer<List<NewsMultiArticleDataBean>>() {
                    @Override
                    public void accept(List<NewsMultiArticleDataBean> newsMultiArticleDataBeans) throws Exception {
                        mView.updateNewsArticleList(newsMultiArticleDataBeans);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    public void loadMoreNewsArticleList(String category, String maxBehotTime) {
        mModel.loadMoreNewsArticleList(category, maxBehotTime, false)
                .compose(RxUtil.<NewsMultiArticleBean>rxSchedulerTransformer())
                .switchMap(new Function<NewsMultiArticleBean, ObservableSource<NewsMultiArticleDataBean>>() {
                    @Override
                    public ObservableSource<NewsMultiArticleDataBean> apply(NewsMultiArticleBean newsMultiArticleBean) throws Exception {
                        List<NewsMultiArticleDataBean> dataBeans = new ArrayList<>();
                        for (NewsMultiArticleBean.DataBean dataBean : newsMultiArticleBean.getData()) {
                            dataBeans.add(mGson.fromJson(dataBean.getContent(), NewsMultiArticleDataBean.class));
                        }
                        return Observable.fromIterable(dataBeans);
                    }
                })
                .toList()
                .compose(mView.<List<NewsMultiArticleDataBean>>bindToLife())
                .subscribe(new Consumer<List<NewsMultiArticleDataBean>>() {
                    @Override
                    public void accept(List<NewsMultiArticleDataBean> newsMultiArticleDataBeans) throws Exception {
                        mView.updateMoreNewsArticleList(newsMultiArticleDataBeans);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
