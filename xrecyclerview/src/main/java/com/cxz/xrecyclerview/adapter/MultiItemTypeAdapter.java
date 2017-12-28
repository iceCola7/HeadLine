package com.cxz.xrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cxz.xrecyclerview.adapter.base.BaseItemDelegate;
import com.cxz.xrecyclerview.adapter.base.BaseViewHolder;
import com.cxz.xrecyclerview.adapter.base.ItemDelegateManager;

import java.util.List;

/**
 * Created by chenxz on 2017/12/28.
 */

public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected Context mContext;
    protected List<T> mDatas;

    protected ItemDelegateManager mItemDelegateManager;
    protected OnItemClickListener mOnItemClickListener;

    public MultiItemTypeAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemDelegateManager = new ItemDelegateManager();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager())
            return super.getItemViewType(position);
        return mItemDelegateManager.getItemViewType(mDatas.get(position), position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseItemDelegate itemDelegate = mItemDelegateManager.getItemDelegate(viewType);
        int layoutId = itemDelegate.getItemViewLayoutId();
        BaseViewHolder holder = BaseViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder, holder.getItemView());
        setListener(parent, holder, viewType);
        return holder;
    }

    public void onViewHolderCreated(BaseViewHolder holder, View itemView) {

    }

    public void convert(BaseViewHolder holder, T t) {
        mItemDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    protected void setListener(final ViewGroup parent, final BaseViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType))
            return;
        viewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder, position);
                }
            }
        });

        viewHolder.getItemView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public MultiItemTypeAdapter addItemViewDelegate(BaseItemDelegate<T> itemViewDelegate) {
        mItemDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, BaseItemDelegate<T> itemViewDelegate) {
        mItemDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemDelegateManager.getItemDelegateCount() > 0;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

}
