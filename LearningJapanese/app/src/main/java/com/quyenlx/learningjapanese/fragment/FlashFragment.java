package com.quyenlx.learningjapanese.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quyenlx.learningjapanese.R;
import com.quyenlx.learningjapanese.Util.Utils;
import com.quyenlx.learningjapanese.activity.BaseAbstractActivity;
import com.quyenlx.learningjapanese.customview.FButton;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by quyenlx on 8/20/2016.
 */
public class FlashFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.fragment_flash_btn_login)
    FButton btnRegister;
    @Bind(R.id.fragment_flash_btn_change_data)
    FButton btnLogin;

    public static FlashFragment newInstance() {
        FlashFragment fragment = new FlashFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flash, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Utils.setColorStatusBar(getActivity(), R.color.colorPrimaryDarkLogin);
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnLogin) {
            ((BaseAbstractActivity) mContext).addFragment(LoginFragment.newInstance());
        } else if (view == btnRegister) {
            ((BaseAbstractActivity) mContext).addFragment(RegisterFragment.newInstance());
        }
    }
}
