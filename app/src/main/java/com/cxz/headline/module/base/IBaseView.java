package com.cxz.headline.module.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by chenxz on 2017/11/25.
 */

public interface IBaseView<T> {

    /**
     * 显示加载动画
     */
    void onShowLoading();

    /**
     * 隐藏动画
     */
    void onHideLoading();

    /**
     * 显示网络错误
     */
    void onShowNetError();

    /**
     * 设置presenter
     *
     * @param presenter
     */
    void setPresenter(T presenter);

    /**
     * 绑定生命周期
     *
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindToLife();
}
