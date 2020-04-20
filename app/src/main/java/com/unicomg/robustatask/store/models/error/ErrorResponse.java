package com.unicomg.robustatask.store.models.error;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public   class ErrorResponse {
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("cod")
    private int cod;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }
}
