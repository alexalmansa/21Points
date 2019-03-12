package com.marcllort.a21points;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TokenService {
    @POST("/api/authenticate")
    Call<UserToken> requestToken(@Body UserLogin userLogin);
}