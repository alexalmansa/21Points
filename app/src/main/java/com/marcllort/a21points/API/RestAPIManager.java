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
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAPIManager {

    private static final String BASE_URL = "http://android.byted.xyz/";
    private static RestAPIManager ourInstance;
    private Retrofit retrofit;
    private RestAPIService restApiService;
    private UserToken userToken;


    public static RestAPIManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new RestAPIManager();
        }
        return ourInstance;
    }

    private RestAPIManager() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restApiService = retrofit.create(RestAPIService.class);

    }

    public synchronized void getUserToken(String username, String password, final RestAPICallBack restAPICallBack) {
        UserData userData = new UserData(username, password);
        Call<UserToken> call = restApiService.requestToken(userData);

        call.enqueue(new Callback<UserToken>() {
            @Override
            public void onResponse(Call<UserToken> call, Response<UserToken> response) {

                if (response.isSuccessful()) {
                    userToken = response.body();
                    restAPICallBack.onLoginSuccess(userToken);
                } else {
                    restAPICallBack.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<UserToken> call, Throwable t) {
                restAPICallBack.onFailure(t);
            }
        });
    }

    public synchronized void register(String username, String email, String password, final RegisterCallback registerCallback) {
        UserData userData = new UserData(username, email, password);
        Call<Void> call = restApiService.register(userData);

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

    public synchronized void postPoints(Points points, final RestAPICallBack restAPICallBack) {
        final Points newUserPoints = points;
        Call<Points> call = restApiService.postPoints(newUserPoints, "Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<Points>() {
            @Override
            public void onResponse(Call<Points> call, Response<Points> response) {

                if (response.isSuccessful()) {
                    restAPICallBack.onPostPoints(response.body());
                } else {
                    restAPICallBack.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Points> call, Throwable t) {
                restAPICallBack.onFailure(t);
            }
        });
    }

    public synchronized void getPointsById(Integer id, final RestAPICallBack restAPICallBack) {
        Call<Points> call = restApiService.getPointsById(id, "Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<Points>() {
            @Override
            public void onResponse(Call<Points> call, Response<Points> response) {

                if (response.isSuccessful()) {
                    restAPICallBack.onGetPoints(response.body());
                } else {
                    restAPICallBack.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Points> call, Throwable t) {
                restAPICallBack.onFailure(t);
            }
        });
    }

    public synchronized void getPointsByWeek(String date, final RestAPICallBack restAPICallBack) {
        Call<Points> call = restApiService.getPointsByWeek(date, "Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<Points>() {
            @Override
            public void onResponse(Call<Points> call, Response<Points> response) {

                if (response.isSuccessful()) {
                    restAPICallBack.onGetPoints(response.body());
                } else {
                    restAPICallBack.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Points> call, Throwable t) {
                restAPICallBack.onFailure(t);
            }
        });
    }

    public synchronized void postBlood(Blood blood, final RestAPICallBack restAPICallBack) {
        final Blood newUserBlood = blood;
        Call<Blood> call = restApiService.postBlood(newUserBlood, "Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<Blood>() {
            @Override
            public void onResponse(Call<Blood> call, Response<Blood> response) {

                if (response.isSuccessful()) {
                    restAPICallBack.onPostBlood(response.body());
                } else {
                    restAPICallBack.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Blood> call, Throwable t) {
                restAPICallBack.onFailure(t);
            }
        });
    }

    public synchronized void getBloodbyMonth(String date, final RestAPICallBack restAPICallBack) {
        Call<ArrayBlood> call = restApiService.getBloodByMonth(date, "Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<ArrayBlood>() {
            @Override
            public void onResponse(Call<ArrayBlood> call, Response<ArrayBlood> response) {

                if (response.isSuccessful()) {
                    restAPICallBack.onGetBlood(response.body());
                } else {
                    restAPICallBack.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayBlood> call, Throwable t) {
                restAPICallBack.onFailure(t);
            }
        });
    }

    public synchronized void postWeight(Weight weight, final RestAPICallBack restAPICallBack) {
        final Weight newUserWeight = weight;
        Call<Weight> call = restApiService.postWeight(newUserWeight, "Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<Weight>() {
            @Override
            public void onResponse(Call<Weight> call, Response<Weight> response) {

                if (response.isSuccessful()) {
                    restAPICallBack.onPostWeight(response.body());
                } else {
                    restAPICallBack.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Weight> call, Throwable t) {
                restAPICallBack.onFailure(t);
            }
        });
    }

    public synchronized void getWeightbyMonth(String date, final RestAPICallBack restAPICallBack) {
        Call<WeightPeriod> call = restApiService.getWeightByMonth(date, "Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<WeightPeriod>() {
            @Override
            public void onResponse(Call<WeightPeriod> call, Response<WeightPeriod> response) {

                if (response.isSuccessful()) {
                    restAPICallBack.onGetWeight(response.body());
                } else {
                    restAPICallBack.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<WeightPeriod> call, Throwable t) {
                restAPICallBack.onFailure(t);
            }
        });
    }

    public synchronized void getMyPreferences(final RestAPICallBack restAPICallBack) {
        Call<Preferences> call = restApiService.getMyPreferences("Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<Preferences>() {
            @Override
            public void onResponse(Call<Preferences> call, Response<Preferences> response) {

                if (response.isSuccessful()) {
                    restAPICallBack.onGetPreferences(response.body());
                } else {
                    restAPICallBack.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Preferences> call, Throwable t) {
                restAPICallBack.onFailure(t);
            }
        });
    }

    public synchronized void postPreferences(Preferences preferences, final RestAPICallBack restAPICallBack) {
        final Preferences newUserPrefences = preferences;
        Call<Preferences> call = restApiService.postPreferences(newUserPrefences, "Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<Preferences>() {
            @Override
            public void onResponse(Call<Preferences> call, Response<Preferences> response) {

                if (response.isSuccessful()) {
                    restAPICallBack.onPostPreferences(response.body());
                } else {
                    restAPICallBack.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Preferences> call, Throwable t) {
                restAPICallBack.onFailure(t);
            }
        });
    }


}