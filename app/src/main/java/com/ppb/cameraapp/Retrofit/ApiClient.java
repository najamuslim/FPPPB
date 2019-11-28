package com.ppb.cameraapp.Retrofit;

import com.ppb.cameraapp.Model.ApiResponse;
import com.ppb.cameraapp.Model.Dataset;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiClient {

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("api/dataset/{kelompok}/{label}")
    Call<Dataset> store(
            @Path("kelompok") Integer kelompok,
            @Path("label") String label,
            @Field("image") String image
    );
}
