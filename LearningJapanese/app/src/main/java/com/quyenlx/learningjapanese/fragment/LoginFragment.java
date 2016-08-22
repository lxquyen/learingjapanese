package com.quyenlx.learningjapanese.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quyenlx.learningjapanese.R;
import com.quyenlx.learningjapanese.Util.Connectivity;
import com.quyenlx.learningjapanese.Util.SlideDateTimePicker;
import com.quyenlx.learningjapanese.Util.Utils;
import com.quyenlx.learningjapanese.Util.ViewUtils;
import com.quyenlx.learningjapanese.activity.BaseAbstractActivity;
import com.quyenlx.learningjapanese.fragment.presenter.LoginPresenter;
import com.quyenlx.learningjapanese.listerner.SlideDateTimeListener;
import com.quyenlx.learningjapanese.model.UserInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by quyenlx on 8/20/2016.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = LoginFragment.class.getSimpleName();
    @Bind(R.id.btnLeft)
    ImageButton btnLeft;
    @Bind(R.id.txt_toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.btnRight)
    ImageButton btnRight;
    @Bind(R.id.toolbar_menu)
    Toolbar toolbar;
    @Bind(R.id.fragment_change_data_ed_secret_code)
    EditText editUsername;
    @Bind(R.id.fragment_change_data_ed_secret_code_require)
    TextView txt_secret_code_require;
    @Bind(R.id.fragment_change_data_pass_word)
    EditText editPassword;
    @Bind(R.id.fragment_change_data_pass_word_require)
    TextView txt_pass_word_require;
    @Bind(R.id.fragment_change_data_birthday)
    TextView ed_birthday;
    @Bind(R.id.fragment_change_data_birthday_require)
    TextView txt_birthday_require;
    @Bind(R.id.fragment_change_data_txt_continues)
    TextView txtContinues;
    @Bind(R.id.fragment_change_data_ll_primary)
    LinearLayout ll_root;

    private Calendar newDate;
    private SimpleDateFormat dateFormatter;
    private LoginPresenter presenter;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        presenter = new LoginPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        initViews();
        return view;
    }

    private void initViews() {
        txtContinues.setOnClickListener(this);
        ed_birthday.setOnClickListener(this);
        ed_birthday.setOnClickListener(this);
        ed_birthday.setInputType(InputType.TYPE_NULL);
        ed_birthday.requestFocus();
        clearData();
        setStyleView();
        ViewUtils.setupUI(ll_root, getActivity());
    }

    private void setStyleView() {
        editUsername.setTextColor(Color.BLACK);
        editPassword.setTextColor(Color.BLACK);
        ed_birthday.setTextColor(Color.BLACK);
        txtContinues.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    public void clearData() {
        ed_birthday.setText("");
        editUsername.setText("");
        editPassword.setText("");
        newDate = null;
    }

    private void initToolbar() {
        toolbar.setBackgroundResource(R.drawable.backround_toolbar_second);
        toolbarTitle.setText("Thông tin tài khoản hiện có");
        toolbarTitle.setTextColor(getResources().getColor(R.color.title_tool_bar));
        btnRight.setVisibility(View.GONE);
        btnLeft.setBackgroundResource(R.drawable.button_delete_black);
        btnLeft.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnLeft) {
            hideKeyboard();
            ((BaseAbstractActivity) mContext).removeFragment();
        } else if (view == txtContinues) {
            if (validateData()) {
                if (Connectivity.isConnected(mContext)) {
                    showProgress("");
                    String dateBirthday = dateFormatter.format(newDate.getTime());
                    presenter.requestData(editUsername.getText().toString(), editPassword.getText().toString(), dateBirthday);
                } else {
                    Utils.showToast(mContext, mContext.getResources().getString(R.string.network_error));
                }
            }
        } else if (view == ed_birthday) {
            hideKeyboard();
            Date currentDate;
            if (newDate != null) {
                currentDate = newDate.getTime();
            } else {
                currentDate = Calendar.getInstance().getTime();
            }
            new SlideDateTimePicker.Builder(getChildFragmentManager())
                    .setListener(listener)
                    .setInitialDate(currentDate)
                    //.setMinDate(minDate)
                    //.setMaxDate(maxDate)
                    //.setIs24HourTime(true)
                    //.setTheme(SlideDateTimePicker.HOLO_DARK)
                    //.setIndicatorColor(Color.parseColor("#990000"))
                    .build()
                    .show();
        }
    }

    private void hideKeyboard() {
        Utils.hideSoftKeyBoard(mContext, editUsername);
        Utils.hideSoftKeyBoard(mContext, editPassword);
    }

    private boolean validateData() {
        boolean check = true;
        if (editUsername.getText().toString().isEmpty()) {
            editUsername.setBackgroundResource(R.drawable.border_set_layout_require);
            txt_secret_code_require.setText(getResources().getString(R.string.empty));
            txt_secret_code_require.setVisibility(View.VISIBLE);
            check = false;
        } else {
            txt_secret_code_require.setVisibility(View.GONE);
            editUsername.setBackgroundResource(R.color.transparent);
        }
        if (editPassword.getText().toString().isEmpty()) {
            editPassword.setBackgroundResource(R.drawable.border_set_layout_require);
            txt_pass_word_require.setText(getResources().getString(R.string.empty));
            txt_pass_word_require.setVisibility(View.VISIBLE);
            check = false;
        } else {
            txt_pass_word_require.setVisibility(View.GONE);
            editPassword.setBackgroundResource(R.color.transparent);
        }
        if (ed_birthday.getText().toString().isEmpty()) {
            ed_birthday.setBackgroundResource(R.drawable.border_set_layout_require);
            txt_birthday_require.setText(getResources().getString(R.string.error_select));
            txt_birthday_require.setVisibility(View.VISIBLE);
            check = false;
        } else {
            txt_birthday_require.setVisibility(View.GONE);
            ed_birthday.setBackgroundResource(R.color.transparent);
        }
        return check;
    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            // Toast.makeText(mContext, dateFormatter.format(date), Toast.LENGTH_SHORT).show();
            int intMonth = Integer.parseInt((String) android.text.format.DateFormat.format("MM", date)); //06
            int year = Integer.parseInt((String) android.text.format.DateFormat.format("yyyy", date)); //2013
            int day = Integer.parseInt((String) android.text.format.DateFormat.format("dd", date)); //20
            newDate = Calendar.getInstance();
            newDate.set(year, intMonth - 1, day);
            // ed_birthday.setText(year + "年" + intMonth + "月" + day + "日");

            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.US);
            ed_birthday.setText(dateFormat.format(date));
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
            Toast.makeText(mContext,
                    "Hủy bỏ", Toast.LENGTH_SHORT).show();
        }
    };

    public void response(UserInfo response) {
        dismissDialog();
        ((BaseAbstractActivity) mContext).addFragment(ConfirmUserInforRegisterFragment.newInstance(response));
    }

    public void response(String message) {
        dismissDialog();
        Utils.showToast(mContext, message);
    }
}
