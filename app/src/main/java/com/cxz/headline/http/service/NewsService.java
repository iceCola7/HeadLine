package com.cxz.headline.http.service;

import com.cxz.headline.bean.news.NewsMultiArticleBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chenxz on 2017/12/11.
 */

public interface NewsService {

    @GET("/api/news/feed/v66/?refer=1&count=20")
    Observable<NewsMultiArticleBean> getNewsArticleList(
            @Query("category") String category,
            @Query("min_behot_time") String max_behot_time);

}
