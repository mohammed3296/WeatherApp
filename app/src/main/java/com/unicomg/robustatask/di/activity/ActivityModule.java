package com.unicomg.robustatask.di.activity;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.unicomg.robustatask.base.BaseActivity;
import com.unicomg.robustatask.di.qualifier.ForActivity;
import com.unicomg.robustatask.di.scope.ActivityScope;
import com.unicomg.robustatask.store.local.ImagesDatabase;
import com.unicomg.robustatask.store.network.NetworkManager;
import com.unicomg.robustatask.store.repo.MainRepo;

import dagger.Module;
import dagger.Provides;


@Module(includes = {ViewModelModule.class, NetworkModule.class})
public class ActivityModule {

    private final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    BaseActivity provideActivity() {
        return activity;
    }

    @ActivityScope
    @ForActivity
    @Provides
    FragmentManager provideFragmentManager() {
        return activity.getSupportFragmentManager();
    }

    @ActivityScope
    @Provides
    @ForActivity
    Context provideActivityContext() {
        return activity;
    }



    @ActivityScope
    @Provides
    MainRepo provideMainRepo(NetworkManager apiService , ImagesDatabase imagesDatabase) {
        return new MainRepo(apiService  ,  imagesDatabase);
    }
}