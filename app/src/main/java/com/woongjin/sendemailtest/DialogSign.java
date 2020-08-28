package com.woongjin.sendemailtest;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/*  사용법
    Intent intent = new Intent(getContext(), SignDialog.class);
    intent.putExtra(SignDialog.KEY_CUSTOMER_NAME, "고객명");
    getContext().startActivity(intent);

    // 결과 이벤트 처리
    SignObserver.getInstance().registerObserver("Login", new SignObserver.OnSignDataListener() {
        @Override
        public void onSignData(String tag, String customerName, Bitmap bitmapSign) {

        }
    });
 */
public class DialogSign extends Dialog implements View.OnClickListener{

    long mLastClickTime = 0;

    SignView vSign;
    String mCustomerName = "";

    HashMap<String, String>  custInfo;
    HashMap<Integer, Bitmap> mImgBitmapMap;
    ArrayList<HashMap<String, String>> signList;

    public DialogSign(Context context, String custName, HashMap<String, String> custInfo, ArrayList<HashMap<String, String>> signList){
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.custInfo = custInfo;
        this.signList = signList;

        initView();
        initViewData();
    }

    /**
     * View 초기화
     */
    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.screen_popup_sign);
        vSign = (SignView) findViewById(R.id.v_popup_sign);



        findViewById(R.id.btn_popup_sign_initialize).setOnClickListener(this);
        findViewById(R.id.btn_popup_sign_save).setOnClickListener(this);


    }

    /**
     * View 데이타 셋팅
     */
    private void initViewData() {



    }

    private void vaildSignData() {
        SignObserver.getInstance().notifyObserver("sign", vSign.getSignToBitmap());
        dismiss();
    }

    @Override
    public void onClick(View v) {

        // 두번클릭을 막기위해
        if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();


        // 사인 초기화
         if(v.getId() == R.id.btn_popup_sign_initialize) {

            ((ImageView)findViewById(R.id.sign_preview)).setVisibility(View.GONE);
            vSign.clearSign();

            // LBN 2017.11.20
//            findViewById(R.id.btn_popup_sign_save).setEnabled(true);
            findViewById(R.id.btn_popup_sign_save).setVisibility(View.VISIBLE);

        }
        // 사인 이미지 Bitmap 반환
        else if(v.getId() == R.id.btn_popup_sign_save) {

            vaildSignData();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        return true;
    }





}
