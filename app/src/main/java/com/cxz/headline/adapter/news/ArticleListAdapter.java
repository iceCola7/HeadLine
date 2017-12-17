package com.cxz.headline.adapter.news;

import android.content.Context;

import com.cxz.headline.R;
import com.cxz.headline.bean.news.NewsMultiArticleDataBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by chenxz on 2017/12/16.
 */

public class ArticleListAdapter extends CommonAdapter<NewsMultiArticleDataBean> {

    public ArticleListAdapter(Context context, int layoutId, List<NewsMultiArticleDataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, NewsMultiArticleDataBean newsMultiArticleDataBean, int position) {
        holder.setText(R.id.textView, newsMultiArticleDataBean.getTitle());
    }
}
