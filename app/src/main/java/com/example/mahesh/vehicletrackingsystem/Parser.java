package com.example.mahesh.vehicletrackingsystem;

/**
 * Created by MAHESH on 12/11/2015.
 */



import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PRADEEP on 12/8/2015.
 */
public class Parser {


    private static final String VEHICLE_ID="vID";
    private static final String LATITUDE="lot";
    private static final String LONGITUDE="lang";
    private static final String SPEED="speed";
    private static final String CUSTOMER_ID="CustomerID";
    private static final String DATA_ARRAY="Addresses";






    ArrayList<HashMap<String,String>> vehicle=new ArrayList<HashMap<String,String>>();


    Context context;
    JSONObject jsonObject1;
    public Parser(Context context,JSONObject json)
    {
        this.context=context;
        this.jsonObject1=json;
    }


    public ArrayList<HashMap<String,String>> getResult()
    {
        JSONObject obj1=null;

        if(jsonObject1!=null)
        {

            try {
                // JSONObject jsonObject1=new JSONObject(json);

                    JSONArray jsonArray=jsonObject1.getJSONArray(DATA_ARRAY);
                    int ArrayLength=jsonArray.length();
                    for(int i=0;i<ArrayLength;i++)
                    {
                        JSONObject jsonObject2=jsonArray.getJSONObject(i);
                        String vid=jsonObject2.getString(VEHICLE_ID);
                        String lot=jsonObject2.getString(LATITUDE);
                        String lang=jsonObject2.getString(LONGITUDE);
                        String speed=jsonObject2.getString(SPEED);
                        String custid=jsonObject2.getString(CUSTOMER_ID);

                        HashMap<String, String> resultDetails = new HashMap<String, String>();

                        resultDetails.put("VEHICLE_ID",vid);
                       resultDetails.put("LATITUDE",lot);
                        resultDetails.put("LONGITUDE",lang);
                        resultDetails.put("SPEED",speed);
                        resultDetails.put("CUSTOMER_ID", custid);


                        vehicle.add(resultDetails);


                    }




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return vehicle;
    }







}
