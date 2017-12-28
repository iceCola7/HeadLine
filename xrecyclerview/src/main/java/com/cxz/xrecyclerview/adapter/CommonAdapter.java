package com.cxz.xrecyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import com.cxz.xrecyclerview.adapter.base.BaseItemDelegate;
import com.cxz.xrecyclerview.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by chenxz on 2017/12/28.
 */

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public CommonAdapter(Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);

        addItemViewDelegate(new BaseItemDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(BaseViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });

    }

    protected abstract void convert(BaseViewHolder holder, T t, int position);
}
