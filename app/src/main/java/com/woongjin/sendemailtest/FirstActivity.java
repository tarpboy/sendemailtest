package com.woongjin.sendemailtest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.view.View.GONE;

public class FirstActivity extends AppCompatActivity {

    String filePath = "";

    View mSignHintLayout;
    View mSignLayout;
    ImageView mSign;
    File mSignPath;

    Context mContext ;


    Button btn_next;


    EditText et_name;
    EditText et_jumin;
    EditText et_address;
    EditText et_tel;
//    EditText et_email;


    EditText et_account;
    EditText et_bank_nm;
    EditText et_account_nm;


    HashMap<String, String> contractData = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        mContext = this;


        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }


        contractData = (HashMap<String, String>)getIntent().getSerializableExtra("contractData");
        Log.e("Jonathan", " contractData :: "+contractData);


        et_name = (EditText)findViewById(R.id.et_name);
        et_jumin = (EditText)findViewById(R.id.et_jumin);
        et_address = (EditText)findViewById(R.id.et_address);
        et_tel = (EditText)findViewById(R.id.et_tel);
//        et_email = (EditText)findViewById(R.id.et_email);



        et_name.setText(contractData.get("NAME_PLACE"));
        et_tel.setText(contractData.get("TEL_PLACE"));
        et_name.setEnabled(false);
        et_tel.setEnabled(false);


        et_account = (EditText)findViewById(R.id.et_account);
        et_bank_nm = (EditText)findViewById(R.id.et_bank_nm);
        et_account_nm = (EditText)findViewById(R.id.et_account_nm);



        mSignHintLayout = findViewById(R.id.signHintLayout);
        mSignLayout = findViewById(R.id.signLayout);
        mSign = findViewById(R.id.sign);
        btn_next = (Button)findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



//                if("".equals(
//                .getText().toString()))
//                {
//                    Toast.makeText(mContext, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
//                    return;
//                }


                if("".equals(et_jumin.getText().toString()))
                {
                    Toast.makeText(mContext, "주민번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }



                if("".equals(et_address.getText().toString()))
                {
                    Toast.makeText(mContext, "주소를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


                if("".equals(et_account.getText().toString()))
                {
                    Toast.makeText(mContext, "계좌번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


                if("".equals(et_account_nm.getText().toString()))
                {
                    Toast.makeText(mContext, "예금주를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if("".equals(et_bank_nm.getText().toString()))
                {
                    Toast.makeText(mContext, "은행명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(mSignPath == null)
                {
                    Toast.makeText(mContext, "사인을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }





                Intent intent = new Intent(mContext, MainActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("filePath", filePath);

                intent.putExtra("name", et_name.getText().toString());
                intent.putExtra("jumin", et_jumin.getText().toString());
                intent.putExtra("address", et_address.getText().toString());
                intent.putExtra("tel", et_tel.getText().toString());
//                intent.putExtra("email", et_email.getText().toString());


                intent.putExtra("B_ACCOUNT_PLACE", et_account.getText().toString());
                intent.putExtra("B_NM_PLACE", et_bank_nm.getText().toString());
                intent.putExtra("B_ACT_NM_PLACE", et_account_nm.getText().toString());


                intent.putExtra("contractData", contractData);


                final RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup1);
                int id = rg.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(id);
                if("남자".equals(rb.getText().toString()))
                {
                    intent.putExtra("GENDER_PLACE", "M");
                }
                else if("여자".equals(rb.getText().toString()))
                {
                    intent.putExtra("GENDER_PLACE", "F");
                }

                startActivity(intent);
            }
        });










        mSignLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "";

                final DialogSign signDialog = new DialogSign(mContext, name, new HashMap<String, String>(), new ArrayList<HashMap<String, String>>());
                signDialog.show();

                SignObserver.getInstance().registerObserver("Fragment_MO_SD_ORD_ACPT_Agreement", new SignObserver.OnSignDataListener() {
                    @Override
                    public void onSignData(String tag, String customerName, Bitmap bitmapSign) {


                        String ex_storage = FileManager.getExternalPath();
                        // Get Absolute Path in External Sdcard
                        String path = ex_storage + "/" + "SIGN_FOLDER";
                        FileManager.makeDirectory(path);



//                        String cacheFileName = getCacheFileName();
//                        File cacheFile = new File(mContext.getCacheDir(), cacheFileName);
//                        try (FileOutputStream fos = new FileOutputStream(cacheFile)) {
//                            bitmapSign.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//                            fos.close();
//                            mSignPath = cacheFile;
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }

                        String cacheFileName = "signImage.png";
                        File cacheFile = new File(path, cacheFileName);
                        try (FileOutputStream fos = new FileOutputStream(cacheFile)) {
                            bitmapSign.compress(Bitmap.CompressFormat.PNG, 100, fos);
                            fos.close();
                            mSignPath = cacheFile;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        uploadFile(cacheFile.getPath().toString());
                        mSignHintLayout.setVisibility(GONE);
                    }
                });
            }
        });




    }






    private String getCacheFileName() {
        return Long.toString(System.currentTimeMillis()) + ".png";
    }

    public void uploadFile(final String filePath) {



        Log.e("Jonathan", " filePath :: " + filePath);
        this.filePath = filePath;

        File imgFile = new  File(filePath);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            mSign.setImageBitmap(myBitmap);

        }

//        Glide.with(this).load(filePath).into(mSign);
    }





}
