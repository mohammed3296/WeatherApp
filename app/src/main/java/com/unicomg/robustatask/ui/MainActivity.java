package com.unicomg.robustatask.ui;

import com.facebook.FacebookSdk;
import com.unicomg.robustatask.R;
import com.unicomg.robustatask.RobustaTaskApplication;
import com.unicomg.robustatask.base.BaseActivity;
import com.unicomg.robustatask.di.activity.ActivityModule;
public class MainActivity extends BaseActivity<MainViewModel> {

    @Override
    protected void initActivityComponent() {
        component = RobustaTaskApplication.getComponent(this)
                .plus(new ActivityModule(this));
        component.inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected void initActivity() {
        FacebookSdk.setApplicationId("114473019407167");
        FacebookSdk.sdkInitialize(getApplicationContext());

    }
}
