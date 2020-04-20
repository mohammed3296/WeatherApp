package com.unicomg.robustatask.ui;

import androidx.lifecycle.MutableLiveData;

import com.unicomg.robustatask.base.BaseViewModel;
import com.unicomg.robustatask.store.local.Image;
import com.unicomg.robustatask.store.models.weather.WeatherResponse;
import com.unicomg.robustatask.store.repo.MainRepo;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainViewModel extends BaseViewModel {
    private final MainRepo mainRepo;

    MutableLiveData<WeatherResponse> weatherResponseMutableLiveData;
    MutableLiveData<List<Image>> listMutableLiveData;

    public MainViewModel(MainRepo mainRepo) {
        this.mainRepo = mainRepo;
        weatherResponseMutableLiveData = new MutableLiveData<>();
        listMutableLiveData = new MutableLiveData<>();
    }

    public void getCurrentWeather(String q, String appid) {
        addToDisposable(mainRepo.getCurrentWeather(q, appid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(ignored -> {
                    loadingLiveData.setValue(true);
                })
                .doFinally(() -> loadingLiveData.setValue(false))
                .subscribe(response -> {
                            weatherResponseMutableLiveData.setValue(response);
                        },
                        this::processError));
    }


    public void insertImage(Image image) {
        addToDisposable(mainRepo.insertImage(image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(ignored -> {
                    loadingLiveData.setValue(true);
                })
                .doFinally(() -> loadingLiveData.setValue(false))
                .subscribe(response -> {
                            errorMsgLiveData.setValue("Image added successfully");
                        },
                        this::processError));

    }

    public void getAllImages() {

        addToDisposable(mainRepo.getAllImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(ignored -> {
                    loadingLiveData.setValue(true);
                })
                .doFinally(() -> loadingLiveData.setValue(false))
                .subscribe(response -> {
                            listMutableLiveData.setValue(response);
                        },
                        this::processError));
    }
}
