package com.underphones.smartfestival.controller;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by moskis on 1/3/15.
 */
public class Client {

    private static final String BASE_URL = "http://151.80.142.200";
    private static final String BASE_VERSION = "/DataFestPHP";

    public IClientCodeEvents eventHandler;

    private Context mContext;
    AsyncHttpClient myClient;

    private Client(){
    }

    public Client(IClientCodeEvents eventHandler, Context mContext){
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

    public void register(String token, String email){
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("email", email);
        myClient.put(getAbsolutePath()+"/register.php", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                eventHandler.CodeFinished(METHOD_REGISTER,response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
                //JSONObject firstEvent = timeline.get(0);
                //String tweetText = firstEvent.getString("text");

                // Do something with the response
                //System.out.println(tweetText);
            }
        });
    }


    /**
     *
     * @return devuelve la url compuesta de peticiones a los ws
     */
    private String getAbsolutePath(){
        return BASE_URL+BASE_VERSION;
    }


    //CONSTANTS
    public final String METHOD_REGISTER="/register.php";
}
