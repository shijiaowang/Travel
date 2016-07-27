package com.example.administrator.travel.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.travel.event.VolleyStringEvent;
import com.example.administrator.travel.global.IVariable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class VolleyUtils {
    public static void getStringRequest(String url,RequestQueue requestQueue,int type){
        final VolleyStringEvent volleyGetEvent=new VolleyStringEvent();
        volleyGetEvent.setType(type);
       StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               success(response, volleyGetEvent);
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               error(error, volleyGetEvent);
           }
       });
       requestQueue.add(stringRequest);
    }
    public static void LoginStringRequest(RequestQueue requestQueue,final String key, final String username, final String password){
        final VolleyStringEvent volleyGetEvent=new VolleyStringEvent();
        volleyGetEvent.setType(IVariable.TYPE_POST_LOGIN);
        String url=IVariable.LOGIN_URL;

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                success(response, volleyGetEvent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error(error, volleyGetEvent);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> postMap=new HashMap<>();
                postMap.put(IVariable.KEY,key);
                postMap.put(IVariable.USERNAME,username);
                postMap.put(IVariable.PASSWORD,password);
                return postMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private static void success(String response, VolleyStringEvent volleyGetEvent) {
        int code=-5;
        String message="未知错误。";
        try {
            JSONObject jsonObject=new JSONObject(response);
            code = jsonObject.getInt("code");
            message=jsonObject.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyGetEvent.setCode(code);
        volleyGetEvent.setMessage(message);
        volleyGetEvent.setIsSuccess(true);
        volleyGetEvent.setError(null);
        volleyGetEvent.setResult(response);
        EventBus.getDefault().post(volleyGetEvent);
    }

    private static void error(VolleyError error, VolleyStringEvent volleyGetEvent) {
        volleyGetEvent.setIsSuccess(false);
        volleyGetEvent.setError(error);
        volleyGetEvent.setResult("");
        EventBus.getDefault().post(volleyGetEvent);
    }

}

