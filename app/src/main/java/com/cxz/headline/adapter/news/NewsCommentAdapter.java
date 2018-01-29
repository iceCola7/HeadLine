package com.cxz.headline.adapter.news;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;

import com.cxz.headline.R;
import com.cxz.headline.base.BaseActivity;
import com.cxz.headline.bean.news.NewsCommentBean;
import com.cxz.headline.util.ShareUtil;
import com.cxz.headline.util.imageloader.ImageLoader;
import com.cxz.headline.util.imageloader.ImageOptions;
import com.cxz.headline.util.imageloader.glide.GlideImageOptions;
import com.cxz.headline.widget.BottomSheetDialogFixed;
import com.cxz.xrecyclerview.adapter.CommonAdapter;
import com.cxz.xrecyclerview.adapter.base.BaseViewHolder;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * Created by chenxz on 2018/1/29.
 */

public class NewsCommentAdapter extends CommonAdapter<NewsCommentBean.DataBean.CommentBean> {

    private ImageOptions options;

    public NewsCommentAdapter(Context context, int layoutId, List<NewsCommentBean.DataBean.CommentBean> datas) {
        super(context, layoutId, datas);
    }

    public void appendDatas(List<NewsCommentBean.DataBean.CommentBean> lists) {
        mDatas.addAll(lists);
        notifyDataSetChanged();
    }

    public void setDatas(List<NewsCommentBean.DataBean.CommentBean> lists) {
        mDatas.clear();
        appendDatas(lists);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final NewsCommentBean.DataBean.CommentBean bean, int position) {
        holder.setText(R.id.tv_username, bean.getUser_name())
                .setText(R.id.tv_text, bean.getText())
                .setText(R.id.tv_likes, bean.getDigg_count() + "赞");

        ImageView iv_avatar = holder.getView(R.id.iv_avatar);
        options = GlideImageOptions.builder()
                .url(bean.getUser_profile_image_url())
                .placeholder(R.mipmap.ic_default_avatar)
                .imageView(iv_avatar)
                .build();
        ImageLoader.getInstance().loadImage(mContext, options);

        RxView.clicks(holder.getItemView())
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        final String content = bean.getText();
                        final BottomSheetDialogFixed dialog = new BottomSheetDialogFixed(mContext);
                        dialog.setOwnerActivity((BaseActivity) mContext);
                        View view = ((BaseActivity) mContext).getLayoutInflater().inflate(R.layout.item_comment_action_sheet, null);
                        view.findViewById(R.id.layout_copy_text).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ClipboardManager copy = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clipData = ClipData.newPlainText("text", content);
                                copy.setPrimaryClip(clipData);
                                Snackbar.make(holder.itemView, R.string.copied_to_clipboard, Snackbar.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                        view.findViewById(R.id.layout_share_text).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ShareUtil.send(mContext, content);
                                dialog.dismiss();
                            }
                        });
                        dialog.setContentView(view);
                        dialog.show();
                    }
                });
    }
}
