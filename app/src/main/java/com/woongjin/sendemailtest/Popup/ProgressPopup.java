package com.woongjin.sendemailtest.Popup;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.woongjin.sendemailtest.R;


/**
 * Created by Jonathan on 2017. 1. 8..
 */
public class ProgressPopup extends Dialog {


    TextView tv_progress_message;

    public ProgressPopup(Context context) {
        super(context, R.style.Theme_Dialog);

        WindowManager.LayoutParams wlmp = getWindow().getAttributes();


//        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
//        getWindow().setAttributes(wlmp);
//        setTitle(null);
//        setCancelable(false);

//        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(
                R.layout.progress_popup, null);

        tv_progress_message = (TextView)view.findViewById(R.id.tv_progress_message);
        setContentView(view);


    }



    public void setMessage(String messege)
    {
        tv_progress_message.setText(messege);
    }


    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}