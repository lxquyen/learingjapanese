package com.quyenlx.learningjapanese.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quyenlx.learningjapanese.R;
import com.quyenlx.learningjapanese.Util.Constants;
import com.quyenlx.learningjapanese.Util.Utils;
import com.quyenlx.learningjapanese.activity.BaseAbstractActivity;
import com.quyenlx.learningjapanese.model.UserInfo;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by quyenlx on 8/20/2016.
 */
public class ConfirmUserInforRegisterFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = ConfirmUserInforRegisterFragment.class.getSimpleName();
    @Bind(R.id.btnLeft)
    ImageButton btnLeft;
    @Bind(R.id.txt_toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.btnRight)
    ImageButton btnRight;
    @Bind(R.id.toolbar_menu)
    Toolbar toolbar;
    @Bind(R.id.fragment_short_info_change_data_txt_k_first_name_k_last_name)
    TextView fragmentShortInfoChangeDataTxtKFirstNameKLastName;
    @Bind(R.id.fragment_confirm_user_info_capture)
    CircleImageView mAvatar;
    @Bind(R.id.fragment_confirm_user_info_full_name)
    TextView txtFullName;
    @Bind(R.id.fragment_confirm_user_info_city)
    TextView txtCity;
    @Bind(R.id.fragment_confirm_nick_name)
    TextView fragmentConfirmNickName;
    @Bind(R.id.fragment_confirm_user_info_birthday)
    TextView txtBirthday;
    @Bind(R.id.fragment_confirm_user_info_gender)
    TextView txtGender;
    @Bind(R.id.ll_root)
    LinearLayout ll_root;

    private UserInfo info;

    public static ConfirmUserInforRegisterFragment newInstance(UserInfo info) {
        ConfirmUserInforRegisterFragment fragment = new ConfirmUserInforRegisterFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.KEY_USER_INFO, info);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            info = (UserInfo) getArguments().getSerializable(Constants.KEY_USER_INFO);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm_user_info_register, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        initViews();
        return view;
    }

    private void initViews() {
        txtFullName.setText(Utils.getName(info.getLast_name(), info.getFirst_name()).trim());
        txtCity.setText(info.getCity());
        txtBirthday.setText(info.getBirthday());
        txtGender.setText(Utils.getGender(info.getGender()));
        if (info.getIsAvatarLocal())
            mPicasso
                    .with(mContext)
                    .load(Uri.fromFile(new File(info.getAvatar())))
                    .placeholder(R.drawable.capture)
                    .into(mAvatar);
        else
            mPicasso
                    .with(mContext)
                    .load(Constants.BASE_SERVER_URL_IMAGE + info.getAvatar())
                    .placeholder(R.drawable.capture)
                    .into(mAvatar);
    }

    private void initToolbar() {
        toolbar.setBackgroundResource(R.drawable.backround_toolbar_second);
        toolbarTitle.setText("Xác nhận thông tin");
        toolbarTitle.setTextColor(getResources().getColor(R.color.title_tool_bar));
        btnLeft.setBackgroundResource(R.drawable.button_delete_black);
        btnLeft.setOnClickListener(this);
        btnRight.setBackgroundResource(R.drawable.done);
        btnRight.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnLeft) {
            ((BaseAbstractActivity) mContext).removeFragment();
        } else if (view == btnRight) {
            ((BaseAbstractActivity)mContext).loadHome(info);
            Utils.showToast(mContext, "Here");
        }
    }
}
