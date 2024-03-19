package ru.mirea.shchukin.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

public class MyDateDialogFragment extends DialogFragment {

    private int myYear, myMonth, myDay;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        myYear = c.get(java.util.Calendar.YEAR);
        myMonth = c.get(java.util.Calendar.MONTH);
        myDay = c.get(java.util.Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), myCallBack, myYear, myMonth, myDay);
    }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myYear = year;
            myMonth = month;
            myDay = dayOfMonth;
            String message = "Выбранная дата: " + myDay + "/" + (myMonth + 1) + "/" + myYear;
            View rootView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
            Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
        }
    };
}
