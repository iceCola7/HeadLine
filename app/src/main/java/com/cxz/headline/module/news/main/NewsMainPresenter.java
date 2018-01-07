package com.cxz.headline.module.news.main;

import com.cxz.headline.base.mvp.BasePresenter;
import com.cxz.headline.base.mvp.IModel;
import com.cxz.headline.bean.news.NewsChannelBean;
import com.cxz.headline.database.dao.NewsChannelDao;
import com.cxz.headline.event.ChannelEvent;
import com.cxz.headline.event.IEvent;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by chenxz on 2017/12/10.
 */

public class NewsMainPresenter extends BasePresenter<IModel, NewsMainContract.View> implements NewsMainContract.Presenter {

    private NewsChannelDao mDao;

    @Inject
    public NewsMainPresenter(NewsMainContract.View view) {
        super(view);
        mDao = new NewsChannelDao();
    }

    /**
     * 接受信号，更新Channel
     *
     * @param event
     */
    @Subscriber(mode = ThreadMode.MAIN)
    public void updateChannel(IEvent event) {
        if (event instanceof ChannelEvent) {
            mView.updateChannel();
        }
    }

    @Override
    public List<NewsChannelBean> getChannelBeans(boolean isEnable) {
        return mDao.queryByEnable(isEnable);
    }

    @Override
    public void initChannelData() {
        mDao.addInitData();
    }
}
