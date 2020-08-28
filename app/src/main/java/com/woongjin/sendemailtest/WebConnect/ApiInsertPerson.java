package com.woongjin.sendemailtest.WebConnect;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.woongjin.sendemailtest.Popup.ProgressPopup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Created by Jonathan on 2016. 8. 18..
 */
public class ApiInsertPerson extends AsyncTask<HashMap<String, String>, String, JSONObject> {

//  AsyncTask< 1, 2, 3 > 에서
//  1번 파라미터는 protected 3 doInBackground(1... params)으로 들어간다
//  2번 파라미터는
//  3번 파라미터는 protected void onPostExecute(3 result)  으로 들어간다  3번은 doInBackground 의 return 형태이기도 해야 한다

    Integer sdata = 0;
    HashMap<String, String> getData = new HashMap<String, String>();
    Context context;

    public InterfaceAsyncResponse interfaceAsyncResponse = null;



    public ApiInsertPerson(Context context, InterfaceAsyncResponse delegate)
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
            result = WebApi.getPerson(context, getData);

            Log.e("Jonathan", "1 result :: " + result);
            JSONArray jArr = result.getJSONArray("getData");
            ArrayList<HashMap<String, String>> arryItem2 = new ArrayList<HashMap<String, String>>();


            for(int i = 0 ; i < jArr.length() ; i++)
            {
                HashMap<String, String> getDataMap = new HashMap<String, String>();

                Iterator<String> key = jArr.getJSONObject(i).keys();
                while (key.hasNext())
                {
                    String DataKey = key.next();
                    getDataMap.put(DataKey, jArr.getJSONObject(i).get(DataKey).toString());

                }

                arryItem2.add(getDataMap);
            }

            if("0".equals(arryItem2.get(0).get("C_COUNT")))
            {
                result = WebApi.insertPerson(context, getData);
            }
            else
            {
                result = WebApi.updatePerson(context, getData);
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
