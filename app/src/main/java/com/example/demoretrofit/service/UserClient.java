package com.example.demoretrofit.service;

import com.example.demoretrofit.model.Login;
import com.example.demoretrofit.model.Products;
import com.example.demoretrofit.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {

    @POST("/auth/login")
    Call<User> login(@Body Login login);

    @GET("products")
    Call<List<Products>> getSecret(@Header("Authorization") String authToken);

}
