package com.unicomg.robustatask.di.activity;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.unicomg.robustatask.di.qualifier.ForActivity;
import com.unicomg.robustatask.di.scope.ActivityScope;
import com.unicomg.robustatask.store.network.APIService;
import com.unicomg.robustatask.store.network.NetworkConstants;
import com.unicomg.robustatask.store.network.NetworkManager;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class NetworkModule {

    @ActivityScope
    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    HttpUrl originalUrl = originalRequest.url();
                    HttpUrl.Builder urlBuilder = originalUrl.newBuilder();
                    HttpUrl url = urlBuilder.build();
                    Request.Builder requestBuilder = originalRequest.newBuilder()
                            .url(url);
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }).build();
    }


    @ActivityScope
    @Provides
    @ForActivity
    APIService provideAPIService(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(NetworkConstants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.newThread()))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(APIService.class);
    }

    @ActivityScope
    @Provides
    @ForActivity
    NetworkManager provideNetworkManager(APIService apiService) {
        return new NetworkManager(apiService);
    }

}
