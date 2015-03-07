package com.underphones.smartfestival.controller;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.underphones.smartfestival.ContactBean;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
        myClient.post(getAbsolutePath() + METHOD_REGISTER, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                eventHandler.CodeFinished(METHOD_REGISTER, null);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("Error", new String(responseBody));
                eventHandler.CodeFinishedWithException(statusCode, METHOD_REGISTER, error);
            }

        });
    }

    public void sendPosition(LatLng pos){
        RequestParams params = new RequestParams();
        params.put("x", pos.latitude);
        params.put("y", pos.longitude);
        myClient.post(getAbsolutePath()+METHOD_SEND_POSITION, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                eventHandler.CodeFinished(METHOD_SEND_POSITION,null);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("Error",new String(responseBody));
                eventHandler.CodeFinishedWithException(statusCode,METHOD_SEND_POSITION,error);
            }

        });
    }

    public void getUsers(){
        RequestParams params = new RequestParams();
        myClient.post(getAbsolutePath()+METHOD_GET_USERS, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject jo = new JSONObject(new String(responseBody));
                    JSONArray data=jo.getJSONArray("data");
                    List<ContactBean> list=new ArrayList<ContactBean>();
                    for (int i=0;i<data.length();i++){
                        ContactBean cb=new ContactBean();
                        JSONObject jcb=data.getJSONObject(i);
                        cb.setEmail(jcb.getString("email"));
                        cb.setImage(jcb.getString("image"));
                        cb.setName(jcb.getString("name"));
                        list.add(cb);
                    }
                    eventHandler.CodeFinished(METHOD_GET_USERS,list);
                }catch (JSONException e){
                    eventHandler.CodeFinishedWithException(200,METHOD_GET_USERS,new Throwable(e.getMessage()));
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("Error",new String(responseBody));
                eventHandler.CodeFinishedWithException(statusCode,METHOD_GET_USERS,error);
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
    public final String METHOD_SEND_POSITION="/sendposition.php";
    public final String METHOD_GET_USERS="/getUsers.php";
}
