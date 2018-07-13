package com.yamschikov.dima.justtodo.tasksactivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.yamschikov.dima.justtodo.R;

import java.util.Calendar;

public class CustomDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    OnDateSetListener mDateSetListener;
    DatePicker view;

    int yy;
    int mm;
    int dd;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
         yy = calendar.get(Calendar.YEAR);
         mm = calendar.get(Calendar.MONTH);
         dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), R.style.DialogTheme, this, yy, mm, dd);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (this.mDateSetListener != null) {

            view.setMaxDate(System.currentTimeMillis());

            this.mDateSetListener.onDateSet(view, year, monthOfYear, dayOfMonth);
        }
    }

    public void setOnDateSetListener(OnDateSetListener dateSetListener) {
        this.mDateSetListener = dateSetListener;
    }


    public interface OnDateSetListener {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth);
    }
}