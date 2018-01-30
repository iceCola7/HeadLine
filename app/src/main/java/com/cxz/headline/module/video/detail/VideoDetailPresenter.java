package com.cxz.headline.module.video.detail;

import android.util.Base64;
import android.util.Log;

import com.cxz.headline.base.BaseObserver;
import com.cxz.headline.base.mvp.BasePresenter;
import com.cxz.headline.base.mvp.IModel;
import com.cxz.headline.bean.news.NewsCommentBean;
import com.cxz.headline.bean.video.VideoDetailBean;
import com.cxz.headline.http.RetrofitHelper;
import com.cxz.headline.http.service.NewsService;
import com.cxz.headline.util.RxUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.CRC32;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chenxz on 2018/1/11.
 */

public class VideoDetailPresenter extends BasePresenter<IModel, VideoDetailContract.View> implements VideoDetailContract.Presenter {

    private String groupId;
    private String itemId;
    private int offset = 0;

    @Inject
    public VideoDetailPresenter(VideoDetailContract.View view) {
        super(view);
    }

    private static String getVideoDetailUrl(String videoId) {
        String VIDEO_HOST = "http://ib.365yg.com";
        String VIDEO_URL = "/video/urls/v/1/toutiao/mp4/%s?r=%s";
        String r = getRandom();
        String s = String.format(VIDEO_URL, videoId, r);
        // 将/video/urls/v/1/toutiao/mp4/{videoid}?r={Math.random()} 进行crc32加密
        CRC32 crc32 = new CRC32();
        crc32.update(s.getBytes());
        String crcString = crc32.getValue() + "";
        String url = VIDEO_HOST + s + "&s=" + crcString;
        return url;
    }

    private static String getRandom() {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

    @Override
    public void loadVideoDetailData(String videoId) {
        String url = getVideoDetailUrl(videoId);
        RetrofitHelper.getInstance().obtainRetrofitService(NewsService.class)
                .getVideoDetail(url)
                .subscribeOn(Schedulers.io())
                .map(new Function<VideoDetailBean, String>() {
                    @Override
                    public String apply(VideoDetailBean videoDetailBean) throws Exception {
                        VideoDetailBean.DataBean.VideoListBean videoList = videoDetailBean.getData().getVideo_list();
                        if (videoList.getVideo_3() != null) {
                            String base64 = videoList.getVideo_3().getMain_url();
                            String url = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                            Log.d(TAG, "getVideoUrls: " + url);
                            return url;
                        }

                        if (videoList.getVideo_2() != null) {
                            String base64 = videoList.getVideo_2().getMain_url();
                            String url = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                            Log.d(TAG, "getVideoUrls: " + url);
                            return url;
                        }

                        if (videoList.getVideo_1() != null) {
                            String base64 = videoList.getVideo_1().getMain_url();
                            String url = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                            Log.d(TAG, "getVideoUrls: " + url);
                            return url;
                        }
                        return null;
                    }
                })
                .compose(mView.<String>bindToLife())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(mView) {
                    @Override
                    public void onNext(String s) {
                        mView.setVideoUrl(s);
                    }
                });
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
