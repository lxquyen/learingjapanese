package com.quyenlx.learningjapanese.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.quyenlx.learningjapanese.R;
import com.quyenlx.learningjapanese.Util.Constants;
import com.quyenlx.learningjapanese.Util.DeviceSupport;
import com.quyenlx.learningjapanese.Util.FilesUtils;
import com.quyenlx.learningjapanese.Util.RequestPermissionsUtils;
import com.quyenlx.learningjapanese.Util.SlideDateTimePicker;
import com.quyenlx.learningjapanese.Util.Utils;
import com.quyenlx.learningjapanese.Util.ViewUtils;
import com.quyenlx.learningjapanese.activity.BaseAbstractActivity;
import com.quyenlx.learningjapanese.dialog.DialogChooseCities;
import com.quyenlx.learningjapanese.dialog.DialogChooseGender;
import com.quyenlx.learningjapanese.fragment.presenter.RegisterPresenter;
import com.quyenlx.learningjapanese.listerner.SlideDateTimeListener;
import com.quyenlx.learningjapanese.model.UserInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by quyenlx on 8/20/2016.
 */
public class RegisterFragment extends BaseFragment implements View.OnClickListener, ActionSheet.ActionSheetListener {
    private static final String TAG = RegisterFragment.class.getSimpleName();
    private static final int DIALOG_FRAGMENT = 3000;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 2000;
    private static final int SELECT_FILE = 1000;
    private static final int DIALOG_FRAGMENT_CITY = 4000;
    @Bind(R.id.btnLeft)
    ImageButton btnBack;
    @Bind(R.id.txt_toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.btnRight)
    ImageButton btnRight;
    @Bind(R.id.toolbar_menu)
    Toolbar toolbar;
    @Bind(R.id.fragment_register_capture)
    CircleImageView img_capture;
    @Bind(R.id.fragment_register_last_name)
    EditText ed_last_name;
    @Bind(R.id.fragment_edit_profile_ed_k_last_name_require)
    TextView ed_last_name_require;
    @Bind(R.id.fragment_register_first_nane)
    EditText ed_first_name;
    @Bind(R.id.fragment_edit_profile_ed_last_name_require)
    TextView ed_first_name_require;
    @Bind(R.id.fragment_register_email)
    EditText ed_email;
    @Bind(R.id.fragment_edit_profile_ed_k_first_name_require)
    TextView ed_ed_email_require;
    @Bind(R.id.fragment_register_birth_day)
    TextView ed_birthday;
    @Bind(R.id.fragment_edit_profile_ed_birthday_require)
    TextView ed_birthday_require;
    @Bind(R.id.fragment_edit_profile_ed_birthday_image)
    ImageView birthday_image;
    @Bind(R.id.fragment_register_gender)
    TextView ed_gender;
    @Bind(R.id.fragment_edit_profile_ed_gender_require)
    TextView ed_gender_require;
    @Bind(R.id.fragment_edit_profile_ed_gender_image)
    ImageView gender_image;
    @Bind(R.id.fragment_register_city)
    TextView ed_city;
    @Bind(R.id.fragment_edit_profile_ed_city_image)
    ImageView city_image;
    @Bind(R.id.fragment_register_address)
    EditText ed_address;
    @Bind(R.id.fragment_edit_profile_txt_confirm)
    TextView txt_confirm;
    @Bind(R.id.ll_root)
    LinearLayout ll_root;

