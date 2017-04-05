package com.yunspeak.travel;

import android.app.Application;
import android.support.annotation.NonNull;
import android.test.ApplicationTestCase;

import com.yunspeak.travel.download.HttpClient;
import com.yunspeak.travel.download.INetworkCallBack;
import com.yunspeak.travel.global.TravelsObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


}