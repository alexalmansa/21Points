package com.marcllort.a21points;

public interface RestAPICallBack {
    void onPostPoints(Points points);
    void onGetPoints(Points points);
    void onLoginSuccess(UserToken userToken);
    void onFailure(Throwable t);
}