package com.cxz.headline.module.news.article;

import com.cxz.headline.base.mvp.BasePresenter;
import com.cxz.headline.bean.news.NewsMultiArticleBean;
import com.cxz.headline.bean.news.NewsMultiArticleDataBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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

        mModel.loadNewsArticleList(category, minBehotTime)
                .subscribeOn(Schedulers.io())
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<NewsMultiArticleDataBean>>() {
                    @Override
                    public void accept(List<NewsMultiArticleDataBean> newsMultiArticleDataBeans) throws Exception {
                        // TODO: 2017/12/13
                    }
                });
    }
}
