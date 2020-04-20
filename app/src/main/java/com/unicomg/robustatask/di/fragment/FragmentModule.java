package com.unicomg.robustatask.di.fragment;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.unicomg.robustatask.di.qualifier.ForFragment;
import com.unicomg.robustatask.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    private final Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @FragmentScope
    @Provides
    Fragment provideFragment() {
        return fragment;
    }

    @FragmentScope
    @ForFragment
    @Provides
    Context provideFragmentContext() {
        return fragment.getContext();
    }

    @FragmentScope
    @ForFragment
    @Provides
    FragmentManager fragmentManager() {
        return fragment.getChildFragmentManager();
    }

    @FragmentScope
    @ForFragment
    @Provides
    Activity provideActivity() {
        return fragment.getActivity();
    }

}