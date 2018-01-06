package com.cxz.headline.module.news.channel;

import com.cxz.headline.base.mvp.BasePresenter;
import com.cxz.headline.bean.news.NewsChannelBean;
import com.cxz.headline.database.dao.NewsChannelDao;
import com.cxz.headline.event.ChannelEvent;

import org.simple.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by chenxz on 2018/1/6.
 */

public class NewsChannelPresenter extends BasePresenter<NewsChannelContract.Model, NewsChannelContract.View> implements NewsChannelContract.Presenter {

    private NewsChannelDao mDao;

    @Inject
    public NewsChannelPresenter(NewsChannelContract.Model model, NewsChannelContract.View view) {
        super(model, view);
        mDao = new NewsChannelDao();
    }

    @Override
    public List<NewsChannelBean> getChannelBeans(boolean isEnable) {
        return mDao.queryByEnable(isEnable);
    }

    @Override
    public void saveChannelData(final List<NewsChannelBean> myBeans, final List<NewsChannelBean> otherBeans) {
        final List<NewsChannelBean> enableBeans = getChannelBeans(true);
        boolean flag = !compare(enableBeans, myBeans);
        if (flag) {
            mDao.removeAll();
            NewsChannelBean bean;
            for (int i = 0; i < myBeans.size(); i++) {
                bean = myBeans.get(i);
                bean.setIsEnable(true);
                mDao.add(bean);
            }
            for (int i = 0; i < otherBeans.size(); i++) {
                bean = otherBeans.get(i);
                bean.setIsEnable(false);
                mDao.add(bean);
            }
            EventBus.getDefault().post(new ChannelEvent("update_channel"));
        }
//        Observable
//                .create(new ObservableOnSubscribe<Boolean>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
//                        boolean b = !compare(enableBeans, myBeans);
//                        e.onNext(b);
//                    }
//                })
//                .doOnNext(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(Boolean aBoolean) throws Exception {
//                        Log.e(TAG, "accept: " + aBoolean);
//                        if (aBoolean) {
//                            mDao.removeAll();
//                            NewsChannelBean bean;
//                            for (int i = 0; i < myBeans.size(); i++) {
//                                bean = myBeans.get(i);
//                                bean.setIsEnable(true);
//                                mDao.add(bean);
//                            }
//                            for (int i = 0; i < otherBeans.size(); i++) {
//                                bean = otherBeans.get(i);
//                                bean.setIsEnable(false);
//                                mDao.add(bean);
//                            }
//                        }
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(Boolean aBoolean) throws Exception {
//                        // TODO: 2018/1/6 发射信号更新
//                        Log.e(TAG, "accept: 发射信号");
//                        EventBus.getDefault().post(new ChannelEvent("update_channel"));
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//
//                    }
//                });
    }

    /**
     * 比较两个List
     *
     * @param a
     * @param b
     * @param <T>
     * @return
     */
    private <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
        if (a.size() != b.size())
            return false;
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i)))
                return false;
        }
        return true;
    }

}
