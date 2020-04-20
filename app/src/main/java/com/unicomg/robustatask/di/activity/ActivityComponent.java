package com.unicomg.robustatask.di.activity;
import com.unicomg.robustatask.ui.MainActivity;
import com.unicomg.robustatask.di.fragment.FragmentComponent;
import com.unicomg.robustatask.di.fragment.FragmentModule;
import com.unicomg.robustatask.di.scope.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

    FragmentComponent plus(FragmentModule fragmentModule);

    void inject(MainActivity mainActivity);

}
