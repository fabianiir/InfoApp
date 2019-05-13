package com.example.infosyst.infosyst;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Servicio {
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("login/")
    Call<ObjetoRes> login(@Field("username") String username, @Field("Fire_token") String fire_token);
}
