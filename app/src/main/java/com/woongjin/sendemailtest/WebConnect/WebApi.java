 package com.woongjin.sendemailtest.WebConnect;

 import android.content.Context;
 import android.icu.text.SimpleDateFormat;
 import android.net.ConnectivityManager;
 import android.net.NetworkInfo;
 import android.util.Log;

 import org.json.JSONObject;

 import java.util.ArrayList;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.Locale;
 import java.util.Map;
 import java.util.Random;


 public class WebApi
{
	
//	public static final String SERVER_URL = "http://ec2-52-78-121-164.ap-northeast-2.compute.amazonaws.com:8080/doHelloParamTest";
//    public static final String SERVER_URL = "http://222.122.20.35:8080/onWedding";
    public static final String SERVER_URL = "http://115.68.20.80:8080/onWedding";

//    public static final String SCHEMA = "" + SCHEMA + "";
    public static final String SCHEMA = "onWedding.";



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






    public static JSONObject getContract(Context context, HashMap<String, String> sendData) throws Exception
    {


        String query = "SELECT * " +
                "FROM " + SCHEMA + "CT_TB_CONTRACT " +
                "";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;


    }



    public static JSONObject getContractHist(Context context, HashMap<String, String> sendData) throws Exception
    {


        String  query = "SELECT * ";
                query += "FROM " + SCHEMA + "CT_TB_CONTRACT_HIST " ;
                if(sendData.containsKey("name"))
                {
                    query += "WHERE NAME_PLACE  = '" + sendData.get("name")  + "' ";
                    query += "AND TEL_PLACE  = '" + sendData.get("tel")  + "' ";
                    query += "AND SEND_YN  = 'N' ";

                }
                query += "";

        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;


    }



    public static JSONObject getSingleContract(Context context, HashMap<String, String> sendData) throws Exception
    {


        String  query = "SELECT * ";
        query += "FROM " + SCHEMA + "CT_TB_CONTRACT " ;
        query += "WHERE CONT_NO  = '" + sendData.get("CONT_NO")  + "' ";
        query += "";

        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;


    }




    // 계약저장
    public static JSONObject insertContract(Context context, HashMap<String, String> getOrderMap ) throws Exception
    {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());


        String query = "";
        //쿼리를 여기서 만든다.

        query =  "INSERT INTO " + SCHEMA + "CT_TB_CONTRACT_HIST " +

                "(" +
                "CONT_NO" + "," +
//                "CONT_CON" + "," +
                "CONT_NAME" + "," +
                "NAME" + "," +
                "PHONE" + "," +
                "LOCA" + "," +
                "H_PAY"+ "," +
                "D_PAY" + "," +
                "BD_PAY" + "," +
                "BD_RAT" + "," +
                "WL_PAY" + "," +
                "WL_RAT" + "," +
                "REMARK" + "," +
                "SEND_YN" + "," +
                "CONT_FM_D" + "," +
                "CONT_TO_D" + "," +
                "UPLD_DT" +
                ") " +

                "VALUES " +

                "(" +
                "'" +  getOrderMap.get("CONT_NO") + "'" + "," +
//                "'" +  getOrderMap.get("CONT_CON") + "'" + "," +
                "'" +  getOrderMap.get("CONT_NAME") + "'" + "," +
                "'" +  getOrderMap.get("name") + "'" + "," +
                "'" +  getOrderMap.get("tel") + "'" + "," +
                "'" +  getOrderMap.get("loc") + "'" + "," +
                "'" +  getOrderMap.get("hpay") + "'" + "," +
                "'" +  getOrderMap.get("dpay") + "'" + "," +
                "'" +  getOrderMap.get("BD_PAY") + "'" + "," +
                "'" +  getOrderMap.get("BD_RAT") + "'" + "," +
                "'" +  getOrderMap.get("WL_PAY") + "'" + "," +
                "'" +  getOrderMap.get("WL_RAT") + "'" + "," +
                "'" +  getOrderMap.get("REMARK1") + getOrderMap.get("REMARK2") + "'" + "," +
                "'" +  "N" + "'" + "," +
                "'" +  getOrderMap.get("CONT_FM_D") + "'" + "," +
                "'" +  getOrderMap.get("CONT_TO_D") + "'" + "," +
                "'" +  currentDateandTime + "'" +
                ")" ;



        JSONObject returnJson = WebConnection.insertQuery(context, query);

