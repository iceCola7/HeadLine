package com.cxz.headline.util.imageloader;

import android.widget.ImageView;

/**
 * Created by chenxz on 2017/12/18.
 */

public class ImageOptions {

    protected String url;
    protected ImageView imageView;
    protected int placeholder = -1;// 占位符
    protected int errorPic = -1;// 错误占位符

    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public int getErrorPic() {
        return errorPic;
    }

}
