package com.quyenlx.learningjapanese.listerner;

import java.util.Date;

/**
 * Created by LxQuyen on 20/08/2016.
 */
public abstract class SlideDateTimeListener {
    /**
     * Informs the client when the user presses "OK"
     * and selects a date and time.
     *
     * @param date The {@code Date} object that contains the date
     *             and time that the user has selected.
     */
    public abstract void onDateTimeSet(Date date);

    /**
     * Informs the client when the user cancels the
     * dialog by pressing Cancel, touching outside
     * the dialog or pressing the Back button.
     * This override is optional.
     */
    public void onDateTimeCancel() {

    }
}