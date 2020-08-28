package com.woongjin.sendemailtest.WebConnect;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.woongjin.sendemailtest.Popup.ProgressPopup;

import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by Jonathan on 2016. 8. 18..
 */
public class ApiGetContracts extends AsyncTask<HashMap<String, String>, String, JSONObject> {

//  AsyncTask< 1, 2, 3 > 에서
//  1번 파라미터는 protected 3 doInBackground(1... params)으로 들어간다
//  2번 파라미터는
//  3번 파라미터는 protected void onPostExecute(3 result)  으로 들어간다  3번은 doInBackground 의 return 형태이기도 해야 한다

    Integer sdata = 0;
    HashMap<String, String> getData = new HashMap<String, String>();
    Context context;

    public InterfaceAsyncResponse interfaceAsyncResponse = null;



    public ApiGetContracts(Context context, InterfaceAsyncResponse delegate)
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
            result = WebApi.getContract(context, getData);
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
