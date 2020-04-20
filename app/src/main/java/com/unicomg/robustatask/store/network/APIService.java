package com.unicomg.robustatask.store.network;

import com.google.gson.JsonElement;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface APIService {

    @GET
    Observable<JsonElement> getRequest(@Url String api, @HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> param);

}
