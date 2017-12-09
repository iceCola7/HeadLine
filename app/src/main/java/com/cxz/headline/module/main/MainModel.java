package com.cxz.headline.module.main;

import com.cxz.headline.base.mvp.BaseModel;
import com.cxz.headline.di.scope.ActivityScope;

import javax.inject.Inject;

/**
 * Created by chenxz on 2017/12/9.
 */
@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {

    @Inject
    public MainModel(){}

}
