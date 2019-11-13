package com.ppb.cameraapp.Retrofit;

import com.ppb.cameraapp.Response.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiClient {
    @FormUrlEncoded
    @POST("/dataset/{KELOMPOK}/{LABEL}")
    Call<ApiResponse> dataset(
            @Field("kelompok") Integer kel,
            @Field("label") String label,
            @Field("image") String image
    );
}
