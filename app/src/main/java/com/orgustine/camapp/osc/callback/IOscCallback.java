package com.orgustine.camapp.osc.callback;

public interface IOscCallback {

    default void onStartRequest() {
    }

    void onSuccessful(Object object);

    void onError(String message);

}
