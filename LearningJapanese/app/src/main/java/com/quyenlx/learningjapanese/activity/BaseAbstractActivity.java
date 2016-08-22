package com.quyenlx.learningjapanese.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.quyenlx.learningjapanese.R;
import com.quyenlx.learningjapanese.Util.Constants;
import com.quyenlx.learningjapanese.Util.sharedpreferences.ConfigManager;
import com.quyenlx.learningjapanese.model.UserInfo;

/**
 * Created by quyenlx on 8/20/2016.
 */
public abstract class BaseAbstractActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        replaceFragmentContent(initFragmentContent());
    }

    protected void replaceFragmentContent(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fmgr = getSupportFragmentManager();
            FragmentTransaction ft = fmgr.beginTransaction();
            ft.replace(R.id.base_activity_layout_content, fragment);
            ft.commit();
        }
    }

    protected void addFragmentContentClearAll(Fragment fragment) {
        FragmentManager fmgr = getSupportFragmentManager();
        FragmentTransaction ft = fmgr.beginTransaction();
        while (fmgr.getBackStackEntryCount() > 0)
            fmgr.popBackStackImmediate();
        ft.add(R.id.base_activity_layout_content, fragment);
        ft.addToBackStack(fragment.getClass().getSimpleName());
        ft.commit();
    }

    protected void addFragmentContentNoClear(Fragment fragment) {
        FragmentManager fmgr = getSupportFragmentManager();
        FragmentTransaction ft = fmgr.beginTransaction();
        ft.add(R.id.base_activity_layout_content, fragment);
        ft.addToBackStack(fragment.getClass().getSimpleName());
        ft.commit();
    }


    public void removeFragment() {
        FragmentManager fmgr = getSupportFragmentManager();
        FragmentTransaction ft = fmgr.beginTransaction();
        if (fmgr.getBackStackEntryCount() > 0)
            fmgr.popBackStackImmediate();
    }

    public void removeFragmentAndAddFragment(Fragment frm) {
        FragmentManager fmgr = getSupportFragmentManager();
        FragmentTransaction ft = fmgr.beginTransaction();
        if (fmgr.getBackStackEntryCount() > 0)
            fmgr.popBackStackImmediate();
        addFragment(frm);
    }

    public void addFragment(Fragment frm) {
        addFragmentContentNoClear(frm);
    }

    protected abstract Fragment initFragmentContent();

    public void loadHome(UserInfo info) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constants.KEY_USER_INFO, info);
        startActivity(intent);
        finish();
    }
}
