package com.quyenlx.learningjapanese.Util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.quyenlx.learningjapanese.R;
import com.quyenlx.learningjapanese.Util.http.HttpUtil;
import com.quyenlx.learningjapanese.model.CityInfo;
import com.quyenlx.learningjapanese.model.GenderInfo;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quyenlx on 8/20/2016.
 */
public class Utils {
    public static void setColorStatusBar(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(color));
        }
    }

    public static void showToast(Context mContext, String here) {
        Toast.makeText(mContext, here, Toast.LENGTH_SHORT).show();
    }

    public static void hideSoftKeyBoard(Context mContext, View view) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText())
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText() && activity.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

    }

    public static ArrayList<GenderInfo> getlistGender(Context context) {
        ArrayList<GenderInfo> list = new ArrayList<>();
        GenderInfo genderInfo = new GenderInfo();
        genderInfo.setSelected(false);
        genderInfo.setId(0);
        genderInfo.setTitle(context.getResources().getString(R.string.male));
        list.add(genderInfo);

        genderInfo = new GenderInfo();
        genderInfo.setSelected(false);
        genderInfo.setId(1);
        genderInfo.setTitle(context.getResources().getString(R.string.female));
        list.add(genderInfo);

        genderInfo = new GenderInfo();
        genderInfo.setSelected(false);
        genderInfo.setId(2);
        genderInfo.setTitle(context.getResources().getString(R.string.unknown));
        list.add(genderInfo);

        return list;
    }

    public static ArrayList<CityInfo> getlistCities(Context context) {
        String json = loadJSONFromAsset(context, "cities.json");
        if (json != null) {
            ArrayList<CityInfo> listCities = new ArrayList<>();
            Type type = new TypeToken<List<String>>() {
            }.getType();
            ArrayList<String> list = HttpUtil.getInstance().initGson().fromJson(json, type);
            for (String item : list) {
                CityInfo info = new CityInfo();
                info.setTitle(item);
                info.setSelected(false);
                listCities.add(info);
            }
            return listCities;
        }
        return new ArrayList<CityInfo>();
    }

    public static String loadJSONFromAsset(Context context, String namefile) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(namefile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static String getName(String last_name, String first_name) {
        return last_name + " " + first_name;
    }


    public static String getGender(int gender) {
        switch (gender) {
            case 0:
                return "Nữ";
            case 1:
                return "Nam";
            case 2:
                return "Không xác định";
            default:
                return "Không xác định";
        }
    }
}
