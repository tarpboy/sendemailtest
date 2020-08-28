 package com.woongjin.sendemailtest.WebConnect;

 import android.app.Activity;
 import android.content.Context;
 import android.net.ConnectivityManager;
 import android.net.NetworkInfo;
 import android.util.Log;

 import com.woongjin.sendemailtest.Popup.NoticePopup;
 import com.woongjin.sendemailtest.Popup.OnEventOkListener;

 import org.json.JSONObject;

 import java.net.URLEncoder;
 import java.util.HashMap;
 import java.util.Map;


 public class WebConnection
{
	
//	  public static final String SERVER_URL = "http://ec2-52-78-121-164.ap-northeast-2.compute.amazonaws.com:8080/doHelloParamTest";
//    public static final String SERVER_URL = "http://222.122.20.35:8080/onWedding";
    public static final String SERVER_URL = "http://115.68.20.80:8080/onWedding";

		
	private static final int CONNECTION_TIME_OUT = 10 * 1000;

	public static class NetCheck {
		public static final int NETWORK_DISCONNECTED = -1;
		public static final int NETWORK_CONNECTED_3G = 0;
		public static final int NETWORK_CONNECTED_WIFI = 1;
		public static final int NETWORK_CONNECTED_ETC = 2;


		public static int NetStateCheck(Context mCtx){
			ConnectivityManager cm = (ConnectivityManager)mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			if(info == null || !info.isConnected()){
				return NETWORK_DISCONNECTED;
			}
			switch(info.getType()){
			case ConnectivityManager.TYPE_WIFI:
				return NETWORK_CONNECTED_WIFI;
			case  ConnectivityManager.TYPE_MOBILE:
				return NETWORK_CONNECTED_3G;
			default:
				return NETWORK_CONNECTED_ETC;
			}
		}
	}



	public static void checkNetwork(final Context context)
    {
        int NetError = NetCheck.NetStateCheck(context);
        if(NetError == NetCheck.NETWORK_DISCONNECTED){
            Log.e("Jonathan", "네트워크 에러남");

            ((Activity)context).runOnUiThread(new Runnable() {
                public void run() {

                    NoticePopup popup = new NoticePopup(context, "네트워크 연결에 실패하였습니다.", new OnEventOkListener() {
                        @Override
                        public void onOk() {
                            // TODO Auto-generated method stub
                            ((Activity)context).moveTaskToBack(true);
                            ((Activity)context).finish();
                            android.os.Process.killProcess(android.os.Process.myPid());

                        }
                    });
                    popup.setCanceledOnTouchOutside(false);
                    popup.show();

                }
            });



        }


    }





    //selectQuery
    public static JSONObject selectQuery(Context context, String query) throws Exception
    {

        JSONObject returnJson = new JSONObject();
        checkNetwork(context);
//        int NetError = NetCheck.NetStateCheck(context);
//        if(NetError == NetCheck.NETWORK_DISCONNECTED){
//            Log.e("Jonathan", "네트워크 에러남");
//            return null;
//        }

        HttpClient.Builder http =	new HttpClient.Builder("POST", SERVER_URL + "/doHelloParam6");
        Map<String, String> result = new HashMap<String, String>();

        Log.e("Jonathan", "selectQuery : " + query);

        result.put("query", URLEncoder.encode(query, "utf-8"));

        http.addAllParameters(result );

        // HTTP 요청 전송
        HttpClient post = http.create();
        post.request();

        // 응답 본문 가져오기
        String body = post.getBody();

        // body가 JSON 형태라면 JSON 형태로 나눠준다
        JSONObject response =  new JSONObject(body);
        if(response == null ){
            // body에 아무것도 없을때.
            return null;
        }
        else
        {
            returnJson = response;
            return returnJson;
        }


    }



