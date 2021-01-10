package com.woongjin.sendemailtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.woongjin.sendemailtest.Popup.NoticePopup;
import com.woongjin.sendemailtest.Popup.OnEventOkListener;
import com.woongjin.sendemailtest.Popup.ProgressPopup;
import com.woongjin.sendemailtest.WebConnect.ApiFileProvider;
import com.woongjin.sendemailtest.WebConnect.ApiGetSingleContract;
import com.woongjin.sendemailtest.WebConnect.ApiSendEmail;
import com.woongjin.sendemailtest.WebConnect.ApiUpdateContract;
import com.woongjin.sendemailtest.WebConnect.InterfaceAsyncResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MainActivity extends AppCompatActivity {


    String filePath = "";
    WebView myWebView;
    Button btn_send_email;


    String html = "";



    String name = "";
    String jumin = "";
    String address = "";
    String tel = "";
    String email = "";

    String B_ACCOUNT_PLACE = "";
    String B_NM_PLACE = "";
    String B_ACT_NM_PLACE = "";


    String GENDER_PLACE = "";

    HashMap<String, String> contractData = new HashMap<>();
    ArrayList<HashMap<String, String>> arryContractList = new ArrayList<HashMap<String, String>>();


    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.enableSlowWholeDocumentDraw();
        }



        setContentView(R.layout.activity_main);


        mContext = this;



        Bundle bundle = new Bundle();
        filePath = getIntent().getStringExtra("filePath");

        name = getIntent().getStringExtra("name");
        jumin = getIntent().getStringExtra("jumin");
        address = getIntent().getStringExtra("address");
        tel = getIntent().getStringExtra("tel");
//        email = getIntent().getStringExtra("email");
        email = "null";

        B_ACCOUNT_PLACE = getIntent().getStringExtra("B_ACCOUNT_PLACE");
        B_NM_PLACE = getIntent().getStringExtra("B_NM_PLACE");
        B_ACT_NM_PLACE = getIntent().getStringExtra("B_ACT_NM_PLACE");
        GENDER_PLACE = getIntent().getStringExtra("GENDER_PLACE");




        contractData = (HashMap<String, String>)getIntent().getSerializableExtra("contractData");



        Log.e("Jonathan", " bundle filePath :: " +filePath);



        btn_send_email = (Button)findViewById(R.id.btn_send_email);
        btn_send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateInfo();

            }
        });




        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.getSettings().setAllowFileAccess(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setBuiltInZoomControls(true);




        getSingleContract();



    }



    private void updateInfo()
    {

        final ProgressPopup dialog = new ProgressPopup(mContext);

        if(!dialog.isShowing())
        {
            dialog.setMessage("로딩중...");
            dialog.show();
        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());





        HashMap<String,String> infoData = (HashMap<String,String>)contractData.clone();
        infoData.put("JUMIN_PLACE", jumin);
        infoData.put("ADRS_PLACE", address);
        infoData.put("EMAIL", email);

        infoData.put("B_ACCOUNT_PLACE", B_ACCOUNT_PLACE);
        infoData.put("B_NM_PLACE", B_NM_PLACE);
        infoData.put("B_ACT_NM_PLACE", B_ACT_NM_PLACE);
        infoData.put("GENDER_PLACE", GENDER_PLACE);

        infoData.put("SEND_DT", currentDateandTime);
        infoData.put("SEND_YN", "Y");








        //Jonathan 200828 커밋



        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Add a new document with a generated ID
        db.collection("CT_TB_CONTRACT_HIST")
                .document(infoData.get("SEQ"))
                .set(infoData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        savePngFile(screenshot2(myWebView));

                        dialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Jonathan", "Error adding document", e);

                        NoticePopup popup = new NoticePopup(mContext, "저장에 실패하였습니다.\n 다시 시도해 주세요.", new OnEventOkListener() {
                            @Override
                            public void onOk() {
                                // TODO Auto-generated method stub

                            }
                        });
                        popup.setCanceledOnTouchOutside(false);
                        popup.show();

                    }
                });












