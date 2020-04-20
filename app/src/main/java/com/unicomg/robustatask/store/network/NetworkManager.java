package com.unicomg.robustatask.store.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unicomg.robustatask.di.qualifier.ForActivity;
import com.unicomg.robustatask.di.scope.ActivityScope;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class NetworkManager {

    private final static Map<String, String> headers = new HashMap<>();
    private final APIService apiService;

    @Inject
    public NetworkManager(@ForActivity APIService apiService) {
        this.apiService = apiService;
    }

    public <T> Observable<T> getRequest(String api, Map<String, Object> param, Class<T> parseClass) {

        if (param == null)

            param = new HashMap<>();


        return apiService.getRequest(api, headers, param).map(jsonElement -> {

            Gson gson = new GsonBuilder().serializeNulls().create();

            return gson.fromJson(jsonElement, parseClass);

        });

    }
}
