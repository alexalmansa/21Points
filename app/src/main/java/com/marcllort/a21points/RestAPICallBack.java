package com.marcllort.a21points;

public interface RestAPICallBack {
    void onPostPoints(Points points);
    void onGetPoints(Points points);
    void onPostBlood(Blood blood);
    void onGetBlood(Blood blood);
    void onPostWeight(Weight weight);
    void onGetWeight(Weight weight);
    void onLoginSuccess(UserToken userToken);
    void onFailure(Throwable t);
}