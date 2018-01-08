package com.cxz.headline.module.video.article;

import com.cxz.headline.base.mvp.BaseModel;
import com.cxz.headline.bean.news.NewsMultiArticleBean;
import com.cxz.headline.di.scope.FragmentScope;
import com.cxz.headline.http.cache.NewsCacheProvider;
import com.cxz.headline.http.service.NewsService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;

/**
 * Created by chenxz on 2018/1/7.
 */

@FragmentScope
public class VideoArticleModel extends BaseModel implements VideoArticleContract.Model {

    @Inject
    public VideoArticleModel() {
    }

    @Override
    public Observable<NewsMultiArticleBean> loadVideoArticleList(final String category, String minBehotTime, final boolean isUpdate) {
        return Observable.just(mRetrofitHelper.obtainRetrofitService(NewsService.class).getNewsArticleList(category, minBehotTime))
                .flatMap(new Function<Observable<NewsMultiArticleBean>, ObservableSource<NewsMultiArticleBean>>() {
                    @Override
                    public ObservableSource<NewsMultiArticleBean> apply(Observable<NewsMultiArticleBean> observable) throws Exception {
                        return mRetrofitHelper.obtainCacheService(NewsCacheProvider.class)
                                .getNewsArticleList(observable, new DynamicKey(category), new EvictDynamicKey(isUpdate))
                                .map(new Function<Reply<NewsMultiArticleBean>, NewsMultiArticleBean>() {
                                    @Override
                                    public NewsMultiArticleBean apply(Reply<NewsMultiArticleBean> newsMultiArticleBeanReply) throws Exception {
                                        return newsMultiArticleBeanReply.getData();
                                    }
                                });
                    }
                });
    }

    @Override
    public Observable<NewsMultiArticleBean> loadMoreVideoArticleList(final String category, String maxBehotTime, final boolean isUpdate) {
        return Observable.just(mRetrofitHelper.obtainRetrofitService(NewsService.class).getNewsArticleList(category, maxBehotTime))
                .flatMap(new Function<Observable<NewsMultiArticleBean>, ObservableSource<NewsMultiArticleBean>>() {
                    @Override
                    public ObservableSource<NewsMultiArticleBean> apply(Observable<NewsMultiArticleBean> observable) throws Exception {
                        return mRetrofitHelper.obtainCacheService(NewsCacheProvider.class)
                                .getMoreNewsArticleList(observable, new DynamicKey(category), new EvictDynamicKey(isUpdate))
                                .map(new Function<Reply<NewsMultiArticleBean>, NewsMultiArticleBean>() {
                                    @Override
                                    public NewsMultiArticleBean apply(Reply<NewsMultiArticleBean> newsMultiArticleBeanReply) throws Exception {
                                        return newsMultiArticleBeanReply.getData();
                                    }
                                });
                    }
                });
    }
}
