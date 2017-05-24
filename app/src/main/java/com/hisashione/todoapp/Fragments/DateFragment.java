package com.hisashione.todoapp.Fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;

import com.hisashione.todoapp.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateFragment extends BottomSheetDialogFragment {

    Button button;



    private BottomSheetBehavior.BottomSheetCallback
            mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
            if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                //dismiss();

                bottomSheet.post(new Runnable() {
                    @Override public void run() {
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                });
                //BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {


        }
    };


    public DateFragment() {
        // Required empty public constructor
    }


    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_date, null);

        button = (Button)getActivity().findViewById(R.id.dateList);



        DatePicker picker = (DatePicker) contentView.findViewById(R.id.calendar_2);




        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        picker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                //Log.d("Tag_2", "Year=" + year + " Month=" + (month + 1) + " day=" + dayOfMonth);




                int finalMont = month + 1;

                //XT_Hisashi
                String year1 = String.valueOf(year);
                String subYear = year1.substring(Math.max(year1.length() - 2, 0));

                String day_1 = String.format("%02d", dayOfMonth);


                String month_1 = String.format("%02d", month + 1);

                //ageTextview.setText("" + month_1 + "-" + day_1 + "-" + subYear );
                button.setText("" + month_1 + "-" + day_1 + "-" + subYear);



            }
        });




        dialog.setContentView(contentView);
    }



}
