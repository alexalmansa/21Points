package com.marcllort.a21points.API;

import com.marcllort.a21points.Model.ArrayBlood;
import com.marcllort.a21points.Model.Blood;
import com.marcllort.a21points.Model.Points;
import com.marcllort.a21points.Model.Preferences;
import com.marcllort.a21points.Model.UserData;
import com.marcllort.a21points.Model.UserToken;
import com.marcllort.a21points.Model.Weight;
import com.marcllort.a21points.Model.WeightPeriod;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestAPIService {

    @POST("/api/authenticate")
    Call<UserToken> requestToken(@Body UserData userData);

    @POST("/api/register")
    Call<Void> register(@Body UserData userData);

    @POST("/api/points")
    Call<Points> postPoints(@Body Points points, @Header("Authorization") String token);

    @POST("/api/blood-pressures")
    Call<Blood> postBlood(@Body Blood blood, @Header("Authorization") String token);

    @POST("/api/weights")
    Call<Weight> postWeight(@Body Weight weight, @Header("Authorization") String token);

    @GET("/api/points/{id}")
    Call<Points> getPointsById(@Path("id") Integer id, @Header("Authorization") String token);

    @GET("/api/points-by-week/{date}")
    Call<Points> getPointsByWeek(@Path("date") String date, @Header("Authorization") String token);

    @GET("/api/blood-pressures/{id}")
    Call<Blood> getBloodById(@Path("id") Integer id, @Header("Authorization") String token);

    @GET("/api/bp-by-month/{date}")
    Call<ArrayBlood> getBloodByMonth(@Path("date") String date, @Header("Authorization") String token);

    @GET("/api/weights/{id}")
    Call<Weight> getWeightById(@Path("id") Integer id, @Header("Authorization") String token);

    @GET("/api/weight-by-month/{date}")
    Call<WeightPeriod> getWeightByMonth(@Path("date") String date, @Header("Authorization") String token);

    @POST("/api/preferences")
    Call<Preferences> postPreferences(@Body Preferences preferences, @Header("Authorization") String token);

    @GET("/api/my-preferences")
    Call<Preferences> getMyPreferences(@Header("Authorization") String token);

}