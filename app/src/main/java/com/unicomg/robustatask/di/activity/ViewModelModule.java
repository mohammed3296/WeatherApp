package com.unicomg.robustatask.di.activity;

import androidx.lifecycle.ViewModel;

import com.unicomg.robustatask.ui.MainViewModel;
import com.unicomg.robustatask.base.ViewModelFactory;
import com.unicomg.robustatask.store.repo.MainRepo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import javax.inject.Provider;

import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;


@Module
public class ViewModelModule {

    @Provides
    ViewModelFactory viewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        return new ViewModelFactory(providerMap);
    }


    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    ViewModel mainViewModel(MainRepo mainRepo) {
        return new MainViewModel(mainRepo);
    }


    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }


}
