package com.cxz.headline.di.component;

import com.cxz.headline.di.module.ChannelFragmentModule;
import com.cxz.headline.di.scope.FragmentScope;
import com.cxz.headline.module.news.channel.ChannelFragment;

import dagger.Component;

/**
 * Created by chenxz on 2017/12/10.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = ChannelFragmentModule.class)
public interface ChannelFragmentComponent {
    void inject(ChannelFragment fragment);
}
