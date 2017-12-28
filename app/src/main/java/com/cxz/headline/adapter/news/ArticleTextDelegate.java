package com.cxz.headline.adapter.news;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxz.headline.R;
import com.cxz.headline.bean.news.NewsMultiArticleDataBean;
import com.cxz.headline.util.ShareUtil;
import com.cxz.headline.util.TimeUtil;
import com.cxz.headline.util.imageloader.ImageLoader;
import com.cxz.headline.util.imageloader.ImageOptions;
import com.cxz.headline.util.imageloader.glide.GlideImageOptions;
import com.cxz.xrecyclerview.adapter.base.BaseItemDelegate;
import com.cxz.xrecyclerview.adapter.base.BaseViewHolder;

/**
 * Created by chenxz on 2017/12/23.
 */

public class ArticleTextDelegate implements BaseItemDelegate<NewsMultiArticleDataBean> {

    private ImageOptions options;
    private Context mContext;

    public ArticleTextDelegate(Context context) {
        this.mContext = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_article_list_text;
    }

    @Override
    public boolean isForViewType(NewsMultiArticleDataBean item, int position) {
        return !item.isHas_image() && !item.isHas_video();
    }

    @Override
    public void convert(BaseViewHolder holder, final NewsMultiArticleDataBean bean, int position) {
        final String title = bean.getTitle();
        String abstractInfo = bean.getAbstractX();
        String extra = bean.getSource() + " " + bean.getComment_count() + "评论"
                + " " + TimeUtil.getTimeStampAgo(String.valueOf(bean.getBehot_time()));
        holder.setText(R.id.tv_title, title)
                .setText(R.id.tv_extra, extra);
        TextView tv_abstract = holder.getView(R.id.tv_abstract);
        tv_abstract.setVisibility(View.GONE);
        if (title != null && !title.equals(abstractInfo) && !TextUtils.isEmpty(abstractInfo)) {
            tv_abstract.setVisibility(View.VISIBLE);
            tv_abstract.setText(abstractInfo);
        }

        if (bean.getUser_info() != null && bean.getUser_info().getAvatar_url() != null) {
            ImageView iv_avatar = holder.getView(R.id.iv_avatar);
            options = GlideImageOptions.builder()
                    .url(bean.getUser_info().getAvatar_url())
                    .placeholder(R.mipmap.ic_default_avatar)
                    .imageView(iv_avatar)
                    .build();
            ImageLoader.getInstance().loadImage(mContext, options);
        }

        final ImageView iv_dots = holder.getView(R.id.iv_dots);
        iv_dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mContext, iv_dots, Gravity.END, 0, R.style.MyPopupMenu);
                popupMenu.inflate(R.menu.menu_share);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.action_share) {
                            ShareUtil.send(mContext, title + "\n" + bean.getShare_url());
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
}