    //insertQuery
    public static JSONObject insertQuery(Context context, String query) throws Exception
    {

        JSONObject returnJson = new JSONObject();
        checkNetwork(context);
//        int NetError = NetCheck.NetStateCheck(context);
//        if(NetError == NetCheck.NETWORK_DISCONNECTED){
//            Log.e("Jonathan", "네트워크 에러남");
//            return null;
//        }

        HttpClient.Builder http =	new HttpClient.Builder("POST", SERVER_URL + "/doHelloParam7");
        Map<String, String> result = new HashMap<String, String>();

        Log.e("Jonathan", "insertQuery : " + query);

        result.put("query", URLEncoder.encode(query, "utf-8"));

        http.addAllParameters(result );

        // HTTP 요청 전송
        HttpClient post = http.create();
        post.request();

        // 응답 본문 가져오기
        String body = post.getBody();

        // body가 JSON 형태라면 JSON 형태로 나눠준다
        JSONObject response =  new JSONObject(body);
        if(response == null ){
            // body에 아무것도 없을때.
            return null;
        }
        else
        {
            returnJson = response;
            return returnJson;
        }


    }




    //updateQuery
    public static JSONObject updateQuery(Context context, String query) throws Exception
    {

        JSONObject returnJson = new JSONObject();
        checkNetwork(context);
//        int NetError = NetCheck.NetStateCheck(context);
//        if(NetError == NetCheck.NETWORK_DISCONNECTED){
//            Log.e("Jonathan", "네트워크 에러남");
//            return null;
//        }

        HttpClient.Builder http =	new HttpClient.Builder("POST", SERVER_URL + "/doHelloParam8");
        Map<String, String> result = new HashMap<String, String>();

        Log.e("Jonathan", "updateQuery : " + query);

        result.put("query", URLEncoder.encode(query, "utf-8"));

        http.addAllParameters(result );

        // HTTP 요청 전송
        HttpClient post = http.create();
        post.request();

        // 응답 본문 가져오기
        String body = post.getBody();

        // body가 JSON 형태라면 JSON 형태로 나눠준다
        JSONObject response =  new JSONObject(body);
        if(response == null ){
            // body에 아무것도 없을때.
            return null;
        }
        else
        {
            returnJson = response;
            return returnJson;
        }


    }



    //deleteQuery
    public static JSONObject deleteQuery(Context context, String query) throws Exception
    {

        JSONObject returnJson = new JSONObject();
        checkNetwork(context);
//        int NetError = NetCheck.NetStateCheck(context);
//        if(NetError == NetCheck.NETWORK_DISCONNECTED){
//            Log.e("Jonathan", "네트워크 에러남");
//            return null;
//        }

        HttpClient.Builder http =	new HttpClient.Builder("POST", SERVER_URL + "/doHelloParam9");
        Map<String, String> result = new HashMap<String, String>();

        Log.e("Jonathan", "deleteQuery : " + query);

        result.put("query", URLEncoder.encode(query, "utf-8"));

        http.addAllParameters(result );

        // HTTP 요청 전송
        HttpClient post = http.create();
        post.request();

        // 응답 본문 가져오기
        String body = post.getBody();

        // body가 JSON 형태라면 JSON 형태로 나눠준다
        JSONObject response =  new JSONObject(body);
        if(response == null ){
            // body에 아무것도 없을때.
            return null;
        }
        else
        {
            returnJson = response;
            return returnJson;
        }


    }





    //selectQuery
    public static JSONObject selectTwoQuery(Context context, String query1, String query2) throws Exception
    {

        JSONObject returnJson = new JSONObject();
        checkNetwork(context);
//        int NetError = NetCheck.NetStateCheck(context);
//        if(NetError == NetCheck.NETWORK_DISCONNECTED){
//            Log.e("Jonathan", "네트워크 에러남");
//            return null;
//        }

        HttpClient.Builder http =	new HttpClient.Builder("POST", SERVER_URL + "/doHelloParam6_1");
        Map<String, String> result = new HashMap<String, String>();

        Log.e("Jonathan", "selectQuery : " + query1);

        result.put("query", URLEncoder.encode(query1, "utf-8"));
//        result.put("query2", URLEncoder.encode(query2, "utf-8"));

        http.addAllParameters(result );

        // HTTP 요청 전송
        HttpClient post = http.create();
        post.request();

        // 응답 본문 가져오기
        String body = post.getBody();

        // body가 JSON 형태라면 JSON 형태로 나눠준다
        JSONObject response =  new JSONObject(body);
        if(response == null ){
            // body에 아무것도 없을때.
            return null;
        }
        else
        {
            returnJson = response;
            return returnJson;
        }


    }







}

