package com.quyenlx.learningjapanese.fragment.presenter;

import com.quyenlx.learningjapanese.Util.Utils;
import com.quyenlx.learningjapanese.Util.http.HttpUtil;
import com.quyenlx.learningjapanese.fragment.LoginFragment;
import com.quyenlx.learningjapanese.listerner.OnResponseSuccess;
import com.quyenlx.learningjapanese.model.UserInfo;

/**
 * Created by LxQuyen on 21/08/2016.
 */
public class LoginPresenter {
    private static final String TAG = LoginPresenter.class.getSimpleName();
    private LoginFragment view;

    public LoginPresenter(LoginFragment view) {
        this.view = view;
    }

    public void requestData(String user_name, String password, String birthday) {
        HttpUtil.getInstance().getLogin(TAG, user_name, password, birthday, new OnResponseSuccess<UserInfo, String>() {
            @Override
            public void onResponseSuccess(String tag, UserInfo response, String extraData) {
                view.response(response);
            }

            @Override
            public void onResponseError(String tag, String message) {
                view.response(message);
            }
        });
    }
}