        return returnJson;

    }




    //사람 찾기
    public static JSONObject getPerson(Context context, HashMap<String, String> sendData) throws Exception
    {


        String query = "SELECT COUNT(C_NAME) AS C_COUNT " +
                "FROM " + SCHEMA + "CT_TB_PERSON " +
                "WHERE C_NAME  = '" + sendData.get("name")  + "' " +
                "AND PHONE = '" + sendData.get("tel") + "' " +
                "";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;


    }



    //로그인
    public static JSONObject login(Context context, HashMap<String, String> sendData) throws Exception
    {


        String query = "SELECT COUNT(C_NAME) AS C_COUNT " +
                "FROM " + SCHEMA + "CT_TB_PERSON " +
                "WHERE C_NAME  = '" + sendData.get("name")  + "' " +
                "AND PHONE = '" + sendData.get("tel") + "' " +
                "AND PASWD = '" + sendData.get("paswd") + "' " +
                "";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;


    }




    // 사람저장
    public static JSONObject insertPerson(Context context, HashMap<String, String> getOrderMap ) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

        query =  "INSERT INTO " + SCHEMA + "CT_TB_PERSON " +

                "(" +
                "C_NAME" + "," +
                "PHONE" + "," +
                "PASWD"+
                ") " +

                "VALUES " +

                "(" +
                "'" +  getOrderMap.get("name") + "'" + "," +
                "'" +  getOrderMap.get("tel") + "'" + "," +
                "'" +  getRandomNumberString() + "'" +
                ")" ;



        JSONObject returnJson = WebConnection.insertQuery(context, query);

        return returnJson;

    }





    //사람 업데이트
    public static JSONObject updatePerson(Context context, HashMap<String, String> getData) throws Exception
    {


        String query = "";
        //쿼리를 여기서 만든다.

        query += "UPDATE " + SCHEMA + "CT_TB_PERSON SET ";
        query += "C_NAME"   +  " = " + "'" + getData.get("name")    +  "', ";
        query += "PHONE"   +  " = " + "'" + getData.get("tel")    +  "', " +
                "PASWD"   +  " = " + "'" + getRandomNumberString() + "' " +
                "WHERE C_NAME = '" + getData.get("name")  + "' " +
                "AND PHONE = '" + getData.get("tel") + "' " ;

        JSONObject returnJson = WebConnection.updateQuery(context, query);

        return returnJson;


    }


    //랜덤 키 생성
    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }




    //전화번호 인증
    public static void reSendConfirmNum(Context context, HashMap<String, String> getOrderMap ) throws Exception
    {

        JSONObject returnJson = new JSONObject();


        int NetError = NetCheck.NetStateCheck(context);
        if(NetError == NetCheck.NETWORK_DISCONNECTED){
            Log.e("Jonathan", "네트워크 에러남");
        }

        HttpClient.Builder http =	new HttpClient.Builder("POST", SERVER_URL + "/doPushTest");

        Map<String, String> result = new HashMap<String, String>();


        result.put("phoneNum", getOrderMap.get("USER_PHONE_NUM"));
        result.put("confirmNum", getOrderMap.get("USER_CONFIRM_NUM"));


        http.addAllParameters(result);


        // HTTP 요청 전송
        HttpClient post = http.create();
        post.request();

        // 응답 본문 가져오기
        String body = post.getBody();


    }







    public static JSONObject regConfirmCheck(Context context, HashMap<String, String> sendData) throws Exception
    {


        String query = "SELECT COUNT(USER_CONFIRM_NUM) AS CHECK_NUM " +
                "FROM " + SCHEMA + "OW_PUSH_INFO " +
                "WHERE USER_PHONE_NUM  = '" + sendData.get("USER_PHONE_NUM") + "' " +
                "AND USER_CONFIRM_NUM = '" + sendData.get("USER_CONFIRM_NUM") + "' " +
                "LIMIT 0, 1 ";

        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }







    //B1_USER_INFO 에 저장
    public static JSONObject regInsertUserInfo(Context context, HashMap<String, String> getData) throws Exception
    {


        String query = "";
        //쿼리를 여기서 만든다.

        query =  "INSERT INTO " + SCHEMA + "OW_USER_INFO " +


//        `USER_ID`, `USER_PASS`, `USER_PHONE_NUM`, `USER_NAME`, `USER_UUID`, `USER_PUSH_KEY`, `USER_REGI_TYPE`,
//                    `USER_BIRTH_DAY`, `USER_GENDER`, `USER_CONFIRM_NUM`, `REG_DATE`, `USE_YN`, `USER_TOKEN`


                "(" +
                "USER_ID" + "," +
                "USER_PASS" + "," +
                "USER_PHONE_NUM" + "," +
                "USER_NAME" + "," +
                "USER_UUID" + "," +
                "USER_PUSH_KEY" + "," +
                "USER_REGI_TYPE" + "," +
                "USER_BIRTH_DAY" + "," +
                "USER_GENDER" + "," +
                "USER_CONFIRM_NUM" + "," +
                "USER_USE_YN" + "," +
                "USER_TOKEN" + "," +
                "REG_DATE" +
                ") " +

                "VALUES " +

                "(" +
                "'" +  getData.get("USER_ID") + "'" + "," +
                "'" +  getData.get("USER_PASS") + "'" + "," +
                "'" +  getData.get("USER_PHONE_NUM") + "'" + "," +
                "'" +  getData.get("USER_NAME") + "'" + "," +
                "'" +  getData.get("USER_UUID") + "'" + "," +
                "'" +  getData.get("USER_PUSH_KEY") + "'" + "," +
                "'" +  getData.get("USER_REGI_TYPE") + "'" + "," +
                "'" +  getData.get("USER_BIRTH_DAY") + "'" + "," +
                "'" +  getData.get("USER_GENDER") + "'" + "," +
                "'" +  getData.get("USER_CONFIRM_NUM") + "'" + "," +
                "'" +  "Y" + "'" + "," +
                "'" +  getData.get("USER_TOKEN") + "'" + "," +
                "NOW()" +
                ")" ;

        JSONObject returnJson = WebConnection.insertQuery(context, query);

        return returnJson;


    }




    public static JSONObject updateContract(Context context, HashMap<String, String> getData) throws Exception
    {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        String query = "";
        //쿼리를 여기서 만든다.

        query += "UPDATE " + SCHEMA + "CT_TB_CONTRACT_HIST SET ";

        query += "GENDER_PLACE"   +  " = " + "'" + getData.get("GENDER_PLACE")    +  "', ";
        query += "B_ACCOUNT_PLACE"   +  " = " + "'" + getData.get("B_ACCOUNT_PLACE")    +  "', ";
        query += "B_NM_PLACE"   +  " = " + "'" + getData.get("B_NM_PLACE")    +  "', ";
        query += "B_ACT_NM_PLACE"   +  " = " + "'" + getData.get("B_ACT_NM_PLACE")    +  "', ";
        query += "JUMIN_PLACE"   +  " = " + "'" + getData.get("JUMIN_PLACE")    +  "', ";
        query += "ADRS_PLACE"   +  " = " + "'" + getData.get("ADRS_PLACE")    +  "', ";
        query += "EMAIL"   +  " = " + "'" + getData.get("EMAIL")    +  "', ";
        query += "SEND_DT"   +  " = " + "'" + currentDateandTime    +  "', ";
        query += "SEND_YN"   +  " = " + "'" + "Y"    +  "' ";


        query += "WHERE SEQ = '" + getData.get("SEQ")  + "' ";

        JSONObject returnJson = WebConnection.updateQuery(context, query);




        return returnJson;


    }



    //B1_USER_INFO 에 저장
    public static JSONObject updateUserInfo(Context context, HashMap<String, String> getData) throws Exception
    {


        String query = "";
        //쿼리를 여기서 만든다.

        query += "UPDATE " + SCHEMA + "OW_USER_INFO SET ";
        query += "USER_PHONE_NUM"   +  " = " + "'" + getData.get("USER_PHONE_NUM")    +  "', ";
        if(getData.containsKey("USER_CONFIRM_NUM"))
        {
            query += "USER_CONFIRM_NUM"   +  " = " + "'" + getData.get("USER_CONFIRM_NUM")    +  "', ";
        }
        query += "USER_NAME"   +  " = " + "'" + getData.get("USER_NAME")    +  "', " +
                "USER_BIRTH_DAY"   +  " = " + "'" + getData.get("USER_BIRTH_DAY")    +  "', "  +
                "USER_GENDER"   +  " = " + "'" + getData.get("USER_GENDER")    +  "' "  +
                "WHERE USER_ID = '" + getData.get("USER_ID")  + "' ";

        JSONObject returnJson = WebConnection.updateQuery(context, query);

        return returnJson;


    }






    public static JSONObject LoginSelectCheck(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "SELECT * " +
                "FROM " + SCHEMA + "OW_USER_INFO " +
                "WHERE USER_ID  = '" + sendData.get("USER_ID") + "' " +
                "AND USER_PASS = '" + sendData.get("USER_PASS") + "' " +
                "LIMIT 0, 1 ";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;


    }




    public static JSONObject forgotEmailNameCheck(Context context, HashMap<String, String> sendData) throws Exception
    {


        String query = "SELECT COUNT(USER_ID) AS CHECK_EMAIL " +
                "FROM " + SCHEMA + "OW_USER_INFO " +
                "WHERE USER_ID  = '" + sendData.get("USER_ID") + "' " +
                "LIMIT 0, 1 ";

        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }







    public static JSONObject forgotUpdateNewPass(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

        query = "UPDATE " + SCHEMA + "OW_USER_INFO SET " +
                "USER_PASS"   +  " = " +      "'" + sendData.get("USER_PASS")    +  "' "  +
                "WHERE USER_ID = '" + sendData.get("USER_ID")  + "' ";



        JSONObject returnJson = WebConnection.updateQuery(context, query);

        return returnJson;

    }




    public static JSONObject MainHomeGetPlannerData(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

        query = "SELECT * FROM " + SCHEMA + "OW_PLANER_HEAD where PLAN_USE_YN = 'Y' " +
                "ORDER BY PLAN_CLICK_NUM DESC " +
                "LIMIT " + sendData.get("START") + ", " + sendData.get("NUM");


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }



    public static JSONObject MainHomeAdData(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.


//        SELECT AD_IMAGE_URL, AD_URL
//        FROM " + SCHEMA + "OW_PORT_AD
//        WHERE USE_YN = 'Y'
//        ORDER BY AD_LEVEL ASC;


        query = "SELECT AD_IMAGE_PATH, AD_IMAGE_NAME, AD_URL " +
                "FROM " + SCHEMA + "OW_PORT_AD " +
                "WHERE USE_YN = 'Y' " +
                "ORDER BY AD_LEVEL ASC ";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }



    public static JSONObject SearchRecommendWords(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.


//        SELECT SEARCH_WORD FROM " + SCHEMA + "OW_SEARCH
//        WHERE SEARCH_RECO_YN = 'Y';


        query = "SELECT SEARCH_WORD FROM " + SCHEMA + "OW_SEARCH " +
                "WHERE SEARCH_RECO_YN = 'Y' ";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }









    public static JSONObject MainBestCatagoryGetPlannerData(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

//        SELECT * FROM " + SCHEMA + "OW_PLANER_HEAD A
//        LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_TAG B ON B.PLAN_NUM = A.PLAN_NUM
//        where A.PLAN_USE_YN = 'Y'
//        AND B.PLAN_TAG = '#테그1'
//        ORDER BY PLAN_CLICK_NUM DESC
//        LIMIT 0, 5

        query = "SELECT * FROM " + SCHEMA + "OW_PLANER_HEAD A " +
                "LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_TAG B ON B.PLAN_NUM = A.PLAN_NUM " +
                "LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_IMAGE C ON C.PLAN_NUM = A.PLAN_NUM " +
                "where A.PLAN_USE_YN = 'Y' " +
                "AND B.PLAN_TAG = '" + sendData.get("PLAN_TAG") + "' " +
                "ORDER BY PLAN_CLICK_NUM DESC " +
                "LIMIT " + sendData.get("START") + ", " + sendData.get("NUM");


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }



    public static JSONObject MainBestGetCatagory(Context context) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

