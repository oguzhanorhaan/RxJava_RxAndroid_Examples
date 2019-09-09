package com.example.rxjava_retrofit_example.Models;

import com.example.rxjava_retrofit_example.Clients.BaseResponse;
import com.google.gson.annotations.SerializedName;

public class User extends BaseResponse {

    @SerializedName("api_key")
    String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}
