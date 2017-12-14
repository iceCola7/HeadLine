package com.cxz.headline.base.mvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import com.cxz.headline.http.RetrofitHelper;

/**
 * Created by chenxz on 2017/11/30.
 */

public class BaseModel implements IModel, LifecycleObserver {

    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 统一处理 网络请求 和 数据缓存
     */
    protected RetrofitHelper mRetrofitHelper;

    public BaseModel() {
        mRetrofitHelper = RetrofitHelper.getInstance();
    }

    @Override
    public void onDestroy() {
        mRetrofitHelper = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        owner.getLifecycle().removeObserver(this);
    }

}
