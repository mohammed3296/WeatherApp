package com.unicomg.robustatask.di.application;

import android.app.Application;
import android.content.Context;

import com.unicomg.robustatask.RobustaTaskApplication;
import com.unicomg.robustatask.di.qualifier.ForApplication;
import com.unicomg.robustatask.di.scope.ApplicationScope;
import com.unicomg.robustatask.store.local.ImagesDatabase;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {

    private final RobustaTaskApplication application;

    public AppModule(RobustaTaskApplication application) {
        this.application = application;
     }

    @ApplicationScope
    @Provides
    Application providesApplication() {
        return application;
    }

    @ApplicationScope
    @Provides
    @ForApplication
    Context providesApplicationContext() {
        return application;
    }

    @ApplicationScope
    @Provides
    ImagesDatabase providesImagesDatabase() {
        return ImagesDatabase.getAppDatabase(application);
    }


}