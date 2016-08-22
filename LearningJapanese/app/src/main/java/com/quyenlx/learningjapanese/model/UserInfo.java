package com.quyenlx.learningjapanese.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by quyenlx on 8/20/2016.
 */
public class UserInfo implements Serializable {
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("email")
    private String email;
    @SerializedName("address")
    private String address;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("quote")
    private String quote;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("city")
    private String city;
    @SerializedName("gender")
    private String gender;
    @SerializedName("password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        if (TextUtils.isEmpty(gender))
            return -1;
        else
            return Integer.parseInt(gender);
    }

    public void setGender(int gender) {
        this.gender = gender + "";
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private boolean isAvatarLocal;

    public boolean getIsAvatarLocal() {
        return isAvatarLocal;
    }

    public void setIsAvatarLocal(boolean avatarLocal) {
        isAvatarLocal = avatarLocal;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