//        SELECT PLAN_TAG FROM " + SCHEMA + "OW_PLAN_TAG
//        WHERE BEST_USE_YN = 'Y'


//        SELECT PLAN_TAG FROM " + SCHEMA + "OW_PLAN_TAG
//        WHERE BEST_USE_YN = 'Y'
//        GROUP BY PLAN_TAG
//        ORDER BY  RAND()
//        LIMIT 0, 5


        query = "SELECT PLAN_TAG FROM " + SCHEMA + "OW_PLAN_TAG " +
                "WHERE BEST_USE_YN = 'Y' " +
                "GROUP BY PLAN_TAG " +
                "ORDER BY  RAND() " +
                "LIMIT 0, 5 ";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }




    public static JSONObject MainHomeGetPortfolioData(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

//        SELECT A.*
//        , C.IMAGE_NAME AS P_IMAGE_NAME
//            , C.IMAGE_PATH AS P_IMAGE_PATH
//        FROM " + SCHEMA + "OW_PORT_HEAD A
//        LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_IMAGE C ON C.PLAN_NUM = A.PLAN_NUM
//        where A.PORT_USE_YN = 'Y'
//        GROUP BY A.PORT_NUM
//        ORDER BY A.PORT_NUM ASC
//        LIMIT 0, 10

        String USER_ID = sendData.get("USER_ID");


        query += "SELECT A.* , B.*" ;
        query +=", C.IMAGE_NAME AS P_IMAGE_NAME " ;
        query +=", C.IMAGE_PATH AS P_IMAGE_PATH " ;
        query +=", E.IMAGE_NAME " ;
        query +=", E.IMAGE_PATH " ;
        if(USER_ID != "")
        {
            query += ", D.LIKE_YN ";
        }
        query +="FROM " + SCHEMA + "OW_PORT_HEAD A " ;
        query +="LEFT OUTER JOIN " + SCHEMA + "OW_PLANER_HEAD B ON B.PLAN_NUM = A.PLAN_NUM " ;
        query +="LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_IMAGE C ON C.PLAN_NUM = A.PLAN_NUM " ;
        query +="LEFT OUTER JOIN " + SCHEMA + "OW_IMAGE E ON E.PORT_NUM = A.PORT_NUM AND E.REPR = 'Y' " ;

        if(USER_ID != "")
        {
            query += "LEFT OUTER JOIN " + SCHEMA + "OW_PORT_LIKE D ON D.PORT_NUM = A.PORT_NUM AND D.USER_ID = '" + sendData.get("USER_ID") + "' " ;
        }
        query +="where A.PORT_USE_YN = 'Y' " ;
        query +="GROUP BY A.PORT_NUM " ;
        query +="ORDER BY A.PORT_NUM ASC " ;
        query +="LIMIT " + sendData.get("START") + ", " + sendData.get("NUM");


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }









    public static JSONObject MainHomeGetRepreImage(Context context, String PlannerNum) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

        //select * from " + SCHEMA + "OW_IMAGE where IMAGE_USE_YN = 'Y' AND REPR = 'Y' AND PLAN_NUM =
        query = "select * from " + SCHEMA + "OW_IMAGE where IMAGE_USE_YN = 'Y' AND REPR = 'Y' " +
                "AND PORT_NUM = '" + PlannerNum + "' " ;


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }



    public static JSONObject MainHomeDetailImages(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

        //select * from " + SCHEMA + "OW_IMAGE where IMAGE_USE_YN = 'Y' AND REPR = 'Y' AND PLAN_NUM =
        query = "select * from " + SCHEMA + "OW_IMAGE where IMAGE_USE_YN = 'Y' " +
                "AND PORT_NUM = '" + sendData.get("PORT_NUM") + "' " +
                "ORDER BY IMAGE_NAME" ;


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }

    public static JSONObject MainHomeDetailLocation(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

        //SELECT * FROM " + SCHEMA + "OW_PLAN_LOCATION;
        query = "SELECT * FROM " + SCHEMA + "OW_PLAN_LOCATION " +
                "WHERE PLAN_NUM = '" + sendData.get("PLAN_NUM") + "' ";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }



    public static JSONObject MainHomePortClick(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

//        UPDATE " + SCHEMA + "OW_PORT_HEAD
//            SET
//        PORT_CLICK_NUM = PORT_CLICK_NUM+1
//        where PORT_NUM = 'PF00002';

        query = "UPDATE " + SCHEMA + "OW_PORT_HEAD SET " +
                "PORT_CLICK_NUM = PORT_CLICK_NUM+1 " +
                "WHERE PORT_NUM = '" + sendData.get("PORT_NUM") + "' ";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }



    public static JSONObject MainBestPlanClick(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.


//        UPDATE " + SCHEMA + "OW_PLANER_HEAD
//            SET
//        PLAN_CLICK_NUM = PLAN_CLICK_NUM+1
//        where PLAN_NUM = 'PL00005';

        query = "UPDATE " + SCHEMA + "OW_PLANER_HEAD SET " +
                "PLAN_CLICK_NUM = PLAN_CLICK_NUM+1 " +
                "where PLAN_NUM  = '" + sendData.get("PLAN_NUM") + "' ";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }






    public static JSONObject MainBestGetPlannerImage(Context context, String PlannerNum) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

        //select * from " + SCHEMA + "OW_IMAGE where IMAGE_USE_YN = 'Y' AND REPR = 'Y' AND PLAN_NUM =
        query = "select * from " + SCHEMA + "OW_PLAN_IMAGE where USE_YN = 'Y' " +
                "AND PLAN_NUM = '" + PlannerNum + "' " ;


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }



    public static JSONObject MainBestPortfolioGetPortfolioData(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

//        SELECT * FROM " + SCHEMA + "OW_PORT_HEAD A
//        LEFT OUTER JOIN " + SCHEMA + "OW_PLANER_HEAD B ON B.PLAN_NUM = A.PLAN_NUM
//        where A.PORT_USE_YN = 'Y'
//        AND A.PLAN_NUM = 'PL00000'
//        ORDER BY A.PORT_NUM ASC

        String USER_ID = sendData.get("USER_ID");


        query += "SELECT A.*, B.* ";
        query += ", C.IMAGE_NAME AS P_IMAGE_NAME ";
        query += ", C.IMAGE_PATH AS P_IMAGE_PATH " ;
        query += ", E.IMAGE_NAME " ;
        query += ", E.IMAGE_PATH " ;
        if(USER_ID != "")
        {
            query += ", D.LIKE_YN ";
        }
        query += "FROM " + SCHEMA + "OW_PORT_HEAD A " ;
        query += "LEFT OUTER JOIN " + SCHEMA + "OW_PLANER_HEAD B ON B.PLAN_NUM = A.PLAN_NUM " ;
        query += "LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_IMAGE C ON C.PLAN_NUM = A.PLAN_NUM " ;
        query +="LEFT OUTER JOIN " + SCHEMA + "OW_IMAGE E ON E.PORT_NUM = A.PORT_NUM AND E.REPR = 'Y' " ;
        if(USER_ID != "")
        {
            query += "LEFT OUTER JOIN " + SCHEMA + "OW_PORT_LIKE D ON D.PORT_NUM = A.PORT_NUM AND D.USER_ID = '" + sendData.get("USER_ID") + "' " ;
        }
        query += "where A.PORT_USE_YN = 'Y' " ;
        query += " AND A.PLAN_NUM = '" + sendData.get("PLAN_NUM") + "' " ;
        query += " GROUP BY A.PORT_NUM " ;
        query += " ORDER BY A.PORT_NUM ASC ";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }



    public static JSONObject MainBestGetPlannerLocation(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

//    select * from " + SCHEMA + "OW_PLAN_LOCATION
//    WHERE PLAN_NUM = 'PL00000'

        query = "select * from " + SCHEMA + "OW_PLAN_LOCATION " +
                "WHERE PLAN_NUM = '" + sendData.get("PLAN_NUM") + "' " ;


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }







    public static JSONObject MainBestGetCoupleHistory(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.


//        SELECT * FROM " + SCHEMA + "OW_PLAN_COUPL
//        WHERE PLAN_NUM = 'PL00000';

        query = "SELECT * FROM " + SCHEMA + "OW_PLAN_COUPL " +
                " WHERE PLAN_NUM = '" + sendData.get("PLAN_NUM") + "' " ;


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }



    public static JSONObject MainRecommendGetPlannerData(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.


//        SELECT * FROM " + SCHEMA + "OW_PLANER_HEAD
//        WHERE RECOM_YN = 'Y'
//        ORDER BY RAND()
//        LIMIT 6


        query = "SELECT * FROM " + SCHEMA + "OW_PLANER_HEAD " +
                "WHERE RECOM_YN = 'Y' " +
                "ORDER BY PLAN_CLASS ASC, RAND() " +
                "LIMIT " + "0" + ", " + "6";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }



    public static JSONObject MainRecommendGetTag(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.


//        SELECT C.PORT_TAG, COUNT(*) c from " + SCHEMA + "OW_PORT_LIKE A
//        LEFT OUTER JOIN " + SCHEMA + "OW_PORT_HEAD B ON B.PORT_NUM = A.PORT_NUM
//        LEFT OUTER JOIN " + SCHEMA + "OW_PORT_TAG C ON C.PORT_NUM = A.PORT_NUM
//        WHERE A.USER_ID = 'tarpboy@naver.com'
//        GROUP BY C.PORT_TAG
//        HAVING c > 1
//        ORDER BY c DESC



        query = "SELECT C.PORT_TAG, COUNT(*) TAG_COUNT from " + SCHEMA + "OW_PORT_LIKE A " +
                "LEFT OUTER JOIN " + SCHEMA + "OW_PORT_HEAD B ON B.PORT_NUM = A.PORT_NUM " +
                "LEFT OUTER JOIN " + SCHEMA + "OW_PORT_TAG C ON C.PORT_NUM = A.PORT_NUM " +
                "WHERE A.USER_ID = '" + sendData.get("USER_ID") + "' " +
                "GROUP BY C.PORT_TAG " +
                "HAVING TAG_COUNT > 1 " +
                "ORDER BY TAG_COUNT DESC " +
                "LIMIT 0, 5 " ;


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }



    public static JSONObject MainRecommendGetTagPlaner(Context context, ArrayList<String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.


//        select A.PLAN_NUM, COUNT(*) TAG_COUNT, B.PLAN_CLASS from " + SCHEMA + "OW_PLAN_TAG A
//        LEFT OUTER JOIN " + SCHEMA + "OW_PLANER_HEAD B ON B.PLAN_NUM = A.PLAN_NUM
//        where PLAN_TAG LIKE '%결혼준비%'
//        OR PLAN_TAG LIKE '%스드메%'
//        OR PLAN_TAG LIKE '%신혼여행%'
//        OR PLAN_TAG LIKE '%제휴업%'
//        OR PLAN_TAG LIKE '%신혼집%'
//        GROUP BY PLAN_NUM
//        HAVING TAG_COUNT > 1
//        ORDER BY TAG_COUNT DESC , B.PLAN_CLASS ASC


        query += "select A.PLAN_NUM, COUNT(*) TAG_COUNT ";
        query += ", B.PLAN_NAME ";
        query += ", B.PLAN_CLASS ";
        query += ", C.IMAGE_PATH ";
        query += ", C.IMAGE_NAME ";
        query += "from " + SCHEMA + "OW_PLAN_TAG A ";
        query += "LEFT OUTER JOIN " + SCHEMA + "OW_PLANER_HEAD B ON B.PLAN_NUM = A.PLAN_NUM ";
        query += "LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_IMAGE C ON C.PLAN_NUM = A.PLAN_NUM ";
        query += "where PLAN_TAG LIKE '%" + sendData.get(0).replace("#", "") + "%' ";
        for(int i = sendData.size()-1 ; i > 0 ; i-- )
        {
            query += "OR PLAN_TAG LIKE '%" + sendData.get(i).replace("#", "") + "%' ";
        }
        query += "GROUP BY PLAN_NUM ";
        if(sendData.size() == 1)
        {
            query += "HAVING TAG_COUNT > 0 ";
        }
        else
        {
            query += "HAVING TAG_COUNT > 1 ";
        }
        query += "ORDER BY TAG_COUNT DESC , B.PLAN_CLASS ASC ";
        query += "LIMIT 0, 5 ";




        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }




    public static JSONObject MainRecommendGetPlanerTags(Context context, String PlannerNum) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.


