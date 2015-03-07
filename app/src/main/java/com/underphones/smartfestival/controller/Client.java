package com.underphones.smartfestival.controller;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by moskis on 1/3/15.
 */
public class Client extends BaseClient{
    public Client(IClientCodeEvents eventHandler, Context mContext){
        super(eventHandler,mContext);
    }

    public void loggin(String name, String photo, String token){
        try {
            JSONObject manJson = new JSONObject();
            manJson.put("name", name);
            manJson.put("photo", photo);
            manJson.put("token", token);
            super.postJSON(manJson,METHOD_LOGIN);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }



    //CONSTANTS
    public final static String METHOD_LOGIN="Client.LOGGIN";
}
