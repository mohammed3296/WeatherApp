package com.unicomg.robustatask;
import android.app.Application;
import android.content.Context;
import com.hadilq.liveevent.BuildConfig;
import com.unicomg.robustatask.di.application.AppComponent;
import com.unicomg.robustatask.di.application.AppModule;
import com.unicomg.robustatask.di.application.DaggerAppComponent;

import timber.log.Timber;

public class RobustaTaskApplication extends Application {

    private static Context context;
    private final AppComponent appComponent = createAppComponent();

    private static RobustaTaskApplication getApp(Context context) {
        return (RobustaTaskApplication) context.getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static AppComponent getComponent(Context context) {
        return getApp(context).appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        setupTimberTree();

    }


    private void setupTimberTree() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

}