//        final ApiUpdateContract getContracts = new ApiUpdateContract(this, new InterfaceAsyncResponse() {
//            @Override
//            public void processFinish(String output) {
//                try {
//
//                    JSONObject result = new JSONObject(output);
//                    if("1".equals(result.getString("getData")))
//                    {
//
//                        savePngFile(screenshot2(myWebView));
//
//
//                    }
//                    else
//                    {
//                        NoticePopup popup = new NoticePopup(mContext, "저장에 실패하였습니다.\n 다시 시도해 주세요.", new OnEventOkListener() {
//                            @Override
//                            public void onOk() {
//                                // TODO Auto-generated method stub
//
//                            }
//                        });
//                        popup.setCanceledOnTouchOutside(false);
//                        popup.show();
//
//                    }
//
//
//
//
//                }
//                catch (Exception e)
//                {
//
//                }
//            }
//        });
//
//        getContracts.execute(infoData);




    }




    private void getSingleContract()
    {

        final ProgressPopup dialog = new ProgressPopup(mContext);

        if(!dialog.isShowing())
        {
            dialog.setMessage("로딩중...");
            dialog.show();
        }



        arryContractList.clear();
        HashMap<String,String> infoData = (HashMap<String,String>)contractData.clone();



        final ArrayList<HashMap<String, String>> mArrayList = new ArrayList<>();

        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name

        db.collection("CT_TB_CONTRACT")
                .whereEqualTo("CONT_NO", infoData.get("CONT_NO"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            mArrayList.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("Jonathan", "findAll  => " + document.getData());

                                Map<String,Object> map = document.getData();
                                HashMap<String,String> newMap =new HashMap<String,String>();
                                for (Map.Entry<String, Object> entry : map.entrySet()) {
                                    if(entry.getValue() instanceof String){
                                        newMap.put(entry.getKey(), (String) entry.getValue());
                                    }
                                }
                                mArrayList.add(newMap);
                            }


                            ArrayList<HashMap<String, String>> arryItem2 = new ArrayList<HashMap<String, String>>();


                            for(int i = 0 ; i < mArrayList.size() ; i++)
                            {
                                HashMap<String, String> getDataMap = new HashMap<String, String>();

                                Iterator<String> key = mArrayList.get(i).keySet().iterator();
                                while (key.hasNext())
                                {
                                    String DataKey = key.next();
                                    getDataMap.put(DataKey, mArrayList.get(i).get(DataKey).toString());

                                }

                                arryItem2.add(getDataMap);
                            }

                            arryContractList = (ArrayList<HashMap<String, String>>)arryItem2.clone();

                            for(int i = 0 ;i < arryContractList.size() ;i++)
                            {
                                Log.e("Jonathan", "arryContractList :: " +arryContractList.get(i));
                            }




                            String imagePath = "file://" + filePath;
                            Log.e("Jonathan", "filePath :: " + imagePath);
                            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                            String imgageBase64 = Base64.encodeToString(bitmapToByteArray(bitmap), Base64.DEFAULT);
                            String image = "data:image/png;base64," + imgageBase64;



                            Drawable myDrawable = getResources().getDrawable(R.drawable.stamp);
                            Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                            String imgageBase64_stamp = Base64.encodeToString(bitmapToByteArray(myLogo), Base64.DEFAULT);
                            String image_stamp = "data:image/png;base64," + imgageBase64_stamp;





                            html = arryContractList.get(0).get("CONT_CON").toString();
                            html = html.replace("{IMAGE_PLACEHOLDER}", image);
                            html = html.replace("{IMAGE_STAMP}", image_stamp);
                            html = html.replace("{NAME_PLACE}", name);
                            html = html.replace("{JUMIN_PLACE}", jumin);
                            html = html.replace("{ADRS_PLACE}", address);
                            html = html.replace("{TEL_PLACE}", tel);




                            html = html.replace("{LOC_PLACE}", contractData.get("LOC_PLACE"));

                            html = html.replace("{BN_PLACE}", contractData.get("BN_PLACE"));
                            html = html.replace("{BNM_PLACE}", contractData.get("BNM_PLACE"));
                            html = html.replace("{BCEO_PLACE}", contractData.get("BCEO_PLACE"));

                            html = html.replace("{MINP_PLACE}", contractData.get("MINP_PLACE"));
                            html = html.replace("{LUNCH_PLACE}", contractData.get("LUNCH_PLACE"));



                            html = html.replace("{CHECK_P_PLACE}", contractData.get("CHECK_P_PLACE"));
                            html = html.replace("{AGREE_P_PLACE}", contractData.get("AGREE_P_PLACE"));
                            html = html.replace("{HAP_P_PLACE}", contractData.get("HAP_P_PLACE"));


                            //Jonathan 210108 근무시간 추가
                            html = html.replace("{START_HOUR}", contractData.get("START_HOUR"));
                            html = html.replace("{START_MIN}", contractData.get("START_MIN"));
                            html = html.replace("{END_HOUR}", contractData.get("END_HOUR"));
                            html = html.replace("{END_MIN}", contractData.get("END_MIN"));



                            //일급
                            if("CT0003".equals(contractData.get("CONT_NO")))
                            {


                                if("M".equals(GENDER_PLACE))
                                {
                                    html = html.replace("{GENDER_PLACE}", "남자");
                                }
                                else if("F".equals(GENDER_PLACE))
                                {
                                    html = html.replace("{GENDER_PLACE}", "여자");
                                }

                                html = html.replace("{H_PAY_PLACE}", contractData.get("H_PAY_PLACE"));
                                html = html.replace("{D_PAY_PLACE}", contractData.get("D_PAY_PLACE"));
                                html = html.replace("{BD_PAY_PLACE}", contractData.get("BD_PAY_PLACE"));
                                html = html.replace("{BD_RAT_PLACE}", contractData.get("BD_RAT_PLACE"));
                                html = html.replace("{WL_PAY_PLACE}", contractData.get("WL_PAY_PLACE"));
                                html = html.replace("{WL_RAT_PLACE}", contractData.get("WL_RAT_PLACE"));
                                html = html.replace("{REMARK1}", Define.nullCheck(contractData.get("REMARK1")));
                                html = html.replace("{REMARK2}", Define.nullCheck(contractData.get("REMARK2")));
                            }
                            //월급
                            else if("CT0004".equals(contractData.get("CONT_NO")))
                            {

                                html = html.replace("{SUSP_PT_PLACE}", contractData.get("SUSP_PT_PLACE"));
                                html = html.replace("{MMPAY_PLACE}", contractData.get("MMPAY_PLACE"));
                                html = html.replace("{B_W_T_PLACE}", contractData.get("B_W_T_PLACE"));
                                html = html.replace("{B_W_P_PLACE}", contractData.get("B_W_P_PLACE"));
                                html = html.replace("{B_H_T_PLACE}", contractData.get("B_H_T_PLACE"));
                                html = html.replace("{B_H_P_PLACE}", contractData.get("B_H_P_PLACE"));
                                html = html.replace("{REMARK1}", Define.nullCheck(contractData.get("REMARK1")));
                                html = html.replace("{REMARK2}", Define.nullCheck(contractData.get("REMARK2")));
                            }


                            html = html.replace("{YY_PLACE}", contractData.get("YY_PLACE"));
                            html = html.replace("{MM_PLACE}", contractData.get("MM_PLACE"));
                            html = html.replace("{DD_PLACE}", contractData.get("DD_PLACE"));

                            html = html.replace("{CONT_FM_D_PLACE}", contractData.get("CONT_FM_D_PLACE"));
                            html = html.replace("{CONT_TO_D_PLACE}", contractData.get("CONT_TO_D_PLACE"));

                            html = html.replace("{GAP_PLACE}", contractData.get("GAP_PLACE"));
                            html = html.replace("{WORK_P_PLACE}", contractData.get("WORK_P_PLACE"));


                            html = html.replace("{B_ACCOUNT_PLACE}", B_ACCOUNT_PLACE);
                            html = html.replace("{B_NM_PLACE}", B_NM_PLACE);
                            html = html.replace("{B_ACT_NM_PLACE}", B_ACT_NM_PLACE);





                            myWebView.loadDataWithBaseURL("", html, "text/html", "UTF-8", null);


                            if(dialog.isShowing())
                            {
                                dialog.dismiss();
                            }

                            saveHtmlFile();



                        } else {
                            Log.w("Jonathan", "Error getting documents.", task.getException());
                        }
                    }
                });









