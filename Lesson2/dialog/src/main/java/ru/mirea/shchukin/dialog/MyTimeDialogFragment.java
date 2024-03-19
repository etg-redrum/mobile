package ru.mirea.shchukin.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

public class MyTimeDialogFragment extends DialogFragment {
    private int myHour, myMinute;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        myHour = c.get(java.util.Calendar.HOUR_OF_DAY);
        myMinute = c.get(java.util.Calendar.MINUTE);

        return new TimePickerDialog(getContext(), myCallBack, myHour, myMinute, true);
    }

    TimePickerDialog.OnTimeSetListener myCallBack = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;

            String message = "Выбранное время: " + myHour + ":" + myMinute;
            View rootView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);

            Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
        }
    };
}
