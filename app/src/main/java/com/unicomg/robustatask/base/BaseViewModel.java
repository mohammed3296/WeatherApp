package com.unicomg.robustatask.base;

import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.hadilq.liveevent.LiveEvent;
import com.unicomg.robustatask.store.models.error.ErrorResponse;

import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public class BaseViewModel extends ViewModel {

    public LiveEvent<String> errorMsgLiveData = new LiveEvent<>();
    public LiveEvent<String> successMsgLiveData = new LiveEvent<>();
    public LiveEvent<String> warningMsgLiveData = new LiveEvent<>();
    public LiveEvent<Boolean> loadingLiveData = new LiveEvent<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    public void addToDisposable(Disposable disposable) {
        this.disposable.remove(disposable);
        this.disposable.add(disposable);
    }

    public void processError(Throwable throwable) {
        loadingLiveData.setValue(false);
        if (throwable instanceof HttpException) {
            if (((HttpException) throwable).code() == 401) {
                errorMsgLiveData.setValue(getHttpErrorMessage((HttpException) throwable));
            } else {
                errorMsgLiveData.setValue(getHttpErrorMessage((HttpException) throwable));
            }
        } else if (throwable instanceof IOException)
            warningMsgLiveData.setValue("No Internet Connection");
        else {
            errorMsgLiveData.setValue(throwable.getLocalizedMessage());
        }
    }

    private String getHttpErrorMessage(HttpException throwable) {
        try {
            ErrorResponse errorResponse = new Gson().fromJson(throwable.response().errorBody().string(), ErrorResponse.class);
            if (errorResponse.getMessage() != null) {
                return errorResponse.getMessage();
            } else {
                return "An error occur try again later.";
            }
        } catch (Exception e) {
            return "An error occur try again later.";
        }
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }
}
