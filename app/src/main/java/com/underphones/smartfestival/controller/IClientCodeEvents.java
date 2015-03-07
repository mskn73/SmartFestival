package com.underphones.smartfestival.controller;

/**
 * Created by moskis on 1/3/15.
 */
public interface IClientCodeEvents {

    public void CodeStartedRequest(String methodName);
    public void CodeFinished(String methodName, byte[] data);
    public void CodeFinishedWithException(int statusCode, String methodName, Throwable ex);

}
