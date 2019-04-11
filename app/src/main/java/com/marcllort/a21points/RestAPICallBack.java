package com.marcllort.a21points;

public interface RestAPICallBack {
    void onPostPoints(Points points);
    void onGetPoints(Points points);
    void onPostBlood(Blood blood);
    void onGetBlood(ArrayBlood arrayblood);
    void onPostWeight(Weight weight);
    void onGetWeight(WeightPeriod weight);
    void onLoginSuccess(UserToken userToken);
    void onFailure(Throwable t);
    void onPostPreferences(Preferences preferences);
    void onGetPreferences(Preferences preferences);
}