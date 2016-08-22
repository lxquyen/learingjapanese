package com.quyenlx.learningjapanese.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.quyenlx.learningjapanese.fragment.FlashFragment;
import com.quyenlx.learningjapanese.model.UserInfo;

import butterknife.ButterKnife;

/**
 * Created by quyenlx on 8/20/2016.
 */
public class FlashActivity extends BaseAbstractActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserInfo userCurrent = JPApplication.getInstance().getUserCurrent();
        if (userCurrent != null) {
            loadHome(userCurrent);
        }
    }

    @Override
    protected Fragment initFragmentContent() {
        return FlashFragment.newInstance();
    }
}
