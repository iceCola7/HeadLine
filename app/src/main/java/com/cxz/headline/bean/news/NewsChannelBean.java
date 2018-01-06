package com.cxz.headline.bean.news;

import android.support.annotation.NonNull;

import io.realm.RealmObject;

/**
 * Created by chenxz on 2017/12/10.
 */
public class NewsChannelBean extends RealmObject implements Comparable<NewsChannelBean> {

    private String channelId;
    private String channelName;
    private boolean isEnable;
    private int position;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean getIsEnable() {
        return this.isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        NewsChannelBean bean = (NewsChannelBean) o;

        if (isEnable != bean.isEnable)
            return false;
        if (position != bean.position)
            return false;
        if (channelId != null ? !channelId.equals(bean.channelId) : bean.channelId != null)
            return false;
        return channelName != null ? channelName.equals(bean.channelName) : bean.channelName == null;

    }

    @Override
    public int hashCode() {
        int result = channelId != null ? channelId.hashCode() : 0;
        result = 31 * result + (channelName != null ? channelName.hashCode() : 0);
        result = 31 * result + (isEnable ? 1 : 0);
        result = 31 * result + position;
        return result;
    }

    @Override
    public int compareTo(@NonNull NewsChannelBean o) {
        return this.position - o.getPosition();
    }
}
