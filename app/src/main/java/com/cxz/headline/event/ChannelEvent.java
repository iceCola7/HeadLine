package com.cxz.headline.event;

/**
 * Created by chenxz on 2018/1/6.
 */

public class ChannelEvent implements IEvent {

    private String message;

    public ChannelEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