//        SELECT * FROM " + SCHEMA + "OW_PLAN_TAG
//        WHERE USE_YN = 'Y'
//        AND PLAN_NUM = 'PL00001'


        query = "SELECT PLAN_TAG FROM " + SCHEMA + "OW_PLAN_TAG " +
                "WHERE USE_YN = 'Y' " +
                "AND PLAN_NUM = '" + PlannerNum + "' ";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }





    public static JSONObject otherProfileGetUserData(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

        //select * from " + SCHEMA + "OW_IMAGE where IMAGE_USE_YN = 'Y' AND REPR = 'Y' AND PLAN_NUM =
        query = "select * from " + SCHEMA + "OW_USER_INFO where USER_USE_YN = 'Y' " +
                "AND USER_ID = '" + sendData.get("USER_ID") + "' ";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }





    public static JSONObject PlannerDetailTag(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

//        query = "SELECT PLAN_TAG FROM " + SCHEMA + "OW_PLAN_TAG WHERE USE_YN = 'Y' " +
//                "AND PLAN_NUM = '" + sendData.get("PLAN_NUM") + "' ";

        query = "SELECT PORT_TAG FROM " + SCHEMA + "OW_PORT_TAG WHERE USE_YN = 'Y' " +
                "AND PORT_NUM = '" + sendData.get("PORT_NUM") + "' ";




        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }








    public static JSONObject PortDetailContents(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

//      SELECT * FROM " + SCHEMA + "OW_PLAN_CONT WHERE USE_YN = 'Y' AND PLAN_NUM = 'PL00001' ;
        query = "SELECT * FROM " + SCHEMA + "OW_PORT_CONT WHERE USE_YN = 'Y' " +
                "AND PORT_NUM = '" + sendData.get("PORT_NUM") + "' ";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }



    public static JSONObject InsertConnectionInfo(Context context, HashMap<String, String> getData) throws Exception
    {


        String query = "";
        //쿼리를 여기서 만든다.

        query =  "INSERT INTO " + SCHEMA + "OW_CONN_INFO " +

                "(" +
                "DATE_TIME" + "," +
                "USER_ID" + "," +
                "DEVICE_UUID" + "," +
                "DEVICE_MODEL" + "," +
                "DEVICE_SDK" + "," +
                "DEVICE_VER" +
                ") " +

                "VALUES " +

                "(" +
                "NOW()" + "," +
                "'" +  getData.get("USER_ID") + "'" + "," +
                "'" +  getData.get("DEVICE_UUID") + "'" + "," +
                "'" +  getData.get("DEVICE_MODEL") + "'" + "," +
                "'" +  getData.get("DEVICE_SDK") + "'" + "," +
                "'" +  getData.get("DEVICE_VER") + "'" +
                ")" ;



        JSONObject returnJson = WebConnection.insertQuery(context, query);

        return returnJson;


    }



    public static JSONObject InsertSearchInfo(Context context, HashMap<String, String> getData) throws Exception
    {


        String query = "";
        //쿼리를 여기서 만든다.

        query =  "INSERT INTO " + SCHEMA + "OW_SEARCH " +

                "(" +
                "SEARCH_WORD" + "," +
                "DEVICE_UUID" + "," +
                "DATE_TIME" +
                ") " +

                "VALUES " +

                "(" +
                "'" +  getData.get("SEARCH_WORD") + "'" + "," +
                "'" +  getData.get("DEVICE_UUID") + "'" + "," +
                "NOW()" +
                ")" ;



        JSONObject returnJson = WebConnection.insertQuery(context, query);

        return returnJson;


    }


    public static JSONObject InsertPortLike(Context context, HashMap<String, String> getData) throws Exception
    {


        String query = "";
        //쿼리를 여기서 만든다.

        query =  "INSERT INTO " + SCHEMA + "OW_PORT_LIKE " +
                "(" +
                "PORT_NUM" + ", " +
                "LIKE_YN" + ", " +
                "USER_ID" +
                ") " +

                "VALUES " +

                "(" +
                "'" +  getData.get("PORT_NUM") + "' " + "," +
                "'Y' " + "," +
                "'" +  getData.get("USER_ID") + "' " +
                ")" ;

        JSONObject returnJson = WebConnection.insertQuery(context, query);

        return returnJson;


    }



    public static JSONObject DeletePortLike(Context context, HashMap<String, String> getData) throws Exception
    {


        String query = "";
        //쿼리를 여기서 만든다.

        query =  "DELETE FROM " + SCHEMA + "OW_PORT_LIKE " +
                "WHERE PORT_NUM = '" + getData.get("PORT_NUM") + "' " +
                "AND USER_ID = '" + getData.get("USER_ID") + "' " ;


        JSONObject returnJson = WebConnection.insertQuery(context, query);

        return returnJson;


    }






    public static JSONObject SearPortData(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.



//        SELECT A.* , B.*
//            , C.IMAGE_NAME AS P_IMAGE_NAME
//        , C.IMAGE_PATH AS P_IMAGE_PATH
//            , E.IMAGE_NAME
//            , E.IMAGE_PATH
//            , D.LIKE_YN
//            , F.PORT_TAG
//        FROM " + SCHEMA + "OW_PORT_HEAD A
//        LEFT OUTER JOIN " + SCHEMA + "OW_PLANER_HEAD B ON B.PLAN_NUM = A.PLAN_NUM
//        LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_IMAGE C ON C.PLAN_NUM = A.PLAN_NUM
//        LEFT OUTER JOIN " + SCHEMA + "OW_IMAGE E ON E.PLAN_NUM = A.PLAN_NUM AND E.REPR = 'Y'
//        LEFT OUTER JOIN " + SCHEMA + "OW_PORT_LIKE D ON D.PORT_NUM = A.PORT_NUM AND D.USER_ID = 'tarpboy@naver.com'
//        LEFT OUTER JOIN " + SCHEMA + "OW_PORT_TAG F ON F.PORT_NUM = A.PORT_NUM
//        where A.PORT_USE_YN = 'Y'
//        AND F.PORT_TAG = '#결혼준비'
//        GROUP BY A.PORT_NUM
//        ORDER BY A.PORT_NUM ASC


        String USER_ID = sendData.get("USER_ID");


        query += "SELECT A.* , B.* " ;
        query +=", C.IMAGE_NAME AS P_IMAGE_NAME " ;
        query +=", C.IMAGE_PATH AS P_IMAGE_PATH " ;
        query +=", E.IMAGE_NAME " ;
        query +=", E.IMAGE_PATH " ;
        if(USER_ID != "")
        {
            query += ", D.LIKE_YN ";
        }
        query +="FROM " + SCHEMA + "OW_PORT_HEAD A " ;
        query +="LEFT OUTER JOIN " + SCHEMA + "OW_PLANER_HEAD B ON B.PLAN_NUM = A.PLAN_NUM " ;
        query +="LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_IMAGE C ON C.PLAN_NUM = A.PLAN_NUM " ;
        query +="LEFT OUTER JOIN " + SCHEMA + "OW_IMAGE E ON E.PORT_NUM = A.PORT_NUM AND E.REPR = 'Y' " ;
        query +="LEFT OUTER JOIN " + SCHEMA + "OW_PORT_TAG F ON F.PORT_NUM = A.PORT_NUM " ;
        if(USER_ID != "")
        {
            query += "LEFT OUTER JOIN " + SCHEMA + "OW_PORT_LIKE D ON D.PORT_NUM = A.PORT_NUM AND D.USER_ID = '" + sendData.get("USER_ID") + "' " ;
        }
        query +="where A.PORT_USE_YN = 'Y' " ;
        query +="AND F.PORT_TAG = '#" + sendData.get("SEARCH_WORD") + "' " ;
        query +="GROUP BY A.PORT_NUM " ;
        query +="ORDER BY A.PORT_NUM ASC ";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }





    public static JSONObject SearchPlanData(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.


//        SELECT A.*, B.*
//            FROM " + SCHEMA + "OW_PLANER_HEAD A
//        LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_IMAGE B ON A.PLAN_NUM = B.PLAN_NUM
//        LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_TAG C ON A.PLAN_NUM = C.PLAN_NUM
//        where PLAN_USE_YN = 'Y'
//        AND C.PLAN_TAG = '#결혼준비'
//        ORDER BY PLAN_CLICK_NUM DESC

        query += "SELECT A.*, B.* " ;
        query += "FROM " + SCHEMA + "OW_PLANER_HEAD A " ;
        query += "LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_IMAGE B ON A.PLAN_NUM = B.PLAN_NUM " ;
        query += "LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_TAG C ON A.PLAN_NUM = C.PLAN_NUM " ;
        query += "where A.PLAN_USE_YN = 'Y' " ;
        query += "AND C.PLAN_TAG = '#" + sendData.get("SEARCH_WORD") + "' " ;
        query += "ORDER BY A.PLAN_CLICK_NUM DESC , A.PLAN_NUM DESC" ;



        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }





    public static JSONObject SearchImageData(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

//        select A.*
//        from " + SCHEMA + "OW_IMAGE A
//        LEFT OUTER JOIN " + SCHEMA + "OW_IMAGE_TAG B ON B.IMAGE_NAME = A.IMAGE_NAME
//        where A.IMAGE_USE_YN = 'Y'
//        AND B.IMAGE_TAG = '#결혼준비'


        query += "select A.* " ;
        query += "FROM " + SCHEMA + "OW_IMAGE A " ;
        query += "LEFT OUTER JOIN " + SCHEMA + "OW_IMAGE_TAG B ON B.IMAGE_NAME = A.IMAGE_NAME " ;
        query += "where A.IMAGE_USE_YN = 'Y' " ;
        query += "AND B.IMAGE_TAG = '#" + sendData.get("SEARCH_WORD") + "' " ;


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }



    public static JSONObject FilterGetLocation(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

//    SELECT PLAN_LOC FROM " + SCHEMA + "OW_PLAN_LOCATION
//    GROUP BY PLAN_LOC;

        query += "SELECT PLAN_LOC FROM " + SCHEMA + "OW_PLAN_LOCATION " ;
        query += "GROUP BY PLAN_LOC " ;

        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }





    public static JSONObject FilterGetStyle(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

//    SELECT IMAGE_TAG FROM " + SCHEMA + "OW_IMAGE_TAG
//    GROUP BY IMAGE_TAG
//    ORDER BY  RAND()
//    LIMIT 0, 20

        query += "SELECT PORT_TAG FROM " + SCHEMA + "OW_PORT_TAG " ;
        query += "GROUP BY PORT_TAG " ;
        query += "ORDER BY  RAND() " ;
        query += "LIMIT 0, 20 " ;

        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }





    public static JSONObject FilterPortData(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.


//    SELECT A.* , B.*
//        , C.IMAGE_NAME AS P_IMAGE_NAME
//    , C.IMAGE_PATH AS P_IMAGE_PATH
//    , E.IMAGE_NAME
//    , E.IMAGE_PATH
//    , D.LIKE_YN
//    , F.PLAN_LOC
//    FROM " + SCHEMA + "OW_PORT_HEAD A
//    LEFT OUTER JOIN " + SCHEMA + "OW_PLANER_HEAD B ON B.PLAN_NUM = A.PLAN_NUM
//    LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_IMAGE C ON C.PLAN_NUM = A.PLAN_NUM
//    LEFT OUTER JOIN " + SCHEMA + "OW_IMAGE E ON E.PLAN_NUM = A.PLAN_NUM AND E.REPR = 'Y'
//    LEFT OUTER JOIN " + SCHEMA + "OW_PORT_LIKE D ON D.PORT_NUM = A.PORT_NUM AND D.USER_ID = 'tarpboy@naver.com'
//    LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_LOCATION F ON F.PLAN_NUM = A.PLAN_NUM
//    LEFT OUTER JOIN " + SCHEMA + "OW_PORT_TAG G1 ON G1.PORT_NUM = A.PORT_NUM
//    LEFT OUTER JOIN " + SCHEMA + "OW_PORT_TAG G2 ON G2.PORT_NUM = A.PORT_NUM
//    LEFT OUTER JOIN " + SCHEMA + "OW_PORT_TAG G3 ON G3.PORT_NUM = A.PORT_NUM
//    where A.PORT_USE_YN = 'Y'
//    AND F.PLAN_LOC = '서울'
//    AND G1.PORT_TAG = '#결혼준비'
//    AND G2.PORT_TAG = '#스드메'
//    AND G3.PORT_TAG = '#신혼여행'
//    GROUP BY A.PORT_NUM, F.PLAN_LOC
//    ORDER BY A.PORT_NUM ASC



        String USER_ID = sendData.get("USER_ID");
        String LOC = sendData.get("LOC");
        String TAG1 = sendData.get("TAG1");
        String TAG2 = sendData.get("TAG2");
        String TAG3 = sendData.get("TAG3");
        String MINM = sendData.get("MINM");
        String MAXM = sendData.get("MAXM");



        query += "SELECT A.* , B.* " ;
        query +=", C.IMAGE_NAME AS P_IMAGE_NAME " ;
        query +=", C.IMAGE_PATH AS P_IMAGE_PATH " ;
        query +=", E.IMAGE_NAME " ;
        query +=", E.IMAGE_PATH " ;
        if(LOC != "")
        {
            query +=", F.PLAN_LOC " ;
        }
        if(USER_ID != "")
        {
            query += ", D.LIKE_YN ";
        }
        query +="FROM " + SCHEMA + "OW_PORT_HEAD A " ;
        query +="LEFT OUTER JOIN " + SCHEMA + "OW_PLANER_HEAD B ON B.PLAN_NUM = A.PLAN_NUM " ;
        query +="LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_IMAGE C ON C.PLAN_NUM = A.PLAN_NUM " ;
        query +="LEFT OUTER JOIN " + SCHEMA + "OW_IMAGE E ON E.PORT_NUM = A.PORT_NUM AND E.REPR = 'Y' " ;
        if(LOC != "")
        {
            query +="LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_LOCATION F ON F.PLAN_NUM = A.PLAN_NUM " ;
        }
        if(TAG1 != "")
        {
            query +="LEFT OUTER JOIN " + SCHEMA + "OW_PORT_TAG G1 ON G1.PORT_NUM = A.PORT_NUM " ;
        }
        if(TAG2 != "")
        {
            query +="LEFT OUTER JOIN " + SCHEMA + "OW_PORT_TAG G2 ON G2.PORT_NUM = A.PORT_NUM " ;
        }
        if(TAG3 != "")
        {
            query +="LEFT OUTER JOIN " + SCHEMA + "OW_PORT_TAG G3 ON G3.PORT_NUM = A.PORT_NUM " ;
        }
        if(USER_ID != "")
        {
            query += "LEFT OUTER JOIN " + SCHEMA + "OW_PORT_LIKE D ON D.PORT_NUM = A.PORT_NUM AND D.USER_ID = '" + sendData.get("USER_ID") + "' " ;
        }
        query +="where A.PORT_USE_YN = 'Y' " ;
        query +="AND A.PORT_PRICE BETWEEN " + MINM + " AND " + MAXM + " " ;
        if(LOC != "")
        {
            query +="AND F.PLAN_LOC = '" + LOC + "' " ;
        }
        if(TAG1 != "")
        {
            query +="AND G1.PORT_TAG = '" + TAG1 + "' " ;
        }
        if(TAG2 != "")
        {
            query +="AND G2.PORT_TAG = '" + TAG2 + "' " ;
        }
        if(TAG3 != "")
        {
            query +="AND G3.PORT_TAG = '" + TAG3 + "' " ;
        }


        query +="GROUP BY A.PORT_NUM " ;
        if(LOC != "")
        {
            query +=", F.PLAN_LOC " ;
        }
        query +="ORDER BY A.PORT_NUM ASC ";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }




    public static JSONObject FilterPlanData(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.



//        SELECT A.*, B.*
//            FROM " + SCHEMA + "OW_PLANER_HEAD A
//        LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_IMAGE B ON A.PLAN_NUM = B.PLAN_NUM
//        LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_LOCATION F ON F.PLAN_NUM = A.PLAN_NUM
//        LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_TAG G1 ON G1.PLAN_NUM = A.PLAN_NUM
//        LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_TAG G2 ON G2.PLAN_NUM = A.PLAN_NUM
//        LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_TAG G3 ON G3.PLAN_NUM = A.PLAN_NUM
//        where PLAN_USE_YN = 'Y'
//        AND F.PLAN_LOC = '서울'
//        AND G1.PLAN_TAG = '#결혼준비'
//        AND G2.PLAN_TAG = '#스드메'
//        AND G3.PLAN_TAG = '#신혼여행'
//        GROUP BY A.PLAN_NUM
//        ORDER BY PLAN_CLICK_NUM DESC


        String USER_ID = sendData.get("USER_ID");
        String LOC = sendData.get("LOC");
        String TAG1 = sendData.get("TAG1");
        String TAG2 = sendData.get("TAG2");
        String TAG3 = sendData.get("TAG3");
        String MINM = sendData.get("MINM");
        String MAXM = sendData.get("MAXM");


        query += "SELECT A.*, B.* " ;
        query += "FROM " + SCHEMA + "OW_PLANER_HEAD A " ;
        query += "LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_IMAGE B ON A.PLAN_NUM = B.PLAN_NUM " ;
        if(LOC != "")
        {
            query +="LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_LOCATION F ON F.PLAN_NUM = A.PLAN_NUM " ;
        }
        if(TAG1 != "")
        {
            query +="LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_TAG G1 ON G1.PLAN_NUM = A.PLAN_NUM " ;
        }
        if(TAG2 != "")
        {
            query +="LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_TAG G2 ON G2.PLAN_NUM = A.PLAN_NUM " ;
        }
        if(TAG3 != "")
        {
            query +="LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_TAG G3 ON G3.PLAN_NUM = A.PLAN_NUM " ;
        }
        query += "where A.PLAN_USE_YN = 'Y' " ;
        query +="AND A.PLAN_PRICE BETWEEN " + MINM + " AND " + MAXM + " " ;

        if(LOC != "")
        {
            query +="AND F.PLAN_LOC = '" + LOC + "' " ;
        }
        if(TAG1 != "")
        {
            query +="AND G1.PLAN_TAG = '" + TAG1 + "' " ;
        }
        if(TAG2 != "")
        {
            query +="AND G2.PLAN_TAG = '" + TAG2 + "' " ;
        }
        if(TAG3 != "")
        {
            query +="AND G3.PLAN_TAG = '" + TAG3 + "' " ;
        }

        query += "GROUP BY A.PLAN_NUM " ;
        query += "ORDER BY A.PLAN_CLICK_NUM DESC , A.PLAN_NUM DESC" ;



        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }




    public static JSONObject FilterImageData(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.


        String USER_ID = sendData.get("USER_ID");
        String LOC = sendData.get("LOC");
        String TAG1 = sendData.get("TAG1");
        String TAG2 = sendData.get("TAG2");
        String TAG3 = sendData.get("TAG3");
        String MINM = sendData.get("MINM");
        String MAXM = sendData.get("MAXM");
        String STUDIO = sendData.get("STUDIO");
        String DRESS = sendData.get("DRESS");
        String MAKEUP = sendData.get("MAKEUP");
        String HOLE = sendData.get("HOLE");




//        SELECT * FROM " + SCHEMA + "OW_IMAGE OI
//        LEFT OUTER JOIN " + SCHEMA + "OW_IMAGE_TAG OIT ON OIT.IMAGE_NAME = OI.IMAGE_NAME
//        where OI.PORT_NUM IN (
//        SELECT A.PORT_NUM
//        FROM " + SCHEMA + "OW_PORT_HEAD A
//        LEFT OUTER JOIN " + SCHEMA + "OW_PLANER_HEAD B ON B.PLAN_NUM = A.PLAN_NUM
//        LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_IMAGE C ON C.PLAN_NUM = A.PLAN_NUM
//        LEFT OUTER JOIN " + SCHEMA + "OW_IMAGE E ON E.PLAN_NUM = A.PLAN_NUM AND E.REPR = 'Y'
//        LEFT OUTER JOIN " + SCHEMA + "OW_PORT_TAG G1 ON G1.PORT_NUM = A.PORT_NUM
//        LEFT OUTER JOIN " + SCHEMA + "OW_PORT_LIKE D ON D.PORT_NUM = A.PORT_NUM AND D.USER_ID = 'tarpboy@naver.com'
//        where A.PORT_USE_YN = 'Y'
//        AND A.PORT_PRICE BETWEEN 50 AND 190
//        AND G1.PORT_TAG = '#결혼준비'
//        GROUP BY A.PORT_NUM
//        ORDER BY A.PORT_NUM ASC
//        )
//        AND OIT.IMAGE_TAG = '#스드메'






        query += "SELECT * FROM " + SCHEMA + "OW_IMAGE OI " ;
        if(STUDIO != "" )
        {
            query += "LEFT OUTER JOIN " + SCHEMA + "OW_IMAGE_TAG OIT1 ON OIT1.IMAGE_NAME = OI.IMAGE_NAME " ;
        }
        if(DRESS != "" )
        {
            query += "LEFT OUTER JOIN " + SCHEMA + "OW_IMAGE_TAG OIT2 ON OIT2.IMAGE_NAME = OI.IMAGE_NAME " ;
        }
        if(MAKEUP != "" )
        {
            query += "LEFT OUTER JOIN " + SCHEMA + "OW_IMAGE_TAG OIT3 ON OIT3.IMAGE_NAME = OI.IMAGE_NAME " ;
        }
        if(HOLE != "" )
        {
            query += "LEFT OUTER JOIN " + SCHEMA + "OW_IMAGE_TAG OIT4 ON OIT4.IMAGE_NAME = OI.IMAGE_NAME " ;
        }

        query += "where OI.PORT_NUM IN ( " ;
        query += "SELECT A.PORT_NUM " ;
        query += "FROM " + SCHEMA + "OW_PORT_HEAD A " ;
        query += "LEFT OUTER JOIN " + SCHEMA + "OW_PLANER_HEAD B ON B.PLAN_NUM = A.PLAN_NUM " ;
        query += "LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_IMAGE C ON C.PLAN_NUM = A.PLAN_NUM " ;
        query += "LEFT OUTER JOIN " + SCHEMA + "OW_IMAGE E ON E.PORT_NUM = A.PORT_NUM AND E.REPR = 'Y' " ;
        if(LOC != "")
        {
            query +="LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_LOCATION F ON F.PLAN_NUM = A.PLAN_NUM " ;
        }
        if(TAG1 != "")
        {
            query +="LEFT OUTER JOIN " + SCHEMA + "OW_PORT_TAG G1 ON G1.PORT_NUM = A.PORT_NUM " ;
        }
        if(TAG2 != "")
        {
            query +="LEFT OUTER JOIN " + SCHEMA + "OW_PORT_TAG G2 ON G2.PORT_NUM = A.PORT_NUM " ;
        }
        if(TAG3 != "")
        {
            query +="LEFT OUTER JOIN " + SCHEMA + "OW_PORT_TAG G3 ON G3.PORT_NUM = A.PORT_NUM " ;
        }
        if(USER_ID != "")
        {
            query += "LEFT OUTER JOIN " + SCHEMA + "OW_PORT_LIKE D ON D.PORT_NUM = A.PORT_NUM AND D.USER_ID = '" + sendData.get("USER_ID") + "' " ;
        }
        query += "where A.PORT_USE_YN = 'Y' " ;
        query +="AND A.PORT_PRICE BETWEEN " + MINM + " AND " + MAXM + " " ;
        if(LOC != "")
        {
            query +="AND F.PLAN_LOC = '" + LOC + "' " ;
        }
        if(TAG1 != "")
        {
            query +="AND G1.PORT_TAG = '" + TAG1 + "' " ;
        }
        if(TAG2 != "")
        {
            query +="AND G2.PORT_TAG = '" + TAG2 + "' " ;
        }
        if(TAG3 != "")
        {
            query +="AND G3.PORT_TAG = '" + TAG3 + "' " ;
        }
        query +="GROUP BY A.PORT_NUM " ;
        if(LOC != "")
        {
            query +=", F.PLAN_LOC " ;
        }
        query +="ORDER BY A.PORT_NUM ASC ";
        query +=") ";
        if(STUDIO != "" )
        {
            query +="AND OIT1.IMAGE_TAG = '#" + STUDIO + "' " ;
        }
        if(DRESS != "" )
        {
            query +="AND OIT2.IMAGE_TAG = '#" + DRESS + "' " ;
        }
        if(MAKEUP != "" )
        {
            query +="AND OIT3.IMAGE_TAG = '#" + MAKEUP + "' " ;
        }
        if(HOLE != "" )
        {
            query +="AND OIT4.IMAGE_TAG = '#" + HOLE + "' " ;
        }





        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }






    public static JSONObject MainHomePortGetTag(Context context, String portNum) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.


