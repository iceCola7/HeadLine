package com.cxz.headline.adapter.news;

import android.content.Context;
import android.widget.ImageView;

import com.cxz.headline.R;
import com.cxz.headline.bean.news.NewsMultiArticleDataBean;
import com.cxz.headline.util.TimeUtil;
import com.cxz.headline.util.imageloader.ImageLoader;
import com.cxz.headline.util.imageloader.glide.GlideImageOptions;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by chenxz on 2017/12/16.
 */

public class ArticleListAdapter extends CommonAdapter<NewsMultiArticleDataBean> {

    public void appendDatas(List<NewsMultiArticleDataBean> dataBeans) {
        mDatas.addAll(dataBeans);
        notifyDataSetChanged();
    }

    public void setDatas(List<NewsMultiArticleDataBean> dataBeans) {
        mDatas.clear();
        appendDatas(dataBeans);
    }

    public ArticleListAdapter(Context context, int layoutId, List<NewsMultiArticleDataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, NewsMultiArticleDataBean bean, int position) {
        String extra = bean.getSource() + " - " + bean.getComment_count() + "评论"
                + " - " + TimeUtil.getTimeStampAgo(String.valueOf(bean.getPublish_time()));
        holder.setText(R.id.tv_title, bean.getTitle())
                .setText(R.id.tv_abstract, bean.getAbstractX())
                .setText(R.id.tv_extra, extra);
        ImageView imageView = holder.getView(R.id.iv_avatar);

        // Glide.with(mContext).load(bean.getUser_info().getAvatar_url()).into(imageView);

        GlideImageOptions options = GlideImageOptions.builder()
                .url(bean.getUser_info().getAvatar_url())
                .imageView(imageView)
                .build();
        ImageLoader.getInstance().loadImage(mContext, options);
    }
}
