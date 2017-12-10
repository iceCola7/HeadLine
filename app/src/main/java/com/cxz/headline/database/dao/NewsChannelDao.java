package com.cxz.headline.database.dao;

import com.cxz.headline.R;
import com.cxz.headline.app.App;
import com.cxz.headline.bean.news.NewsChannelBean;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by chenxz on 2017/12/10.
 */

public class NewsChannelDao {

    private Realm mRealm;

    public NewsChannelDao() {
        mRealm = Realm.getDefaultInstance();
    }

    /**
     * 初始化数据
     */
    public void addInitData() {
        String newsIds[] = App.getInstance().getResources().getStringArray(R.array.tab_news_id);
        String newsNames[] = App.getInstance().getResources().getStringArray(R.array.tab_news_name);

        NewsChannelBean bean = new NewsChannelBean();
        for (int i = 0; i < 8; i++) {
            bean.setChannelId(newsIds[i]);
            bean.setChannelName(newsNames[i]);
            bean.setIsEnable(true);
            bean.setPosition(i);
            add(bean);
        }
        for (int i = 8; i < newsIds.length; i++) {
            bean.setChannelId(newsIds[i]);
            bean.setChannelName(newsNames[i]);
            bean.setIsEnable(false);
            bean.setPosition(i);
            add(bean);
        }
    }

    /**
     * 增加
     *
     * @param bean
     */
    public void add(NewsChannelBean bean) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(bean);
        mRealm.commitTransaction();
    }

    /**
     * 根据 isEnable 查询所有记录
     *
     * @param isEnable
     * @return
     */
    public List<NewsChannelBean> queryByEnable(boolean isEnable) {
        RealmResults<NewsChannelBean> results = mRealm.where(NewsChannelBean.class).equalTo("isEnable", isEnable).findAll();
        return mRealm.copyFromRealm(results);
    }

}
