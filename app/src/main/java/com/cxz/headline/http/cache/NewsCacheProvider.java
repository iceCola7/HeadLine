package com.cxz.headline.http.cache;

import com.cxz.headline.bean.news.NewsMultiArticleBean;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * Created by chenxz on 2017/12/11.
 */

public interface NewsCacheProvider {

    @LifeCache(duration = 3, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<NewsMultiArticleBean>> getNewsArticleList(Observable<NewsMultiArticleBean> observable, DynamicKey dynamicKey, EvictDynamicKey evictDynamicKey);

}
