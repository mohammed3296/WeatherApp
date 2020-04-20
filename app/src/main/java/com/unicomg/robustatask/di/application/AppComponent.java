package com.unicomg.robustatask.di.application;

import com.unicomg.robustatask.RobustaTaskApplication;
import com.unicomg.robustatask.di.activity.ActivityComponent;
import com.unicomg.robustatask.di.activity.ActivityModule;
import com.unicomg.robustatask.di.application.AppModule;
import com.unicomg.robustatask.di.scope.ApplicationScope;

import dagger.Component;

@ApplicationScope
@Component(modules = {AppModule.class})
public interface AppComponent {

    ActivityComponent plus(ActivityModule activityModule);

    void inject(RobustaTaskApplication app);

}