    private boolean buttonClick;
    private Calendar newDate;
    private Date dtBirthday;
    private boolean mIsAvatarLocal = false;
    private int mGender = -1;
    private String mCity = "";
    private String mAvatar;
    private RegisterPresenter presenter;

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RegisterPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        if (Build.VERSION.SDK_INT >= 23) {
            RequestPermissionsUtils.showCheckPermissionCamera(mContext, ll_root, Constants.MY_PERMISSIONS_REQUEST_CAMERA);
            RequestPermissionsUtils.showCheckPermissionWriteExternalStorage(mContext, ll_root, Constants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            RequestPermissionsUtils.showCheckPermissionReadExternalStorage(mContext, ll_root, Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
        initToolbar();
        initViews();
        return view;
    }

    private void initViews() {
        txt_confirm.setOnClickListener(this);
        ed_birthday.setOnClickListener(this);
        ed_birthday.setInputType(InputType.TYPE_NULL);
        ed_birthday.requestFocus();
        img_capture.setOnClickListener(this);

        ed_gender.setOnClickListener(this);
        ed_gender.setInputType(InputType.TYPE_NULL);
        ed_gender.requestFocus();
        ed_city.setOnClickListener(this);
        ed_city.setInputType(InputType.TYPE_NULL);
        ed_city.requestFocus();
        ed_email.setText("");
        ed_email.setHint("");
        ed_first_name.setText("");
        ed_first_name.setHint("");
        ed_last_name.setText("");
        ed_last_name.setHint("");
        ed_birthday.setText("");
        ed_birthday.setHint("");
        ed_gender.setText("");
        ed_city.setText("");
        ed_address.setText("");
        ed_address.setHint("");
        ViewUtils.setupUI(ll_root, getActivity());
        birthday_image.setVisibility(View.VISIBLE);
        gender_image.setVisibility(View.VISIBLE);
        city_image.setVisibility(View.VISIBLE);
    }

    private void initToolbar() {
        btnRight.setVisibility(View.GONE);
        toolbar_title.setText("Thông tin tài khoản");
        toolbar_title.setTextColor(getResources().getColor(R.color.title_tool_bar));
        toolbar.setBackgroundResource(R.drawable.backround_toolbar_second);
        btnBack.setBackgroundResource(R.drawable.button_delete_black);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnBack) {
            hideSoftKeyBoard();
            ((BaseAbstractActivity) mContext).removeFragment();
        } else if (view == txt_confirm) {
            hideSoftKeyBoard();
            if (validateData()) {
                showProgress("");

                if (setDataNewUser().getIsAvatarLocal())
                    presenter.requestData(TAG, setDataNewUser());
                else
                    presenter.requestData(setDataNewUser());
            }
        } else if (view == ed_birthday) {
            if (!buttonClick) {
                buttonClick = true;
                ed_birthday_require.setVisibility(View.GONE);
                ed_birthday.setBackgroundResource(R.color.transparent);
                new SlideDateTimePicker.Builder(getChildFragmentManager())
                        .setListener(listener)
                        .setInitialDate(dtBirthday)
                        .build()
                        .show();
            }
        } else if (view == img_capture) {
            if (!buttonClick) {
                buttonClick = true;
                showUploadDialog();
            }
        } else if (view == ed_gender) {
            if (!buttonClick) {
                buttonClick = true;
                ed_gender_require.setVisibility(View.GONE);
                ed_gender.setBackgroundResource(R.color.transparent);
                showDialogGender(mGender);
            }
        } else if (view == ed_city) {
            if (!buttonClick) {
                buttonClick = true;
                showDialogCities(mCity);
            }
        }
    }

    private void showDialogGender(int gender) {
        DialogChooseGender dialogFrag = DialogChooseGender.newInstance(gender);
        dialogFrag.setTargetFragment(this, DIALOG_FRAGMENT);
        dialogFrag.show(getChildFragmentManager(), DialogChooseGender.class.getSimpleName());
    }

    private void showDialogCities(String city) {
        DialogChooseCities dialogFrag = DialogChooseCities.newInstance(city);
        dialogFrag.setTargetFragment(this, DIALOG_FRAGMENT_CITY);
        dialogFrag.show(getChildFragmentManager(), DialogChooseCities.class.getSimpleName());
       /* DialogUtils.showDialogCity(mContext, city, new OnChooseDataCallBack() {
            @Override
            public void onChooseCallBack(Intent intent, int flag) {
                buttonClick = false;
                if (flag == Activity.RESULT_OK) {
                    Bundle bundle = intent.getExtras();
                    mCity = bundle.getString(Constants.KEY_CITY);
                    ed_city.setText(mCity);
                }
            }
        });*/
    }

    private void showUploadDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialog_choose_picture);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                buttonClick = false;
            }
        });
        dialog.show();

        LinearLayout choose_camera_image = (LinearLayout) dialog.findViewById(R.id.choose_camera_image);
        LinearLayout choose_library = (LinearLayout) dialog.findViewById(R.id.choose_library);

        choose_camera_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DeviceSupport.isDeviceSupportCamera(mContext))
                    capturePicture();
                dialog.dismiss();
                buttonClick = false;
            }
        });


        choose_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageFromGallery();
                dialog.dismiss();
                buttonClick = false;
            }
        });


    }

    private UserInfo setDataNewUser() {
        UserInfo info = new UserInfo();
        info.setFirst_name(ed_first_name.getText().toString().trim());
        info.setLast_name(ed_last_name.getText().toString().trim());
        if (newDate != null) {
            int month = (newDate.get(Calendar.MONTH) + 1);
            String monthString = "";
            if (month < 10) {
                monthString = "0" + month;
            } else {
                monthString = month + "";
            }
            int day = (newDate.get(Calendar.DAY_OF_MONTH));
            String dayString = "";
            if (day < 10) {
                dayString = "0" + day;
            } else {
                dayString = day + "";
            }
            info.setBirthday(newDate.get(Calendar.YEAR) + "-" + monthString + "-" + dayString);
        }
        info.setIsAvatarLocal(mIsAvatarLocal);
        info.setEmail(ed_email.getText().toString().trim());
        info.setAddress(ed_address.getText().toString().trim());
        if (info.getIsAvatarLocal()) {
            info.setAvatar(mAvatar);
        }
        info.setCity(ed_city.getText().toString().trim());
        info.setGender(mGender);
        return info;
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
        if (index == 0) {
            loadImageFromGallery();
        } else if (index == 1) {
            if (DeviceSupport.isDeviceSupportCamera(mContext))
                capturePicture();

        }
    }

    private void capturePicture() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    private void loadImageFromGallery() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                SELECT_FILE);
    }

    private boolean validateData() {
        boolean check = true;

        if (ed_email.getText().toString().trim().isEmpty()) {
            ed_email.setBackgroundResource(R.drawable.border_set_layout_require);
            ed_ed_email_require.setVisibility(View.VISIBLE);
            ed_ed_email_require.setText(getResources().getString(R.string.empty));
            check = false;
        } else {
            ed_ed_email_require.setVisibility(View.GONE);
            ed_email.setBackgroundResource(R.color.transparent);
        }

        if (ed_first_name.getText().toString().trim().isEmpty()) {
            ed_first_name.setBackgroundResource(R.drawable.border_set_layout_require);
            ed_first_name_require.setVisibility(View.VISIBLE);
            ed_first_name_require.setText(getResources().getString(R.string.empty));
            check = false;
        } else {
            ed_first_name_require.setVisibility(View.GONE);
            ed_first_name.setBackgroundResource(R.color.transparent);
        }
        if (ed_last_name.getText().toString().trim().isEmpty()) {
            ed_last_name_require.setVisibility(View.VISIBLE);
            ed_last_name_require.setText(getResources().getString(R.string.empty));
            ed_last_name.setBackgroundResource(R.drawable.border_set_layout_require);
            check = false;
        } else {
            ed_last_name_require.setVisibility(View.GONE);
            ed_last_name.setBackgroundResource(R.color.transparent);
        }
        if (ed_birthday.getText().toString().trim().isEmpty()) {
            ed_birthday.setBackgroundResource(R.drawable.border_set_layout_require);
            ed_birthday_require.setVisibility(View.VISIBLE);
            ed_birthday_require.setText(getResources().getString(R.string.error_select));
            check = false;
        } else {
            ed_birthday_require.setVisibility(View.GONE);
            ed_birthday.setBackgroundResource(R.color.transparent);
        }
        if (ed_gender.getText().toString().trim().isEmpty()) {
            ed_gender_require.setVisibility(View.VISIBLE);
            ed_gender_require.setText(getResources().getString(R.string.error_select_gender));
            ed_gender.setBackgroundResource(R.drawable.border_set_layout_require);
            check = false;
        } else {
            ed_gender_require.setVisibility(View.GONE);
            ed_gender.setBackgroundResource(R.color.transparent);
        }
        return check;
    }

    private void hideSoftKeyBoard() {
        Utils.hideSoftKeyBoard(mContext, ed_address);
        Utils.hideSoftKeyBoard(mContext, ed_first_name);
        Utils.hideSoftKeyBoard(mContext, ed_email);
        Utils.hideSoftKeyBoard(mContext, ed_last_name);
    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            int intMonth = Integer.parseInt((String) android.text.format.DateFormat.format("MM", date)); //06
            int year = Integer.parseInt((String) android.text.format.DateFormat.format("yyyy", date)); //2013
            int day = Integer.parseInt((String) android.text.format.DateFormat.format("dd", date)); //20
            newDate = Calendar.getInstance();
            newDate.set(year, intMonth - 1, day);
            ed_birthday.setText(day + "/" + intMonth + "/" + year);
            dtBirthday = date;
            buttonClick = false;
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
            Toast.makeText(mContext,
                    "Canceled", Toast.LENGTH_SHORT).show();
            buttonClick = false;
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        buttonClick = false;
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                mAvatar = destination.getPath();
                mIsAvatarLocal = true;

                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mPicasso
                        .with(mContext)
                        .load(Uri.fromFile(destination))
                        .placeholder(R.drawable.capture)
                        .into(img_capture);
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();

                String selectedImagePath = FilesUtils.getPath(mContext, selectedImageUri);
                Log.i(TAG, "=============>selectedImagePath: " + selectedImagePath);
                mAvatar = selectedImagePath;
                mIsAvatarLocal = true;
                mPicasso
                        .with(mContext)
                        .load(Uri.fromFile(new File(selectedImagePath)))
                        .placeholder(R.drawable.capture)
                        .into(img_capture);
            } else if (requestCode == DIALOG_FRAGMENT) {
                Bundle bundle = data.getExtras();
                mGender = bundle.getInt(Constants.KEY_GENDER);

                if (mGender == 0) {
                    ed_gender.setText(getResources().getText(R.string.male));
                } else if (mGender == 1) {
                    ed_gender.setText(getResources().getText(R.string.female));
                } else {
                    ed_gender.setText(getResources().getText(R.string.unknown));
                }
            } else if (requestCode == DIALOG_FRAGMENT_CITY) {
                Bundle bundle = data.getExtras();
                mCity = bundle.getString(Constants.KEY_CITY);
                ed_city.setText(mCity);

            }
        }
    }

    public void responseSuccess() {
        dismissDialog();
        ((BaseAbstractActivity) mContext).addFragment(ConfirmUserInforRegisterFragment.newInstance(setDataNewUser()));
    }

    public void responseError(String message) {
        dismissDialog();
        Utils.showToast(mContext, message);
    }
}
