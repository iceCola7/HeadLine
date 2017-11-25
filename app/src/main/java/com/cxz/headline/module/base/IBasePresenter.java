package com.cxz.headline.module.base;

/**
 * Created by chenxz on 2017/11/25.
 */

public interface IBasePresenter {

    /**
     * 刷新数据
     */
    void doRefresh();

    /**
     * 显示网络错误
     */
    void doShowNetError();
}