//        final ApiGetSingleContract getContractHist = new ApiGetSingleContract(this, new InterfaceAsyncResponse() {
//            @Override
//            public void processFinish(String output) {
//                try {
//
//                    JSONObject result = new JSONObject(output);
//                    JSONArray jArr = result.getJSONArray("getData");
//                    ArrayList<HashMap<String, String>> arryItem2 = new ArrayList<HashMap<String, String>>();
//
//
//                    for(int i = 0 ; i < jArr.length() ; i++)
//                    {
//                        HashMap<String, String> getDataMap = new HashMap<String, String>();
//
//                        Iterator<String> key = jArr.getJSONObject(i).keys();
//                        while (key.hasNext())
//                        {
//                            String DataKey = key.next();
//                            getDataMap.put(DataKey, jArr.getJSONObject(i).get(DataKey).toString());
//
//                        }
//
//                        arryItem2.add(getDataMap);
//                    }
//
//                    arryContractList = (ArrayList<HashMap<String, String>>)arryItem2.clone();
//
//                    for(int i = 0 ;i < arryContractList.size() ;i++)
//                    {
//                        Log.e("Jonathan", "arryContractList :: " +arryContractList.get(i));
//                    }
//
//
//
//
//                    String imagePath = "file://" + filePath;
//                    Log.e("Jonathan", "filePath :: " + imagePath);
//                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//                    String imgageBase64 = Base64.encodeToString(bitmapToByteArray(bitmap), Base64.DEFAULT);
//                    String image = "data:image/png;base64," + imgageBase64;
//
//
//
//                    Drawable myDrawable = getResources().getDrawable(R.drawable.stamp);
//                    Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
//                    String imgageBase64_stamp = Base64.encodeToString(bitmapToByteArray(myLogo), Base64.DEFAULT);
//                    String image_stamp = "data:image/png;base64," + imgageBase64_stamp;
//
//
//
//
//
//                    html = arryContractList.get(0).get("CONT_CON").toString();
//                    html = html.replace("{IMAGE_PLACEHOLDER}", image);
//                    html = html.replace("{IMAGE_STAMP}", image_stamp);
//                    html = html.replace("{NAME_PLACE}", name);
//                    html = html.replace("{JUMIN_PLACE}", jumin);
//                    html = html.replace("{ADRS_PLACE}", address);
//                    html = html.replace("{TEL_PLACE}", tel);
//
//
//
//
//                    html = html.replace("{LOC_PLACE}", contractData.get("LOC_PLACE"));
//
//                    html = html.replace("{BN_PLACE}", contractData.get("BN_PLACE"));
//                    html = html.replace("{BNM_PLACE}", contractData.get("BNM_PLACE"));
//                    html = html.replace("{BCEO_PLACE}", contractData.get("BCEO_PLACE"));
//
//                    html = html.replace("{MINP_PLACE}", contractData.get("MINP_PLACE"));
//                    html = html.replace("{LUNCH_PLACE}", contractData.get("LUNCH_PLACE"));
//
//
//
//                    html = html.replace("{CHECK_P_PLACE}", contractData.get("CHECK_P_PLACE"));
//                    html = html.replace("{AGREE_P_PLACE}", contractData.get("AGREE_P_PLACE"));
//                    html = html.replace("{HAP_P_PLACE}", contractData.get("HAP_P_PLACE"));
//
//
//
//                    //일급
//                    if("CT0003".equals(contractData.get("CONT_NO")))
//                    {
//
//
//                        if("M".equals(GENDER_PLACE))
//                        {
//                            html = html.replace("{GENDER_PLACE}", "남자");
//                        }
//                        else if("F".equals(GENDER_PLACE))
//                        {
//                            html = html.replace("{GENDER_PLACE}", "여자");
//                        }
//
//                        html = html.replace("{H_PAY_PLACE}", contractData.get("H_PAY_PLACE"));
//                        html = html.replace("{D_PAY_PLACE}", contractData.get("D_PAY_PLACE"));
//                        html = html.replace("{BD_PAY_PLACE}", contractData.get("BD_PAY_PLACE"));
//                        html = html.replace("{BD_RAT_PLACE}", contractData.get("BD_RAT_PLACE"));
//                        html = html.replace("{WL_PAY_PLACE}", contractData.get("WL_PAY_PLACE"));
//                        html = html.replace("{WL_RAT_PLACE}", contractData.get("WL_RAT_PLACE"));
//                        html = html.replace("{REMARK1}", Define.nullCheck(contractData.get("REMARK1")));
//                        html = html.replace("{REMARK2}", Define.nullCheck(contractData.get("REMARK2")));
//                    }
//                    //월급
//                    else if("CT0004".equals(contractData.get("CONT_NO")))
//                    {
//
//                        html = html.replace("{SUSP_PT_PLACE}", contractData.get("SUSP_PT_PLACE"));
//                        html = html.replace("{MMPAY_PLACE}", contractData.get("MMPAY_PLACE"));
//                        html = html.replace("{B_W_T_PLACE}", contractData.get("B_W_T_PLACE"));
//                        html = html.replace("{B_W_P_PLACE}", contractData.get("B_W_P_PLACE"));
//                        html = html.replace("{B_H_T_PLACE}", contractData.get("B_H_T_PLACE"));
//                        html = html.replace("{B_H_P_PLACE}", contractData.get("B_H_P_PLACE"));
//                        html = html.replace("{REMARK1}", Define.nullCheck(contractData.get("REMARK1")));
//                        html = html.replace("{REMARK2}", Define.nullCheck(contractData.get("REMARK2")));
//                    }
//
//
//                    html = html.replace("{YY_PLACE}", contractData.get("YY_PLACE"));
//                    html = html.replace("{MM_PLACE}", contractData.get("MM_PLACE"));
//                    html = html.replace("{DD_PLACE}", contractData.get("DD_PLACE"));
//
//                    html = html.replace("{CONT_FM_D_PLACE}", contractData.get("CONT_FM_D_PLACE"));
//                    html = html.replace("{CONT_TO_D_PLACE}", contractData.get("CONT_TO_D_PLACE"));
//
//                    html = html.replace("{GAP_PLACE}", contractData.get("GAP_PLACE"));
//                    html = html.replace("{WORK_P_PLACE}", contractData.get("WORK_P_PLACE"));
//
//
//                    html = html.replace("{B_ACCOUNT_PLACE}", B_ACCOUNT_PLACE);
//                    html = html.replace("{B_NM_PLACE}", B_NM_PLACE);
//                    html = html.replace("{B_ACT_NM_PLACE}", B_ACT_NM_PLACE);
//
//
//
//
//
//                    myWebView.loadDataWithBaseURL("", html, "text/html", "UTF-8", null);
//
//
//
//                    saveHtmlFile();
//
//
//                }
//                catch (Exception e)
//                {
//
//                }
//            }
//        });
//
//        getContractHist.execute(infoData);
    }





    private void saveHtmlFile() {

        String fileName = "";
        String ex_storage = FileManager.getExternalPath();
        // Get Absolute Path in External Sdcard
        String path = ex_storage + "/" + "SIGN_FOLDER";

//        fileName = "contract.html";
        fileName = contractData.get("CONT_NAME")+ "_" +contractData.get("NAME_PLACE") + ".html";


        File file = new File(path, fileName);

        try {
            FileOutputStream out = new FileOutputStream(file);
            byte[] data = html.getBytes();
            out.write(data);
            out.close();
            Log.e("Jonathan", "File Save : " + file.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static byte[] bitmapToByteArray(Bitmap $bitmap) {

        if ($bitmap == null)
            return null;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        $bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    public void sendEmail2()
    {

        String fileName = "";
        String ex_storage = FileManager.getExternalPath();
        // Get Absolute Path in External Sdcard
        String path = ex_storage + "/" + "SIGN_FOLDER";


//        fileName = "contract.html";
//        fileName = contractData.get("CONT_NAME")+ "_" +contractData.get("NAME_PLACE") + ".html";
        fileName = contractData.get("CONT_NAME")+ "_" +contractData.get("NAME_PLACE") + ".png";
        File shareFile = new File(path, fileName);


//        Uri uri = Uri.fromFile(new File(path, fileName));
        final Uri uri = ApiFileProvider.getUri(mContext, BuildConfig.APPLICATION_ID, shareFile);




//        String toemailAddress = "payfun.kr@gmail.com";
        String toemailAddress = "sales@payfun.kr";
        String msubject = "계약서 전송";
        String mmessage = "계약서 내용입니다.";


        Log.e("Jonathan", "path :: " + shareFile.getAbsolutePath());

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", toemailAddress, null));
        intent.putExtra(Intent.EXTRA_SUBJECT, msubject);
        intent.putExtra(Intent.EXTRA_TEXT, mmessage);
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        List<ResolveInfo> resolveInfos = mContext.getPackageManager().queryIntentActivities(intent, 0);
        if (resolveInfos.size() == 0) {
            new AlertDialog.Builder(mContext)
                    .setMessage("Email 앱이 없습니다.")
                    .setPositiveButton("yes", null)
                    .show();
        } else {
            String packageName = resolveInfos.get(0).activityInfo.packageName;
            String name = resolveInfos.get(0).activityInfo.name;

            intent.setAction(Intent.ACTION_SEND);
            intent.setComponent(new ComponentName(packageName, name));

            startActivity(intent);
        }


    }



    public void sendEmail()
    {

        final HashMap<String,String> loginData = new HashMap<String, String>();
        loginData.put("email", email);


        final ApiSendEmail apiLogin = new ApiSendEmail(this, new InterfaceAsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {

                    JSONObject result = new JSONObject(output);

                    if("{}".equals(result.toString()))
                    {
                        Toast.makeText(mContext, "이메일을 성공적으로 보냈습니다.", Toast.LENGTH_SHORT).show();
                        NoticePopup popup = new NoticePopup(mContext, "이메일 전송 성공.", new OnEventOkListener() {
                            @Override
                            public void onOk() {
                                // TODO Auto-generated method stub

                            }
                        });
                        popup.setCanceledOnTouchOutside(false);
                        popup.show();
                    }
                    else
                    {
                        NoticePopup popup = new NoticePopup(mContext, "이메일 전송 실패.", new OnEventOkListener() {
                            @Override
                            public void onOk() {
                                // TODO Auto-generated method stub

                            }
                        });
                        popup.setCanceledOnTouchOutside(false);
                        popup.show();
                    }


                }
                catch (Exception e)
                {

                }
            }
        });

        apiLogin.execute(loginData);




//        try {
//            GMailSender sender = new GMailSender("tarpboy1989@gmail.com", "Jonathan17!");
//            sender.sendMail("This is Subject",
//                    "This is Body",
//                    "tarpboy@naver.com");
//        } catch (Exception e) {
//            Log.e("SendMail", e.getMessage(), e);
//        }
    }





    public static Bitmap screenshot2(WebView webView) {
        webView.measure(View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        webView.layout(0, 0, webView.getMeasuredWidth(), webView.getMeasuredHeight());
        webView.setDrawingCacheEnabled(true);
        webView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(webView.getMeasuredWidth(),
                webView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        int iHeight = bitmap.getHeight();
        canvas.drawBitmap(bitmap, 0, iHeight, paint);
        webView.draw(canvas);
        return bitmap;
    }



    private void savePngFile(Bitmap bitmap) {

        String fileName = "";
        String ex_storage = FileManager.getExternalPath();
        // Get Absolute Path in External Sdcard
        String path = ex_storage + "/" + "SIGN_FOLDER";

//        fileName = "contract.html";
        fileName = contractData.get("CONT_NAME")+ "_" +contractData.get("NAME_PLACE") + ".png";


        File file = new File(path, fileName);

        try (FileOutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (IOException e) {
            e.printStackTrace();
        }



        sendEmail2();

    }



}