//        select PORT_TAG from " + SCHEMA + "OW_PORT_TAG
//        where PORT_NUM = 'PF00001'


        query += "select PORT_TAG from " + SCHEMA + "OW_PORT_TAG " ;
        query += "where PORT_NUM = '" + portNum + "' " ;
        query += "LIMIT 0, 6 " ;

        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }





    public static JSONObject OtherPortWishData(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

//        SELECT A.*
//        , C.IMAGE_NAME AS P_IMAGE_NAME
//            , C.IMAGE_PATH AS P_IMAGE_PATH
//        FROM " + SCHEMA + "OW_PORT_HEAD A
//        LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_IMAGE C ON C.PLAN_NUM = A.PLAN_NUM
//        where A.PORT_USE_YN = 'Y'
//        GROUP BY A.PORT_NUM
//        ORDER BY A.PORT_NUM ASC
//        LIMIT 0, 10

        String USER_ID = sendData.get("USER_ID");


        query += "SELECT A.* , B.*" ;
        query +=", C.IMAGE_NAME AS P_IMAGE_NAME " ;
        query +=", C.IMAGE_PATH AS P_IMAGE_PATH " ;
        query +=", E.IMAGE_NAME " ;
        query +=", E.IMAGE_PATH " ;
        if(USER_ID != "")
        {
            query += ", D.LIKE_YN ";
        }
        query +="FROM " + SCHEMA + "OW_PORT_HEAD A " ;
        query +="LEFT OUTER JOIN " + SCHEMA + "OW_PLANER_HEAD B ON B.PLAN_NUM = A.PLAN_NUM " ;
        query +="LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_IMAGE C ON C.PLAN_NUM = A.PLAN_NUM " ;
        query +="LEFT OUTER JOIN " + SCHEMA + "OW_IMAGE E ON E.PORT_NUM = A.PORT_NUM AND E.REPR = 'Y' " ;

        if(USER_ID != "")
        {
            query += "LEFT OUTER JOIN " + SCHEMA + "OW_PORT_LIKE D ON D.PORT_NUM = A.PORT_NUM AND D.USER_ID = '" + sendData.get("USER_ID") + "' " ;
        }
        query +="where A.PORT_USE_YN = 'Y' " ;
        query +="AND D.LIKE_YN = 'Y' " ;
        query +="GROUP BY A.PORT_NUM " ;
        query +="ORDER BY A.PORT_NUM ASC " ;


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }





    public static JSONObject OtherNoticeData(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

//        SELECT *
//        , DATE_FORMAT(NO_REG_DATE,'%Y.%m.%d') AS NO_DATE
//        FROM " + SCHEMA + "OW_NOTICE
//        WHERE NO_SHOW_YN = 'Y';
//        ORDER BY NO_REG_DATE DESC


        query += "SELECT * " ;
        query += ", DATE_FORMAT(NO_REG_DATE,'%Y.%m.%d') AS NO_DATE " ;
        query += "FROM " + SCHEMA + "OW_NOTICE " ;
        query += "WHERE NO_SHOW_YN = 'Y' " ;
        query += "ORDER BY NO_REG_DATE DESC " ;




        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }



    public static JSONObject OtherFnQData(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

//        SELECT *
//        , DATE_FORMAT(NO_REG_DATE,'%Y.%m.%d') AS NO_DATE
//        FROM " + SCHEMA + "OW_NOTICE
//        WHERE NO_SHOW_YN = 'Y';
//        ORDER BY NO_REG_DATE DESC


        query += "SELECT * " ;
        query += "FROM " + SCHEMA + "OW_FNQ " ;
        query += "WHERE FQ_SHOW_YN = 'Y' " ;
        query += "ORDER BY FQ_NUM ASC " ;




        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }




    //전화번호 인증
    public static JSONObject otherInquire(Context context, HashMap<String, String> sendData ) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.


        query =  "INSERT INTO " + SCHEMA + "OW_INQUIRE " +

                "(" +
                "USER_ID" + "," +
                "USER_NAME" + "," +
                "IQ_TITL" + "," +
                "IQ_CONT" + "," +
                "REG_DATE" +
                ") " +

                "VALUES " +

                "(" +
                "'" +  sendData.get("USER_ID") + "'" + "," +
                "'" +  sendData.get("USER_NAME") + "'" + "," +
                "'" +  sendData.get("IQ_TITL") + "'" + "," +
                "'" +  sendData.get("IQ_CONT") + "'" + "," +
                "NOW()" +
                ")" ;



        JSONObject returnJson = WebConnection.insertQuery(context, query);

        return returnJson;

    }





    public static JSONObject OtherAgreeLaw(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

//        select CONVERT(AG_CONTENT USING utf8) AS CONTENT from " + SCHEMA + "OW_AGREE_LAW
//        where AG_TYPE = 'P'

        query += "select CONVERT(AG_CONTENT USING utf8) AS CONTENT from " + SCHEMA + "OW_AGREE_LAW  " ;
        query += "where AG_TYPE = '" + sendData.get("AG_TYPE") + "' " ;


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }





    public static JSONObject AAA(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query1 = "";
        //쿼리를 여기서 만든다.

//        SELECT A.*
//        , C.IMAGE_NAME AS P_IMAGE_NAME
//            , C.IMAGE_PATH AS P_IMAGE_PATH
//        FROM " + SCHEMA + "OW_PORT_HEAD A
//        LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_IMAGE C ON C.PLAN_NUM = A.PLAN_NUM
//        where A.PORT_USE_YN = 'Y'
//        GROUP BY A.PORT_NUM
//        ORDER BY A.PORT_NUM ASC
//        LIMIT 0, 10

        String USER_ID = sendData.get("USER_ID");


        query1 += "SELECT A.* , B.*" ;
        query1 +=", C.IMAGE_NAME AS P_IMAGE_NAME " ;
        query1 +=", C.IMAGE_PATH AS P_IMAGE_PATH " ;
        query1 +=", E.IMAGE_NAME " ;
        query1 +=", E.IMAGE_PATH " ;
        if(USER_ID != "")
        {
            query1 += ", D.LIKE_YN ";
        }
        query1 +="FROM " + SCHEMA + "OW_PORT_HEAD A " ;
        query1 +="LEFT OUTER JOIN " + SCHEMA + "OW_PLANER_HEAD B ON B.PLAN_NUM = A.PLAN_NUM " ;
        query1 +="LEFT OUTER JOIN " + SCHEMA + "OW_PLAN_IMAGE C ON C.PLAN_NUM = A.PLAN_NUM " ;
        query1 +="LEFT OUTER JOIN " + SCHEMA + "OW_IMAGE E ON E.PORT_NUM = A.PORT_NUM AND E.REPR = 'Y' " ;

        if(USER_ID != "")
        {
            query1 += "LEFT OUTER JOIN " + SCHEMA + "OW_PORT_LIKE D ON D.PORT_NUM = A.PORT_NUM AND D.USER_ID = '" + sendData.get("USER_ID") + "' " ;
        }
        query1 +="where A.PORT_USE_YN = 'Y' " ;
        query1 +="GROUP BY A.PORT_NUM " ;
        query1 +="ORDER BY RAND()" ;

//        query1 +="ORDER BY A.PORT_NUM ASC " ;
//        query1 +="LIMIT " + sendData.get("START") + ", " + sendData.get("NUM");






        String query2 = "";
        //쿼리를 여기서 만든다.


//        select PORT_TAG from " + SCHEMA + "OW_PORT_TAG
//        where PORT_NUM = 'PF00001'


        query2 += "select PORT_TAG from " + SCHEMA + "OW_PORT_TAG " ;
        query2 += "where PORT_NUM = '" + "PF000000" + "' " ;
        query2 += "LIMIT 0, 6 " ;



        JSONObject returnJson = WebConnection.selectTwoQuery(context, query1, query2);

        return returnJson;

    }



    public static JSONObject CountHeart(Context context, HashMap<String, String> sendData) throws Exception
    {

//        select COUNT(*) from onWedding.OW_PORT_LIKE
//        where USER_ID = 'tarpboy@naver.com'

        String query = "select COUNT(*) AS COUNT " +
                "FROM " + SCHEMA + "OW_PORT_LIKE " +
                "WHERE USER_ID  = '" + sendData.get("USER_ID") + "' " +
                " ";

        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;
    }




    public static JSONObject VersionCheck(Context context, HashMap<String, String> sendData) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.


//        SELECT * FROM onWedding.OW_VERSION;


        query = "SELECT * FROM onWedding.OW_VERSION ";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;

    }



}

