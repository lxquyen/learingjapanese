package com.quyenlx.learningjapanese.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.quyenlx.learningjapanese.R;
import com.quyenlx.learningjapanese.activity.JPApplication;
import com.squareup.picasso.Picasso;

/**
 * Created by quyenlx on 8/20/2016.
 */
public class BaseFragment extends Fragment {
    protected Context mContext;
    protected Picasso mPicasso;
    private ProgressDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JPApplication app = (JPApplication) getActivity().getApplication();
        mPicasso = app.getPicasso();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK != resultCode) {
            getActivity().finish();
            return;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void dismissDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    public void showProgress(String mess) {
        dialog = new ProgressDialog(mContext, R.style.MyTheme);
        dialog.setCancelable(false);
        dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        dialog.show();
    }
}
