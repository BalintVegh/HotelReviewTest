package vegh.balint.hotelreviewtest.ui.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DatePicker dialog
 */
public class DatePickerFragment extends DialogFragment {

    public static final String TAG = "DatePickerFragment";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);

        dialog.getDatePicker().setMaxDate(new Date().getTime());

        return dialog;
    }

    public static String getCurrentDate(){
        Calendar c = Calendar.getInstance();

        return DateFormat.getDateInstance().format(c.getTime());
    }

    public static Long getDate(){
        Long date = getDate();
        return date;
    }
}
