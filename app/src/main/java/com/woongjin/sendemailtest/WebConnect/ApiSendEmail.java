package com.woongjin.sendemailtest.WebConnect;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.woongjin.sendemailtest.FileManager;
import com.woongjin.sendemailtest.GMailSender;
import com.woongjin.sendemailtest.Popup.ProgressPopup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;


/**
 * Created by Jonathan on 2016. 8. 18..
 */
public class ApiSendEmail extends AsyncTask<HashMap<String, String>, String, JSONObject> {

//  AsyncTask< 1, 2, 3 > 에서
//  1번 파라미터는 protected 3 doInBackground(1... params)으로 들어간다
//  2번 파라미터는
//  3번 파라미터는 protected void onPostExecute(3 result)  으로 들어간다  3번은 doInBackground 의 return 형태이기도 해야 한다

    Integer sdata = 0;
    HashMap<String, String> getData = new HashMap<String, String>();
    Context context;

    public InterfaceAsyncResponse interfaceAsyncResponse = null;



    public ApiSendEmail(Context context, InterfaceAsyncResponse delegate)
    {
        this.context = context;
        this.interfaceAsyncResponse = delegate;

        dialog = new ProgressPopup(context);
    }


    private ProgressPopup dialog;


    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        this.dialog.setMessage("로딩중...");
        this.dialog.show();
    }

    @Override
    protected JSONObject doInBackground(HashMap<String, String>... params) {
        // TODO Auto-generated method stub
        JSONObject result = new JSONObject();
        getData = params[0];


        try {
            String fileName = "";
            String ex_storage = FileManager.getExternalPath();
            // Get Absolute Path in External Sdcard
            String path = ex_storage + "/" + "SIGN_FOLDER";


//            fileName = "contract.html";
            fileName = getData.get("CONT_NAME")+ "_" +getData.get("NAME_PLACE");
            File file = new File(path, fileName);


            try {

                Log.e("Jonathan", "email ::::: " + getData.get("email"));
//                GMailSender gMailSender = new GMailSender("tarpboy1989@gmail.com", "Jonathan17!");
                GMailSender gMailSender = new GMailSender("payfun.kr@gmail.com", "vpdlvjswhgdk!$%");
                //GMailSender.sendMail(제목, 본문내용, 받는사람);
//            gMailSender.sendMail("계약서 전송.", html, "tarpboy@naver.com");
                gMailSender.sendMail("계약서 전송", "계약서 내용입니다.", "payfun.kr@gmail.com", getData.get("email"), file);

                Toast.makeText(context, "이메일을 성공적으로 보냈습니다.", Toast.LENGTH_SHORT).show();
            } catch (SendFailedException e) {
                Toast.makeText(context, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            } catch (MessagingException e) {
                Toast.makeText(context, "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }



            Log.e("Jonathan", "2 result :: " + result);



        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        if(result == null|| result.equals(""))
        {
            return null;
        }
        else
        {
            return result;
        }
    }



    @Override
    protected void onPostExecute(JSONObject result) {

        super.onPostExecute(result);

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        if(result == null){

            return;
        }
        String resultmsg = "";


        resultmsg = result.toString();
        Log.e("Jonathan", "resultmsg :: " + resultmsg);

        interfaceAsyncResponse.processFinish(resultmsg);


    }





}
