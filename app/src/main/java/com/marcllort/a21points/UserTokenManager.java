package com.marcllort.a21points;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserTokenManager {

    private static final String BASE_URL = "http://192.168.1.173:8080/";
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
        UserLogin userLogin = new UserLogin(username, password);
        Call<UserToken> call = tokenService.requestToken(userLogin);

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

}