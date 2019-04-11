package com.marcllort.a21points.API;

import com.marcllort.a21points.Model.UserToken;

public interface LoginCallback {
    void onSuccess(UserToken userToken);
    void onFailure(Throwable t);
}