package com.marcllort.a21points.API;


import com.marcllort.a21points.Model.UserData;
import com.marcllort.a21points.Model.UserToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserTokenManager {

    private static final String BASE_URL = "http://android.byted.xyz/";
    private static UserTokenManager ourInstance;
    private Retrofit retrofit;
    private TokenService tokenService;
    private UserToken userToken;


    public static UserTokenManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new UserTokenManager();
        }
        return ourInstance;
    }

    private UserTokenManager() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tokenService = retrofit.create(TokenService.class);

    }

    public synchronized void getUserToken(String username, String password, final LoginCallback loginCallback) {
        UserData userData = new UserData(username, password);
        Call<UserToken> call = tokenService.requestToken(userData);

        call.enqueue(new Callback<UserToken>() {
            @Override
            public void onResponse(Call<UserToken> call, Response<UserToken> response) {
                userToken = response.body();

                if (response.isSuccessful()) {
                    loginCallback.onSuccess(userToken);
                } else {
                    loginCallback.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<UserToken> call, Throwable t) {
                loginCallback.onFailure(t);
            }
        });
    }

    public synchronized void register(String username, String email, String password, final RegisterCallback registerCallback) {
        UserData userData = new UserData(username, email, password);
        Call<Void> call = tokenService.register(userData);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    registerCallback.onSuccess();
                } else {
                    registerCallback.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                registerCallback.onFailure(t);
            }
        });
    }

}