package com.quyenlx.learningjapanese.Util.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.quyenlx.learningjapanese.model.UserInfo;

import java.io.IOException;

/**
 * Created by quyenlx on 8/20/2016.
 */
public class ConfigManager {
    private Context mContext;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private boolean autoCommit = true;

    public ConfigManager(Context mContext) {
        this.autoCommit = true;
        this.mContext = mContext;
        pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        editor = pref.edit();
    }

    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    public void removeAll() {
        editor.clear();
        editor.commit();
    }

    public void commit() {
        editor.commit();
    }

    public void putUserInfoShared(String key, UserInfo obj) {
        try {
            editor.putString(key, ObjectSerializer.serialize(obj));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public UserInfo getUserInfoShared(String key) {
        UserInfo user = null;
        try {
            user = (UserInfo) ObjectSerializer.deserialize(pref.getString(key, ObjectSerializer.serialize(new UserInfo())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}

