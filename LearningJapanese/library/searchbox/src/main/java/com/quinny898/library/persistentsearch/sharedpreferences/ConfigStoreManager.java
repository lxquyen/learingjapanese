package com.quinny898.library.persistentsearch.sharedpreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Quanpv on 22/5/2016.
 */
public class ConfigStoreManager<T> {
    Context activity;
    private SharedPreferences pref;
    private Editor editor;
    private boolean autoCommit = true;

    public ConfigStoreManager(Context activity) {
        this.autoCommit = true;
        this.activity = activity;
        pref = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = pref.edit();
    }

    public ConfigStoreManager(Activity activity, boolean autoCommit) {
        this.autoCommit = autoCommit;
        this.activity = activity;
        pref = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = pref.edit();
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        if (autoCommit) {
            commit();
        }
    }

    public String getString(String key, String defaultValue) {
        return pref.getString(key, defaultValue);
    }

    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    public void removeAll() {
        editor.clear();
        editor.commit();
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        if (autoCommit) {
            commit();
        }
    }

    public int getInt(String key, int defaultValue) {
        return pref.getInt(key, defaultValue);
    }

    public void putLong(String key, long value) {
        editor.putLong(key, value);
        if (autoCommit) {
            commit();
        }
    }

    public long getLong(String key, long defaultValue) {
        return pref.getLong(key, defaultValue);
    }

    public void commit() {
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return pref.getBoolean(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        if (autoCommit) {
            commit();
        }
    }

    public void putArrayListInteger(String key, Set<String> list) {
        editor.putStringSet(key, list);
        if (autoCommit) {
            commit();
        }
    }

    public Set<String> getArrayListInteger(String key, Set<String> defaultValue) {
        return pref.getStringSet(key, defaultValue);
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public void putArrayList(String key, ArrayList<T> searchables) {
        try {
            editor.putString(key, ObjectSerializer.serialize(searchables));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public ArrayList<T> getArrayList(String key) {
        ArrayList arrayList = new ArrayList();
        try {
            arrayList = (ArrayList) ObjectSerializer.deserialize(pref.getString(key, ObjectSerializer.serialize(new ArrayList())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

}
