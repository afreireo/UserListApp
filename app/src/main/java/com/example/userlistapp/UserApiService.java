package com.example.userlistapp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserApiService {
    @GET("api")
    Call<UserResponse> getUsers();
}
