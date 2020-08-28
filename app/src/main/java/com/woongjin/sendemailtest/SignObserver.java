package com.woongjin.sendemailtest;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Iterator;

public class SignObserver {

    private static SignObserver signObserver = null;
    private ArrayList<SignData> observers;

    public synchronized static SignObserver getInstance() {

        if(signObserver == null) {

            signObserver = new SignObserver();
        }

        return signObserver;
    }

    /**
     * 차량 스캔 결과 이벤트를 받기 위해 등록받는 부분
     *
     * @param tag
     * @param signDataListener
     */
    public void registerObserver(String tag, OnSignDataListener signDataListener) {

        if (observers == null) {
            observers = new ArrayList<SignData>();
        }
        observers.clear();

        SignData signData = new SignData();
        signData.setSignDataListener(signDataListener);
        signData.setObserverTag(tag);
        observers.add(signData);
    }

    /**
     * 등록된 모든 클래스에 데이타를 보낸다.
     *
     * @param customerName : 고객명
     * @param bitmapSign :
     */
    public void notifyObserver(String customerName, Bitmap bitmapSign) {

        Iterator<SignData> iterator = observers.iterator();

        while (iterator.hasNext()) {

            SignData signData = iterator.next();
            signData.getSignDataListener().onSignData(signData.getObserverTag(), customerName, bitmapSign);
        }
    }

    public interface OnSignDataListener {

        /**
         *
         * @param tag : 이벤트 구분 값
         * @param customerName : 고객명
         * @param bitmapSign : 비트맵 이미지
         */
        void onSignData(String tag, String customerName, Bitmap bitmapSign);
    }

    /**
     * 이벤트 저장 클래스
     */
    public class SignData {

        OnSignDataListener signDataListener;
        String observerTag;

        public OnSignDataListener getSignDataListener() {
            return signDataListener;
        }

        public void setSignDataListener(OnSignDataListener signDataListener) {
            this.signDataListener = signDataListener;
        }

        public String getObserverTag() {
            return observerTag;
        }

        public void setObserverTag(String observerTag) {
            this.observerTag = observerTag;
        }
    }
}
