package com.cxz.xrecyclerview.adapter.base;

import android.support.v4.util.SparseArrayCompat;

/**
 * Created by chenxz on 2017/12/28.
 */

public class ItemDelegateManager<T> {

    SparseArrayCompat<BaseItemDelegate<T>> delegates = new SparseArrayCompat<>();

    public int getItemDelegateCount() {
        return delegates.size();
    }

    public ItemDelegateManager<T> addDelegate(BaseItemDelegate<T> delegate) {
        int viewType = delegates.size();
        if (delegate != null) {
            delegates.put(viewType, delegate);
            viewType++;
        }
        return this;
    }

    public ItemDelegateManager<T> addDelegate(int viewType, BaseItemDelegate<T> delegate) {
        if (delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An BaseItemDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered BaseItemDelegate is "
                            + delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }

    public ItemDelegateManager<T> removeDelegate(BaseItemDelegate<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("BaseItemDelegate is null");
        }
        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    public ItemDelegateManager<T> removeDelegate(int itemType) {
        int indexToRemove = delegates.indexOfKey(itemType);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    public int getItemViewType(T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            BaseItemDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                return delegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No BaseItemDelegate added that matches position=" + position + " in data source");
    }

    public void convert(BaseViewHolder holder, T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            BaseItemDelegate<T> delegate = delegates.valueAt(i);

            if (delegate.isForViewType(item, position)) {
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemDelegateManager added that matches position=" + position + " in data source");
    }

    public BaseItemDelegate getItemDelegate(int viewType) {
        return delegates.get(viewType);
    }

    public int getItemViewLayoutId(int viewType) {
        return getItemDelegate(viewType).getItemViewLayoutId();
    }

    public int getItemViewType(BaseItemDelegate BaseItemDelegate) {
        return delegates.indexOfValue(BaseItemDelegate);
    }

}
