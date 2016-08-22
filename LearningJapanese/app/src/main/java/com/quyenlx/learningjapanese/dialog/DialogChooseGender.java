package com.quyenlx.learningjapanese.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.quyenlx.learningjapanese.R;
import com.quyenlx.learningjapanese.Util.Constants;
import com.quyenlx.learningjapanese.Util.Utils;
import com.quyenlx.learningjapanese.adapter.GenderRecyclerViewAdapter;
import com.quyenlx.learningjapanese.model.GenderInfo;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by LxQuyen on 20/08/2016.
 */
public class DialogChooseGender extends DialogFragment {
    private static final String TAG = DialogChooseGender.class.getSimpleName();
    private Context mContext;
    Dialog dialog;
    private int gender;
    private GenderRecyclerViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
    }

    public static DialogChooseGender newInstance(int gender) {
        Log.i(TAG, "=================>gio tinh mac dinh: " + gender);
        DialogChooseGender dialogFragment = new DialogChooseGender();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.KEY_GENDER, gender);
        dialogFragment.setArguments(bundle);
        return dialogFragment;

    }

    /* @Override
     public Dialog onCreateDialog(Bundle savedInstanceState) {

         dialog = new Dialog(getActivity());
         dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
         dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                 WindowManager.LayoutParams.FLAG_FULLSCREEN);
         dialog.setContentView(R.layout.dialog_gender);
         TextView txt_ok = (TextView) dialog.findViewById(R.id.txt_ok);
         TextView txt_title = (TextView) dialog.findViewById(R.id.txt_title);
         TextView txt_cancel = (TextView) dialog.findViewById(R.id.txt_cancel);
         RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radio_gender);
         final RadioButton radio_gender_female = (RadioButton) dialog.findViewById(R.id.radio_gender_female);
         final RadioButton radio_gender_male = (RadioButton) dialog.findViewById(R.id.radio_gender_male);
         final RadioButton radio_gender_unknown = (RadioButton) dialog.findViewById(R.id.radio_gender_unknown);
         final int[] gender = {getArguments().getInt(Constants.KEY_GENDER)};
         if (gender[0] == 0) {
             radio_gender_male.setChecked(true);
         } else if (gender[0] == 1) {
             radio_gender_female.setChecked(true);
         } else if (gender[0] == 2) {
             radio_gender_unknown.setChecked(true);
         }
         dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

         dialog.show();
         ButterKnife.bind(dialog, getActivity());
         txt_ok.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (radio_gender_male.isChecked()) {
                     gender[0] = 0;
                 } else if (radio_gender_female.isChecked()) {
                     gender[0] = 1;
                 } else if (radio_gender_unknown.isChecked()) {
                     gender[0] = 2;
                 }
                 if (gender[0] >= 0) {
                     Intent i = new Intent()
                             .putExtra(Constants.KEY_GENDER, gender[0]);
                     getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                     removeFragmentAndDismiss(getActivity());
                 } else {
                     Intent i = new Intent()
                             .putExtra(Constants.KEY_GENDER, "");
                     getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, i);
                     removeFragmentAndDismiss(getActivity());
                 }
             }
         });
         txt_cancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent()
                         .putExtra(Constants.KEY_GENDER, "");
                 getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, i);
                 removeFragmentAndDismiss(getActivity());
             }
         });
         txt_cancel.setTextColor(getResources().getColor(R.color.text_ok));
         txt_ok.setTextColor(getResources().getColor(R.color.text_ok));
         radio_gender_female.setTextColor(getResources().getColor(R.color.black));
         radio_gender_male.setTextColor(getResources().getColor(R.color.black));
         radio_gender_unknown.setTextColor(getResources().getColor(R.color.black));
         txt_title.setTextColor(getResources().getColor(R.color.black));
         return dialog;
     }*/
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialog_gender);
        TextView txt_ok = (TextView) dialog.findViewById(R.id.txt_ok);
        TextView txt_cancel = (TextView) dialog.findViewById(R.id.txt_cancel);
        RecyclerView recycler = (RecyclerView) dialog.findViewById(R.id.recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        final ArrayList<GenderInfo> list = Utils.getlistGender(getActivity());
        for (GenderInfo info : list) {
            if (getArguments().getInt(Constants.KEY_GENDER) == info.getId()) {
                Log.i(TAG, "=================>RELATION mac dinh: " + info.getId());
                info.setSelected(true);
                gender = info.getId();
                // mInfo = info;
                break;
            }
        }
        adapter = new GenderRecyclerViewAdapter(getActivity(), list);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);
        /*recycler.addOnItemTouchListener(new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mInfo = list.get(position);
                mInfo.setSelected(!mInfo.isSelected());
                relation = mInfo.getId();
            }
        }));*/

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
        ButterKnife.bind(dialog, getActivity());
        txt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenderInfo mInfo = adapter.getGenderSelected();
                if (mInfo != null && mInfo.isSelected()) {
                    gender = mInfo.getId();
                } else {
                    gender = -1;
                }
                if (gender >= 0) {
                    Intent i = new Intent()
                            .putExtra(Constants.KEY_GENDER, gender);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                    removeFragmentAndDismiss(getActivity());
                } else {
                    Intent i = new Intent()
                            .putExtra(Constants.KEY_GENDER, "");
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, i);
                    removeFragmentAndDismiss(getActivity());
                }

            }
        });
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent()
                        .putExtra(Constants.KEY_GENDER, "");
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, i);
                removeFragmentAndDismiss(getActivity());
            }
        });
        return dialog;
    }

    public void removeFragmentAndDismiss(FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        Fragment prev = fragmentManager.findFragmentByTag(DialogChooseGender.class.getSimpleName());
        if (prev != null) {
            fragmentTransaction.remove(prev);
        }
        fragmentTransaction.commit();
        dialog.dismiss();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Intent i = new Intent()
                .putExtra(Constants.KEY_GENDER, "");
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, i);
        removeFragmentAndDismiss(getActivity());
    }
}
