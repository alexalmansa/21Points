package com.marcllort.a21points.API;

import com.marcllort.a21points.Model.ArrayBlood;
import com.marcllort.a21points.Model.Blood;
import com.marcllort.a21points.Model.Points;
import com.marcllort.a21points.Model.Preferences;
import com.marcllort.a21points.Model.UserToken;
import com.marcllort.a21points.Model.Weight;
import com.marcllort.a21points.Model.WeightPeriod;

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