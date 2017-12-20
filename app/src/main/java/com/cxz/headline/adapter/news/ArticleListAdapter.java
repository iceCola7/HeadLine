package com.cxz.headline.adapter.news;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.cxz.headline.R;
import com.cxz.headline.bean.news.NewsMultiArticleDataBean;
import com.cxz.headline.util.ShareUtil;
import com.cxz.headline.util.TimeUtil;
import com.cxz.headline.util.imageloader.ImageLoader;
import com.cxz.headline.util.imageloader.ImageOptions;
import com.cxz.headline.util.imageloader.glide.GlideImageOptions;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by chenxz on 2017/12/16.
 */

public class ArticleListAdapter extends CommonAdapter<NewsMultiArticleDataBean> {

    private final Context context;
    private ImageOptions options;

    public ArticleListAdapter(Context context, int layoutId, List<NewsMultiArticleDataBean> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }

    public void appendDatas(List<NewsMultiArticleDataBean> dataBeans) {
        mDatas.addAll(dataBeans);
        notifyDataSetChanged();
    }

    public void setDatas(List<NewsMultiArticleDataBean> dataBeans) {
        mDatas.clear();
        appendDatas(dataBeans);
    }

    @Override
    protected void convert(ViewHolder holder, final NewsMultiArticleDataBean bean, int position) {
        String extra = bean.getSource() + " " + bean.getComment_count() + "评论"
                + " " + TimeUtil.getTimeStampAgo(String.valueOf(bean.getBehot_time()));
        holder.setText(R.id.tv_title, bean.getTitle())
                .setText(R.id.tv_abstract, bean.getAbstractX())
                .setText(R.id.tv_extra, extra);
        ImageView iv_avatar = holder.getView(R.id.iv_avatar);

        options = GlideImageOptions.builder()
                .url(bean.getUser_info().getAvatar_url())
                .imageView(iv_avatar)
                .build();
        ImageLoader.getInstance().loadImage(context, options);

        final ImageView iv_dots = holder.getView(R.id.iv_dots);
        iv_dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, iv_dots, Gravity.END, 0, R.style.MyPopupMenu);
                popupMenu.inflate(R.menu.menu_share);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.action_share) {
                            ShareUtil.send(context, bean.getTitle() + "\n" + bean.getShare_url());
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
}
