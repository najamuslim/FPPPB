package com.ppb.cameraapp.Response;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("status_code")
    String code;

    @SerializedName("message")
    String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
