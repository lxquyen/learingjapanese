package com.quyenlx.learningjapanese.dialog;

import android.app.Activity;
import android.app.Dialog;
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
import com.quyenlx.learningjapanese.adapter.CityRecyclerViewAdapter;
import com.quyenlx.learningjapanese.model.CityInfo;

import java.util.ArrayList;

/**
 * Created by LxQuyen on 20/08/2016.
 */
public class DialogChooseCities extends DialogFragment {
    private static final String TAG = DialogChooseCities.class.getSimpleName();
    Dialog dialog;
    // private CityInfo mInfo;
    private ArrayList<CityInfo> list;
    private CityRecyclerViewAdapter adapter;
    private CityInfo mInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static DialogChooseCities newInstance(String cty) {
        Log.i(TAG, "=================>thanh pho mac dinh: " + cty);
        DialogChooseCities dialogFragment = new DialogChooseCities();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_CITY, cty);
        dialogFragment.setArguments(bundle);
        return dialogFragment;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialog_city);
        TextView txt_ok = (TextView) dialog.findViewById(R.id.txt_ok);
        TextView txt_title = (TextView) dialog.findViewById(R.id.txt_title);
        TextView txt_cancel = (TextView) dialog.findViewById(R.id.txt_cancel);
        RecyclerView recycler = (RecyclerView) dialog.findViewById(R.id.recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        list = Utils.getlistCities(getActivity());
        for (CityInfo info : list) {
            if (getArguments().getString(Constants.KEY_CITY).equals(info.getTitle())) {
                Log.i(TAG, "=================>co thanh pho mac dinh" + info.getTitle());
                info.setSelected(true);
                //  mInfo = info;
                break;
            }
        }
        adapter = new CityRecyclerViewAdapter(getActivity(), list);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        txt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityInfo info = adapter.getCityInfoSelect();
                if (info != null && info.isSelected()) {
                    Intent i = new Intent()
                            .putExtra(Constants.KEY_CITY, info.getTitle());
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                    removeFragmentAndDismiss(getActivity());
                } else {
                    Intent i = new Intent()
                            .putExtra(Constants.KEY_CITY, "");
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                    removeFragmentAndDismiss(getActivity());

                }

            }
        });
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent()
                        .putExtra(Constants.KEY_CITY, "");
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, i);
                removeFragmentAndDismiss(getActivity());
            }
        });
        txt_cancel.setTextColor(getResources().getColor(R.color.colorAccent));
        txt_ok.setTextColor(getResources().getColor(R.color.colorAccent));
        txt_title.setText("Thành phô");//都市を選択してください
        txt_title.setTextColor(getResources().getColor(R.color.black));
        return dialog;
    }

    public void removeFragmentAndDismiss(FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        Fragment prev = fragmentManager.findFragmentByTag(DialogChooseCities.class.getSimpleName());
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
                .putExtra(Constants.KEY_CITY, "");
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, i);
        removeFragmentAndDismiss(getActivity());
    }
}