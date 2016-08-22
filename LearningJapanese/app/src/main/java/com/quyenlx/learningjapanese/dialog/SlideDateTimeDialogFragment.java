package com.quyenlx.learningjapanese.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.quyenlx.learningjapanese.R;
import com.quyenlx.learningjapanese.Util.SlideDateTimePicker;
import com.quyenlx.learningjapanese.listerner.DateFragment;
import com.quyenlx.learningjapanese.listerner.SlideDateTimeListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by LxQuyen on 20/08/2016.
 */
public class SlideDateTimeDialogFragment extends DialogFragment implements DateFragment.DateChangedListener {
    public static final String TAG_SLIDE_DATE_TIME_DIALOG_FRAGMENT = "tagSlideDateTimeDialogFragment";
    private static final String TAG = SlideDateTimeDialogFragment.class.getSimpleName();

    private static SlideDateTimeListener mListener;

    private Context mContext;
    private View mButtonHorizontalDivider;
    private View mButtonVerticalDivider;
    private Button mOkButton;
    private Button mCancelButton;
    private Date mInitialDate;
    private int mTheme;
    private int mIndicatorColor;
    private Date mMinDate;
    private Date mMaxDate;
    private boolean mIsClientSpecified24HourTime;
    private boolean mIs24HourTime;
    private Calendar mCalendar;
    private int mDateFlags =
            DateUtils.FORMAT_SHOW_WEEKDAY |
                    DateUtils.FORMAT_SHOW_DATE |
                    DateUtils.FORMAT_ABBREV_ALL;
    private TextView txt_birthday;
    private DateFragment dateFragment;

    public SlideDateTimeDialogFragment() {
        // Required empty public constructor
    }

    /**
     * <p>Return a new instance of {@code SlideDateTimeDialogFragment} with its bundle
     * filled with the incoming arguments.</p>
     * <p/>
     * <p>Called by {@link SlideDateTimePicker#show()}.</p>
     *
     * @param listener
     * @param initialDate
     * @param minDate
     * @param maxDate
     * @param isClientSpecified24HourTime
     * @param is24HourTime
     * @param theme
     * @param indicatorColor
     * @return
     */
    public static SlideDateTimeDialogFragment newInstance(SlideDateTimeListener listener,
                                                          Date initialDate, Date minDate, Date maxDate, boolean isClientSpecified24HourTime,
                                                          boolean is24HourTime, int theme, int indicatorColor) {
        mListener = listener;

        // Create a new instance of SlideDateTimeDialogFragment
        SlideDateTimeDialogFragment dialogFragment = new SlideDateTimeDialogFragment();

        // Store the arguments and attach the bundle to the fragment
        Bundle bundle = new Bundle();
        bundle.putSerializable("initialDate", initialDate);
        bundle.putSerializable("minDate", minDate);
        bundle.putSerializable("maxDate", maxDate);
        bundle.putBoolean("isClientSpecified24HourTime", isClientSpecified24HourTime);
        bundle.putBoolean("is24HourTime", is24HourTime);
        bundle.putInt("theme", theme);
        bundle.putInt("indicatorColor", indicatorColor);
        dialogFragment.setArguments(bundle);

        // Return the fragment with its bundle
        return dialogFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mContext = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setRetainInstance(true);

        unpackBundle();

        mCalendar = Calendar.getInstance();
        mCalendar.setTime(mInitialDate);

        switch (mTheme) {
            case SlideDateTimePicker.HOLO_DARK:
                setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Dialog_NoActionBar);
                break;
            case SlideDateTimePicker.HOLO_LIGHT:
                setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
                break;
            default:  // if no theme was specified, default to holo light
                setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.slide_date_time_picker, container);

