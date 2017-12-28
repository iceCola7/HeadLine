package com.cxz.headline.http.service;

import com.cxz.headline.bean.news.NewsMultiArticleBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chenxz on 2017/12/11.
 */

public interface NewsService {

    @GET("/api/news/feed/v62/?refer=1&count=20&iid=5034850950&device_id=6096495334&aid=13")
    Observable<NewsMultiArticleBean> getNewsArticleList(
            @Query("category") String category,
            @Query("min_behot_time") String min_behot_time);

    @GET("/api/news/feed/v62/?refer=1&count=20&iid=5034850950&device_id=6096495334&aid=13")
    Observable<NewsMultiArticleBean> loadMoreNewsArticleList(
            @Query("category") String category,
            @Query("max_behot_time") String max_behot_time);

}
