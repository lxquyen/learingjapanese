package com.quyenlx.learningjapanese.fragment.presenter;

import android.nfc.Tag;

import com.quyenlx.learningjapanese.Util.http.HttpUtil;
import com.quyenlx.learningjapanese.fragment.RegisterFragment;
import com.quyenlx.learningjapanese.listerner.OnResponseSuccess;
import com.quyenlx.learningjapanese.model.BaseResponse;
import com.quyenlx.learningjapanese.model.UserInfo;

/**
 * Created by LxQuyen on 21/08/2016.
 */
public class RegisterPresenter {
    private static final String TAG = RegisterPresenter.class.getSimpleName();
    private RegisterFragment view;

    public RegisterPresenter(RegisterFragment view) {
        this.view = view;
    }

    public void requestData(UserInfo userInfo) {
        HttpUtil.getInstance().registerNoAvatar(TAG, userInfo, new OnResponseSuccess<BaseResponse, String>() {
            @Override
            public void onResponseSuccess(String tag, BaseResponse response, String extraData) {
                view.responseSuccess();
            }

            @Override
            public void onResponseError(String tag, String message) {
                view.responseError(message);
            }
        });
    }

    public void requestData(String tag, UserInfo userInfo) {
        HttpUtil.getInstance().register(TAG, userInfo, new OnResponseSuccess<String, String>() {
            @Override
            public void onResponseSuccess(String tag, String response, String extraData) {
                view.responseSuccess();
            }

            @Override
            public void onResponseError(String tag, String message) {
                view.responseError(message);
            }
        });
    }
}
