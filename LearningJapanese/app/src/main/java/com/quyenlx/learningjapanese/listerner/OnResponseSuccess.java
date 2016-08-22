package com.quyenlx.learningjapanese.listerner;

/**
 * Created by LxQuyen on 21/08/2016.
 */
public interface OnResponseSuccess<T, TE> {
    void onResponseSuccess(String tag, T response, TE extraData);

    void onResponseError(String tag, String message);

}