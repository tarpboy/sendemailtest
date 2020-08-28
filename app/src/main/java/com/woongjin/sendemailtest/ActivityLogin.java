package com.woongjin.sendemailtest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.woongjin.sendemailtest.WebConnect.ApiLogin;
import com.woongjin.sendemailtest.WebConnect.InterfaceAsyncResponse;
import com.woongjin.sendemailtest.WebConnect.WebApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.view.View.GONE;

public class ActivityLogin extends AppCompatActivity {



    Context mContext ;


    Button bt_login;


    EditText et_name;
    EditText et_tel;
    EditText et_pwd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;


        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = { Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if(!hasPermissions(mContext, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }




        et_name = (EditText)findViewById(R.id.et_name);
        et_tel = (EditText)findViewById(R.id.et_tel);
        et_pwd = (EditText)findViewById(R.id.et_pwd);



        bt_login = (Button)findViewById(R.id.bt_login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if("".equals(et_name.getText().toString()))
                {
                    Toast.makeText(mContext, "이름 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


                if("".equals(et_tel.getText().toString()))
                {
                    Toast.makeText(mContext, "휴대전화 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }



                if("".equals(et_pwd.getText().toString()))
                {
                    Toast.makeText(mContext, "인증번호 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                ApiLogin();

            }
        });








//        // Access a Cloud Firestore instance from your Activity
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        // Create a new user with a first and last name
//        Map<String, Object> user = new HashMap<>();
//        user.put("first", "ddd");
//        user.put("last", "Lovelsssace");
//        user.put("born", 2333);
//
//        // Add a new document with a generated ID
//        db.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("Jonathan", "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("Jonathan", "Error adding document", e);
//                    }
//                });



    }




    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }



    public void ApiLogin()
    {

        final ArrayList<HashMap<String, String>> mArrayList = new ArrayList<>();

        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name

        db.collection("CT_TB_PERSON")
                .whereEqualTo("C_NAME", et_name.getText().toString())
                .whereEqualTo("PHONE", et_tel.getText().toString())
                .whereEqualTo("PASWD", et_pwd.getText().toString())
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


                            if(mArrayList.size() > 0)
                            {

                                final HashMap<String,String> loginData = new HashMap<String, String>();

                                loginData.put("name", et_name.getText().toString());
                                loginData.put("tel", et_tel.getText().toString());
                                loginData.put("paswd", et_pwd.getText().toString());

                                Intent intent = new Intent(mContext, ActivityContractList.class);
                                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("loginData", loginData);
                                startActivity(intent);
                            }
                            else
                            {
                                NoticePopup popup = new NoticePopup(mContext, "입력정보가 틀립니다.\n 관리자에게 문의하세요", new OnEventOkListener() {
                                    @Override
                                    public void onOk() {
                                        // TODO Auto-generated method stub

                                    }
                                });
                                popup.setCanceledOnTouchOutside(false);
                                popup.show();
                            }



                        } else {
                            Log.w("Jonathan", "Error getting documents.", task.getException());
                        }
                    }
                });






//        final HashMap<String,String> loginData = new HashMap<String, String>();
//
//        loginData.put("name", et_name.getText().toString());
//        loginData.put("tel", et_tel.getText().toString());
//        loginData.put("paswd", et_pwd.getText().toString());
//
//
//        final ApiLogin apiLogin = new ApiLogin(this, new InterfaceAsyncResponse() {
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
//                    if("0".equals(arryItem2.get(0).get("C_COUNT")))
//                    {
//                        NoticePopup popup = new NoticePopup(mContext, "입력정보가 틀립니다.\n 관리자에게 문의하세요", new OnEventOkListener() {
//                            @Override
//                            public void onOk() {
//                                // TODO Auto-generated method stub
//
//                            }
//                        });
//                        popup.setCanceledOnTouchOutside(false);
//                        popup.show();
//                    }
//                    else
//                    {
//
//                        Intent intent = new Intent(mContext, ActivityContractList.class);
//                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
//                        intent.putExtra("loginData", loginData);
//                        startActivity(intent);
//                    }
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
//        apiLogin.execute(loginData);


    }








}
