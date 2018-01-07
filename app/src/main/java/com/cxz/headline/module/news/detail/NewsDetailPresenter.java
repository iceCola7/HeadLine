package com.cxz.headline.module.news.detail;

import android.text.TextUtils;

import com.cxz.headline.base.mvp.BasePresenter;
import com.cxz.headline.base.mvp.IModel;
import com.cxz.headline.bean.news.NewsContentBean;
import com.cxz.headline.bean.news.NewsMultiArticleDataBean;
import com.cxz.headline.http.RetrofitHelper;
import com.cxz.headline.http.service.NewsService;
import com.cxz.headline.util.SettingUtil;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by chenxz on 2018/1/6.
 */

public class NewsDetailPresenter extends BasePresenter<IModel, NewsDetailContract.View> implements NewsDetailContract.Presenter {

    @Inject
    public NewsDetailPresenter(NewsDetailContract.View view) {
        super(view);
    }

    @Override
    public void loadDetailData(final NewsMultiArticleDataBean bean) {
        String groupId = String.valueOf(bean.getGroup_id());
        String itemId = String.valueOf(bean.getItem_id());
        final String url = bean.getDisplay_url();

        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        try {
                            Response<ResponseBody> response = RetrofitHelper.getInstance().obtainRetrofitService(NewsService.class)
                                    .getNewsContentRedirectUrl(url).execute();
                            // 获取重定向后的 URL 用于拼凑API
                            if (response.isSuccessful()) {
                                String httpUrl = response.raw().request().url().toString();
                                if (!TextUtils.isEmpty(httpUrl) && httpUrl.contains("toutiao")) {
                                    String api = httpUrl + "info/";
                                    e.onNext(api);
                                } else {
                                    e.onError(new Throwable());
                                }
                            } else {
                                e.onError(new Throwable());
                            }
                        } catch (Exception e1) {
                            e.onComplete();
                            e1.printStackTrace();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .switchMap(new Function<String, ObservableSource<NewsContentBean>>() {
                    @Override
                    public ObservableSource<NewsContentBean> apply(String s) throws Exception {
                        return RetrofitHelper.getInstance().obtainRetrofitService(NewsService.class).getNewsContent(s);
                    }
                })
                .map(new Function<NewsContentBean, String>() {
                    @Override
                    public String apply(NewsContentBean newsContentBean) throws Exception {
                        return getHTML(newsContentBean);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<String>bindToLife())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        mView.showWebView(s, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showWebView(null, false);
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });

    }

    private String getHTML(NewsContentBean bean) {
        String title = bean.getData().getTitle();
        String content = bean.getData().getContent();
        if (content != null) {

            String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/toutiao_light.css\" type=\"text/css\">";
            if (SettingUtil.getInstance().getIsNightMode()) {
                css = css.replace("toutiao_light", "toutiao_dark");
            }

            String html = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">" +
                    css +
                    "<body>\n" +
                    "<article class=\"article-container\">\n" +
                    "    <div class=\"article__content article-content\">" +
                    "<h1 class=\"article-title\">" +
                    title +
                    "</h1>" +
                    content +
                    "    </div>\n" +
                    "</article>\n" +
                    "</body>\n" +
                    "</html>";

            return html;
        } else {
            return null;
        }
    }
}
