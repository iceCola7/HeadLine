package com.cxz.headline.adapter.news;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cxz.headline.R;
import com.cxz.headline.bean.news.NewsMultiArticleDataBean;
import com.cxz.headline.util.TimeUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by chenxz on 2017/12/16.
 */

public class ArticleListAdapter extends CommonAdapter<NewsMultiArticleDataBean> {

    private Context context;

    public ArticleListAdapter(Context context, int layoutId, List<NewsMultiArticleDataBean> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder holder, NewsMultiArticleDataBean bean, int position) {
        String extra = bean.getSource() + " - " + bean.getComment_count() + "评论"
                + " - " + TimeUtil.getTimeStampAgo(String.valueOf(bean.getPublish_time()));
        holder.setText(R.id.tv_title, bean.getTitle())
                .setText(R.id.tv_abstract, bean.getAbstractX())
                .setText(R.id.tv_extra, extra);
        Glide.with(context).load(bean.getUser_info().getAvatar_url()).into((ImageView) holder.getView(R.id.iv_avatar));
    }
}
