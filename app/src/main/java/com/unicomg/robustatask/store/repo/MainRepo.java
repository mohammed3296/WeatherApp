package com.unicomg.robustatask.store.repo;

import com.unicomg.robustatask.di.qualifier.ForActivity;
import com.unicomg.robustatask.di.scope.ActivityScope;
import com.unicomg.robustatask.store.local.Image;
import com.unicomg.robustatask.store.local.ImagesDatabase;
import com.unicomg.robustatask.store.models.weather.WeatherResponse;
import com.unicomg.robustatask.store.network.NetworkConstants;
import com.unicomg.robustatask.store.network.NetworkManager;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;


@ActivityScope
public class MainRepo {

    private final NetworkManager networkManager;
    ImagesDatabase appDatabase;


    public MainRepo(@ForActivity NetworkManager networkManager, ImagesDatabase appDatabase) {
        this.networkManager = networkManager;
        this.appDatabase = appDatabase;
    }


    public Observable<WeatherResponse> getCurrentWeather(String q, String appid) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("q", q);
        params.put("appid", appid);
        return networkManager.getRequest(NetworkConstants.CURRENT_WEATHER, params, WeatherResponse.class);
    }

    public Single<Long> insertImage(Image image) {
        return appDatabase.imageDao().insert(image);
    }

    public Single<List<Image>> getAllImages() {
        return appDatabase.imageDao().getAllImages();
    }

}
