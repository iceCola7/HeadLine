package com.cxz.headline.module.news.article;

import android.text.TextUtils;

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
    public void loadNewsArticleList(String category, String minBehotTime, boolean isUpdate) {
        mModel.loadNewsArticleList(category, minBehotTime, isUpdate)
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
                .subscribe(new BaseSingleObserver<List<NewsMultiArticleDataBean>>(mView) {
                    @Override
                    public void onSuccess(List<NewsMultiArticleDataBean> dataBeans) {
                        mView.updateNewsArticleList(dataBeans);
                    }
                });
    }

    @Override
    public void loadMoreNewsArticleList(String category, String maxBehotTime, boolean isUpdate) {
        mModel.loadMoreNewsArticleList(category, maxBehotTime, isUpdate)
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
                .subscribe(new BaseSingleObserver<List<NewsMultiArticleDataBean>>(mView) {
                    @Override
                    public void onSuccess(List<NewsMultiArticleDataBean> dataBeans) {
                        mView.updateMoreNewsArticleList(dataBeans);
                    }
                });
    }
}
