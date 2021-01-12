package com.woongjin.sendemailtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.woongjin.sendemailtest.Adapter.AddrListAdapter;
import com.woongjin.sendemailtest.Popup.NoticePopup;
import com.woongjin.sendemailtest.Popup.OnEventOkListener;
import com.woongjin.sendemailtest.WebConnect.ApiGetContractHist;
import com.woongjin.sendemailtest.WebConnect.InterfaceAsyncResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ActivityContractList extends AppCompatActivity implements View.OnClickListener {


    private AddrListAdapter mAdapter;
    ListView addrList;

    Context mContext;

    HashMap<String,String> loginData = new HashMap<>();

    ArrayList<HashMap<String, String>> arryContractList = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_list);


        mContext = this;

        addrList = (ListView) findViewById(R.id.home_rvList);
        loginData = (HashMap<String, String>)getIntent().getSerializableExtra("loginData");



        getContractHist();

    }

    @Override
    public void onClick(View v) {





    }




    public void getContractHist()
    {

        arryContractList.clear();
        HashMap<String,String> infoData = (HashMap<String,String>)loginData.clone();




        final ArrayList<HashMap<String, String>> mArrayList = new ArrayList<>();

        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name

        db.collection("CT_TB_CONTRACT_HIST")
                .whereEqualTo("NAME_PLACE", infoData.get("name"))
                .whereEqualTo("TEL_PLACE", infoData.get("tel"))
                .whereEqualTo("SEND_YN", "N")
                .whereEqualTo("USE_YN", "Y")
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


                            for(int i = 0 ; i < mArrayList.size(); i++)
                            {
                                HashMap<String, String> getDataMap = new HashMap<String, String>();

                                Iterator<String> key = mArrayList.get(i).keySet().iterator();;
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

                            mAdapter = new AddrListAdapter(mContext, arryContractList, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                            addrList.setAdapter(mAdapter);


                        } else {
                            Log.w("Jonathan", "Error getting documents.", task.getException());
                        }
                    }
                });




//        final ApiGetContractHist getContractHist = new ApiGetContractHist(this, new InterfaceAsyncResponse() {
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
//                    mAdapter = new AddrListAdapter(mContext, arryContractList, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                        }
//                    });
//                    addrList.setAdapter(mAdapter);
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
//        getContractHist.execute(infoData);

    }
}
