package com.quyenlx.learningjapanese.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.quyenlx.learningjapanese.R;
import com.quyenlx.learningjapanese.fragment.HomeFragment;

import butterknife.Bind;

/**
 * Created by LxQuyen on 21/08/2016.
 */
public class HomeActivity extends AppCompatActivity {
    @Bind(R.id.txt_toolbar_title)
    TextView mTitle;
    @Bind(R.id.toolbar_menu)
    Toolbar toolbar;
    @Bind(R.id.btnLeft)
    ImageButton btnLeft;
    @Bind(R.id.btnRight)
    ImageButton btnRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar.setBackgroundResource(R.color.colorPrimary);
        FragmentManager fmgr = getSupportFragmentManager();
        FragmentTransaction ft = fmgr.beginTransaction();
        ft.replace(R.id.activity_home_patient_content, HomeFragment.newInstance());
        ft.commit();
    }

    public void setController(String title, boolean hideRight, boolean b1) {
//        mTitle.setText(title);
        mTitle.setTextColor(Color.WHITE);
        toolbar.setBackgroundResource(R.color.colorPrimary);
        btnLeft.setVisibility(View.GONE);
        btnRight.setVisibility(View.GONE);
    }
}
