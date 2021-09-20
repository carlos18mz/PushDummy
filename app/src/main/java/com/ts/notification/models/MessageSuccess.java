package com.ts.notification.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageSuccess {

    @SerializedName("success")
    @Expose
    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @SerializedName("data")
    @Expose
    private String data;
}
