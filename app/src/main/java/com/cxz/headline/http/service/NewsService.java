package com.cxz.headline.http.service;

import com.cxz.headline.bean.news.NewsContentBean;
import com.cxz.headline.bean.news.NewsMultiArticleBean;
import com.cxz.headline.bean.video.VideoDetailBean;
import com.cxz.headline.common.Constant;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;

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

    /**
     * 获取新闻内容的API
     */
    @GET
    @Headers("User-Agent:" + Constant.USER_AGENT_MOBILE)
    Call<ResponseBody> getNewsContentRedirectUrl(@Url String url);

    /**
     * 获取新闻HTML内容
     * http://m.toutiao.com/i6364969235889783298/info/
     */
    @GET
    Observable<NewsContentBean> getNewsContent(@Url String url);

    /**
     * 获取视频信息
     * Api 生成较复杂 详情查看
     * http://ib.365yg.com/video/urls/v/1/toutiao/mp4/视频ID?r=17位随机数&s=加密结果
     */
    @GET
    Observable<VideoDetailBean> getVideoDetail(@Url String url);
}
