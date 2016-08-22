package com.quyenlx.learningjapanese.Util.http;

import android.content.Context;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.quyenlx.learningjapanese.Util.Constants;
import com.quyenlx.learningjapanese.Util.sharedpreferences.ConfigManager;
import com.quyenlx.learningjapanese.activity.JPApplication;
import com.quyenlx.learningjapanese.listerner.OnResponseSuccess;
import com.quyenlx.learningjapanese.model.BaseResponse;
import com.quyenlx.learningjapanese.model.UserInfo;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LxQuyen on 20/08/2016.
 */
public class HttpUtil {
    private static HttpUtil mInstance;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
    private Retrofit retrofit;
    private APIService service;

    private ConfigManager configManager;

    public static HttpUtil getInstance() {
        if (mInstance == null)
            mInstance = new HttpUtil();
        return mInstance;
    }

    public static void releaseInstanceAPI() {
        if (mInstance != null) {
            mInstance = null;
        }
    }

    public Gson initGson() {
        return new GsonBuilder()
                .disableHtmlEscaping()
                .registerTypeAdapter(Boolean.class, booleanAsIntAdapter)
                .registerTypeAdapter(boolean.class, booleanAsIntAdapter)
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }

    private TypeAdapter<Boolean> booleanAsIntAdapter = new TypeAdapter<Boolean>() {
        @Override
        public void write(JsonWriter out, Boolean value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value);
            }
        }

        @Override
        public Boolean read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case BOOLEAN:
                    return in.nextBoolean();
                case NULL:
                    in.nextNull();
                    return null;
                case NUMBER:
                    return in.nextInt() != 0;
                case STRING:
                    return Boolean.parseBoolean(in.nextString());
                default:
                    throw new IllegalStateException("Expected BOOLEAN or NUMBER but was " + peek);
            }
        }
    };

    public HttpUtil() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor interceptorHeader = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
//                        .header("User-Agent", "Chat")
//                        .header("Accept", "application/vnd.chat.vn.v1.full+json")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        };
        OkHttpClient httpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).addInterceptor(logging).addInterceptor(interceptorHeader).build();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        retrofit = new Retrofit.Builder().client(httpClient).baseUrl(Constants.BASE_SERVER_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        service = retrofit.create(APIService.class);
    }

    public void getLogin(final String TAG, String user_name, String password, String birthday, final OnResponseSuccess<UserInfo, String> onResponseSuccess) {
        Call<UserInfo> callDetail = service.getLogin(user_name, password, birthday);
        callDetail.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (onResponseSuccess != null && response.body() != null) {
                    onResponseSuccess.onResponseSuccess(TAG, response.body(), null);
                } else {
                    if (onResponseSuccess != null) {
                        onResponseSuccess.onResponseError(TAG, response.raw().message());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                if (onResponseSuccess != null) {
                    onResponseSuccess.onResponseError(TAG, t.getMessage());
                }
            }

        });
    }

    public void registerNoAvatar(final String TAG, UserInfo info, final OnResponseSuccess<BaseResponse, String> onResponseSuccess) {
        Call<BaseResponse> call = service.register(info.getFirst_name(), info.getLast_name(), info.getBirthday(), info.getEmail(), info.getGender() + "", info.getCity(), info.getAddress());
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (onResponseSuccess != null && response.body() != null) {
                    onResponseSuccess.onResponseSuccess(TAG, response.body(), null);
                } else {
                    if (onResponseSuccess != null) {
                        onResponseSuccess.onResponseError(TAG, response.raw().message());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                if (onResponseSuccess != null) {
                    onResponseSuccess.onResponseError(TAG, t.getMessage());
                }
            }
        });
    }

    public void register(final String TAG, UserInfo info, final OnResponseSuccess<String, String> onResponseSuccess) {
        try {
            File sourceFile = null;
            if (info.getIsAvatarLocal())
                sourceFile = new File(info.getAvatar());
            final long totalSize = sourceFile.length();
            RequestBody fileReqBody = new OkHttpMultiPartEntity(sourceFile, "multipart/form-data", new OkHttpMultiPartEntity.ProgressListener() {
                @Override
                public void transferred(long num) {
                    float progress = (num / (float) totalSize) * 100;
                    Log.i(TAG, "============>progress:" + (int) progress);
                }
            });
            MultipartBody.Part filePart =
                    MultipartBody.Part.createFormData("avatar", sourceFile.getName(), fileReqBody);
            Call<ResponseBody> call = service.register(info.getFirst_name(), info.getLast_name(), info.getBirthday(), info.getEmail(), info.getGender() + "", info.getCity(), info.getAddress(), filePart);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    onResponseSuccess.onResponseSuccess(TAG, "success", null);

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    onResponseSuccess.onResponseError(TAG, t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (onResponseSuccess != null) {
                onResponseSuccess.onResponseError(TAG, e.getMessage());
            }
        }
    }
}
