package com.woongjin.sendemailtest.Popup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


import com.woongjin.sendemailtest.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class NoticePopup extends Dialog implements View.OnClickListener {

	private String mBody2 = "";

	private OnEventOkListener mOnEventPopupListener;
	private OnEventCancelListener mOnEventCancelListener;



	private Context mContext;
	NumberFormat formatter = new DecimalFormat("#0");


	TextView tv_notice_popup;
	Button bt_notice_confirm;



	public NoticePopup(Context context, String body2, OnEventOkListener onEventPopupListener) {
		super(context, R.style.Theme_Dialog);



		mContext = context;

		mBody2 = body2;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.notice_popup);
		mOnEventPopupListener = onEventPopupListener;


		tv_notice_popup = (TextView)findViewById(R.id.tv_notice_popup);
		bt_notice_confirm = (Button)findViewById(R.id.bt_notice_confirm);


		if(mBody2.length() > 20)
		{
			tv_notice_popup.setTextSize(13);
		}


		tv_notice_popup.setText(mBody2);

		bt_notice_confirm.setOnClickListener(this);



	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);



	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub


		switch (v.getId()) {


			case R.id.bt_notice_confirm:

//				dismiss();
				clickOk();
				break;







		}


	}

	private void clickOk() {
		if (mOnEventPopupListener != null)
			mOnEventPopupListener.onOk();

		dismiss();
	}

	private void clickCancel() {
		if (mOnEventCancelListener != null)
			mOnEventCancelListener.onCancel();

//		dismiss();
	}


	public void setOnCancelListener(OnEventCancelListener onEventCancelListener) {
		mOnEventCancelListener = onEventCancelListener;
	}









}
