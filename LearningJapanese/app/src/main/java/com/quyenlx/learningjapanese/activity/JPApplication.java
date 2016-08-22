package com.quyenlx.learningjapanese.activity;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.quyenlx.learningjapanese.Util.Constants;
import com.quyenlx.learningjapanese.Util.sharedpreferences.ConfigManager;
import com.quyenlx.learningjapanese.model.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;

/**
 * Created by quyenlx on 8/20/2016.
 */
public class JPApplication extends MultiDexApplication {
    private static JPApplication mInstance;
    private Context context;
    private Picasso mPicasso;

    public static synchronized JPApplication getInstance() {
        return mInstance;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


        context = getApplicationContext();

        getIntancePicasso(context);
        MultiDex.install(this);
    }

    public Picasso getIntancePicasso(Context context) {
        final OkHttpClient client = new OkHttpClient.Builder()
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .build();

        mPicasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(client))
                .build();
        return mPicasso;
    }

    public Picasso getPicasso() {
        return mPicasso;
    }

    public UserInfo getUserCurrent() {
        ConfigManager manager = new ConfigManager(context);
        UserInfo infoShared = manager.getUserInfoShared(Constants.KEY_CURRENT_USER);
        return infoShared;
    }
}