        setupViews(view);
        customizeViews();
        initButtons();
        dateFragment = DateFragment.newInstance(
                mTheme,
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH),
                mMinDate,
                mMaxDate);
        dateFragment.setTargetFragment(SlideDateTimeDialogFragment.this, 100);

        FragmentManager fmgr = getChildFragmentManager();
        FragmentTransaction ft = fmgr.beginTransaction();
        ft.replace(R.id.datePicker, dateFragment, TAG);
        ft.commit();
        int intMonth = Integer.parseInt((String) android.text.format.DateFormat.format("MM", mInitialDate)); //06
        int year = Integer.parseInt((String) android.text.format.DateFormat.format("yyyy", mInitialDate)); //2013
        int day = Integer.parseInt((String) android.text.format.DateFormat.format("dd", mInitialDate)); //20
        //txt_birthday.setText(year + "年" + intMonth + "月" + day + "日");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault());
        txt_birthday.setText(dateFormat.format(mInitialDate));
        txt_birthday.setTextColor(getResources().getColor(R.color.green_system));
        return view;
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }

        super.onDestroyView();
    }

    private void unpackBundle() {
        Bundle args = getArguments();

        mInitialDate = (Date) args.getSerializable("initialDate");
        mMinDate = (Date) args.getSerializable("minDate");
        mMaxDate = (Date) args.getSerializable("maxDate");
        mIsClientSpecified24HourTime = args.getBoolean("isClientSpecified24HourTime");
        mIs24HourTime = args.getBoolean("is24HourTime");
        mTheme = args.getInt("theme");
        mIndicatorColor = args.getInt("indicatorColor");
    }

    private void setupViews(View v) {
        mButtonHorizontalDivider = v.findViewById(R.id.buttonHorizontalDivider);
        mButtonVerticalDivider = v.findViewById(R.id.buttonVerticalDivider);
        mOkButton = (Button) v.findViewById(R.id.okButton);
        mCancelButton = (Button) v.findViewById(R.id.cancelButton);
        txt_birthday = (TextView) v.findViewById(R.id.txt_birthday);
    }

    private void customizeViews() {
        int lineColor = mTheme == SlideDateTimePicker.HOLO_DARK ?
                getResources().getColor(R.color.gray_holo_dark) :
                getResources().getColor(R.color.gray_holo_light);

        // Set the colors of the horizontal and vertical lines for the
        // bottom buttons depending on the theme.
        switch (mTheme) {
            case SlideDateTimePicker.HOLO_LIGHT:
            case SlideDateTimePicker.HOLO_DARK:
                mButtonHorizontalDivider.setBackgroundColor(lineColor);
                mButtonVerticalDivider.setBackgroundColor(lineColor);
                break;

            default:  // if no theme was specified, default to holo light
                mButtonHorizontalDivider.setBackgroundColor(getResources().getColor(R.color.gray_holo_light));
                mButtonVerticalDivider.setBackgroundColor(getResources().getColor(R.color.gray_holo_light));
        }

    }


    private void initButtons() {
        mOkButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener == null) {
                    throw new NullPointerException(
                            "Listener no longer exists for mOkButton");
                }

                mListener.onDateTimeSet(new Date(mCalendar.getTimeInMillis()));

                dismiss();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener == null) {
                    throw new NullPointerException(
                            "Listener no longer exists for mCancelButton");
                }

                mListener.onDateTimeCancel();

                dismiss();
            }
        });
    }

    /**
     * <p>The callback used by the DatePicker to update {@code mCalendar} as
     * the user changes the date. Each time this is called, we also update
     * the text on the date tab to reflect the date the user has currenly
     * selected.</p>
     * <p/>
     * <p>Implements the {@link DateFragment.DateChangedListener}
     * interface.</p>
     */
    @Override
    public void onDateChanged(int year, int month, int day) {
        mCalendar.set(year, month, day);
        Calendar nowDate = Calendar.getInstance();
        if (mCalendar.after(nowDate)) {
            mCalendar = nowDate;
            dateFragment = DateFragment.newInstance(
                    mTheme,
                    mCalendar.get(Calendar.YEAR),
                    mCalendar.get(Calendar.MONTH),
                    mCalendar.get(Calendar.DAY_OF_MONTH),
                    mMinDate,
                    mMaxDate);
            dateFragment.setTargetFragment(SlideDateTimeDialogFragment.this, 100);

            FragmentManager fmgr = getChildFragmentManager();
            FragmentTransaction ft = fmgr.beginTransaction();
            ft.replace(R.id.datePicker, dateFragment, TAG);
            ft.commit();
        }
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault());
        txt_birthday.setText(dateFormat.format(mCalendar.getTime()));

    }

    /**
     * <p>Called when the user clicks outside the dialog or presses the <b>Back</b>
     * button.</p>
     * <p/>
     * <p><b>Note:</b> Actual <b>Cancel</b> button clicks are handled by {@code mCancelButton}'s
     * event handler.</p>
     */
    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);

        if (mListener == null) {
            throw new NullPointerException(
                    "Listener no longer exists in onCancel()");
        }

        mListener.onDateTimeCancel();
    }

  /*  private class ViewPagerAdapter extends FragmentPagerAdapter
    {
        public ViewPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position)
            {
            case 0:
                DateFragment dateFragment = DateFragment.newInstance(
                    mTheme,
                    mCalendar.get(Calendar.YEAR),
                    mCalendar.get(Calendar.MONTH),
                    mCalendar.get(Calendar.DAY_OF_MONTH),
                    mMinDate,
                    mMaxDate);
                dateFragment.setTargetFragment(SlideDateTimeDialogFragment.this, 100);
                return dateFragment;
            case 1:
                TimeFragment timeFragment = TimeFragment.newInstance(
                    mTheme,
                    mCalendar.get(Calendar.HOUR_OF_DAY),
                    mCalendar.get(Calendar.MINUTE),
                    mIsClientSpecified24HourTime,
                    mIs24HourTime);
                timeFragment.setTargetFragment(SlideDateTimeDialogFragment.this, 200);
                return timeFragment;
            default:
                return null;
            }
        }

        @Override
        public int getCount()
        {
            return 2;
        }
    }*/
}
