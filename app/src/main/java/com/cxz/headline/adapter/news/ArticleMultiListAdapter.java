package com.cxz.headline.adapter.news;

import android.content.Context;

import com.cxz.headline.bean.news.NewsMultiArticleDataBean;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by chenxz on 2017/12/23.
 */

public class ArticleMultiListAdapter extends MultiItemTypeAdapter<NewsMultiArticleDataBean> {

    public ArticleMultiListAdapter(Context context, List<NewsMultiArticleDataBean> datas) {
        super(context, datas);

        addItemViewDelegate(new ArticleTextDelegate(context));
        addItemViewDelegate(new ArticleImagesDelagate(context));
        addItemViewDelegate(new ArticleVidelDelagate(context));
    }

    public void appendDatas(List<NewsMultiArticleDataBean> dataBeans) {
        mDatas.addAll(dataBeans);
        notifyDataSetChanged();
    }

    public void setDatas(List<NewsMultiArticleDataBean> dataBeans) {
        mDatas.clear();
        appendDatas(dataBeans);
    }

}
