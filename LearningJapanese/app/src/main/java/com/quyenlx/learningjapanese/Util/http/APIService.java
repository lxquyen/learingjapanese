package com.quyenlx.learningjapanese.Util.http;

import com.quyenlx.learningjapanese.model.BaseResponse;
import com.quyenlx.learningjapanese.model.UserInfo;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by LxQuyen on 20/08/2016.
 */
public interface APIService {
    String API_LOG_IN = "api/login";
    String API_REGISTER = "api/register";
    String API_REGISTER_NO_AVATAR = "api/register_no_avatar";

    @FormUrlEncoded
    @POST(API_LOG_IN)
    Call<UserInfo> getLogin(@Field("user_name") String user_name, @Field("password") String password, @Field("birthday") String birthday);

    @FormUrlEncoded
    @POST(API_REGISTER_NO_AVATAR)
    Call<BaseResponse> register(@Field("first_name") String first_name,
                                @Field("last_name") String last_name,
                                @Field("birthday") String birthday,
                                @Field("email") String email,
                                @Field("gender") String gender,
                                @Field("city") String city,
                                @Field("address") String address);

    @Multipart
    @POST(API_REGISTER)
    Call<ResponseBody> register(@Part("first_name") String first_name,
                                @Part("last_name") String last_name,
                                @Part("birthday") String birthday,
                                @Part("email") String email,
                                @Part("gender") String gender,
                                @Part("city") String city,
                                @Part("address") String address,
                                @Part MultipartBody.Part avatar);

}
