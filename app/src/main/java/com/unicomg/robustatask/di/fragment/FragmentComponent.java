package com.unicomg.robustatask.di.fragment;


import com.unicomg.robustatask.di.scope.FragmentScope;

import dagger.Subcomponent;


@FragmentScope
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {
 
}