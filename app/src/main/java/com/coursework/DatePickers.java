package com.coursework;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.DateUtils;
import android.widget.DatePicker;
import android.widget.TextView;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/***
 * Класс отвечающий за все манипуляции связанные с датой
 */
public class DatePickers {
    Calendar dateCal=Calendar.getInstance();
 DatePickerDialog datePic;
    TextView date;

    Context context;

    public DatePickers(Context contextT, TextView dateTv) {
        context=contextT;
        date=dateTv;
        context=contextT;
    }

    public void setDate(Context context) {

        datePic=new DatePickerDialog(context , d,
                dateCal.get(Calendar.YEAR),
                dateCal.get(Calendar.MONTH),
                dateCal.get(Calendar.DAY_OF_MONTH));
        datePic.show();

    }



    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateCal.set(Calendar.YEAR, year);
            dateCal.set(Calendar.MONTH, monthOfYear);
            dateCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate();
        }
    };

    private void setInitialDate() {

        date.setText(DateUtils.formatDateTime(context,
                dateCal.getTimeInMillis(), DateUtils.FORMAT_SHOW_YEAR|DateUtils.FORMAT_NUMERIC_DATE));
    }
}
