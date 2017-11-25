package com.cxz.headline.module.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenxz on 2017/11/25.
 */

public abstract class BaseFragment<T extends IBasePresenter> extends RxFragment implements IBaseView<T> {

    protected T presenter;
    private Unbinder mUnbinder;

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    protected abstract int attachLayoutId();

    /**
     * 初始化视图控件
     *
     * @param view
     */
    protected abstract void initView(View view);

    /**
     * 初始化数据
     *
     * @throws NullPointerException
     */
    protected abstract void initData() throws NullPointerException;

    /**
     * 初始化Toolbar
     *
     * @param toolbar
     * @param homeAsUpEnabled
     * @param title
     */
    protected void initToolbar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        ((BaseActivity) getActivity()).initToolbar(toolbar, homeAsUpEnabled, title);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(attachLayoutId(), container, false);
        // 返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        mUnbinder = ButterKnife.bind(this,view);
        initView(view);
        initData();
        return view;
    }

    /**
     * 绑定生命周期
     *
     * @param <T1>
     * @return
     */
    @Override
    public <T1> LifecycleTransformer<T1> bindToLife() {
        return bindUntilEvent(FragmentEvent.DESTROY);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // ButterKnife解绑
        mUnbinder.unbind();
    }
}
