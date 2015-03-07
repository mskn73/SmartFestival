package com.underphones.smartfestival.controller;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.underphones.smartfestival.BuildConfig;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by moskis on 1/3/15.
 */
public class BaseClient {

    private static final String BASE_URL = "http://192.168.0.14/__ajax__";
    private static final String BASE_VERSION = "/metric";

    public IClientCodeEvents eventHandler;

    private Context mContext;
    AsyncHttpClient myClient;

    private BaseClient(){
    }

    public BaseClient(IClientCodeEvents eventHandler, Context mContext){
        setUpClient(mContext);
        this.eventHandler = eventHandler;
    }

    private void setUpClient(Context mContext){
        this.mContext=mContext;
        myClient = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(mContext);
        myClient.setCookieStore(myCookieStore);
        myClient.setMaxRetriesAndTimeout(3,10000);
        myClient.addHeader("Accept", "application/json");
        myClient.addHeader("Content-type", "application/json");
    }

    public void postJSON(JSONObject jsonParams, final String method) {
        if (eventHandler == null)
            throw new RuntimeException("You must implement IClientCodeEvents");

        try {
            if (jsonParams == null)
                jsonParams = new JSONObject();

            StringEntity entity = new StringEntity(jsonParams.toString());

            myClient.post(mContext, getAbsolutePath() + method, entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    eventHandler.CodeFinished(method,responseBody);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    eventHandler.CodeFinishedWithException(statusCode,method,error);
                }

                @Override
                public void onStart() {
                    super.onStart();
                    eventHandler.CodeStartedRequest(method);
                }
            });
        }catch (UnsupportedEncodingException e){
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        }
    }


    /**
     *
     * @return devuelve la url compuesta de peticiones a los ws
     */
    private String getAbsolutePath(){
        return BASE_URL+BASE_VERSION;
    }
}
