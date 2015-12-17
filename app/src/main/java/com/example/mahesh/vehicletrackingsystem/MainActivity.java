package com.example.mahesh.vehicletrackingsystem;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mahesh.vehicletrackingsystem.app.AppController;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class
            .getSimpleName();

    private String urlJsonObj = "http://115.99.245.214:8080/gpsdata/sendData";

    private String tag_json_obj = "jobj_req";
    private ProgressDialog pDialog;
    private TextView msgResponse;

    VehicleAdapter resultAdapter;
    ListView listView;
    ArrayList<HashMap<String,String>> results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pDialog=new ProgressDialog(this);
        listView= (ListView) findViewById(R.id.populate);
        msgResponse=(TextView)findViewById(R.id.msgResponse);
        makeJsonObjReq();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                HashMap<String,String> vehicleObject = results.get(position);

                String lat=vehicleObject.get("LATITUDE");
                String lan=vehicleObject.get("LONGITUDE");
                String vehicleid=vehicleObject.get("VEHICLE_ID");
                String customerid=vehicleObject.get("CUSTOMER_ID");
                String speed=vehicleObject.get("SPEED");
      Intent vehicle=new Intent(MainActivity.this,MapActivity.class);

                vehicle.putExtra("latin", lat);
                vehicle.putExtra("lagin", lan);
                vehicle.putExtra("vid", vehicleid);
                vehicle.putExtra("cid", customerid);
                vehicle.putExtra("spd", speed);
                startActivity(vehicle);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void makeJsonObjReq() {
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Parser parse=new Parser(MainActivity.this,response);
                results=parse.getResult();

                if(results!=null)
                {
                    resultAdapter=new VehicleAdapter(MainActivity.this,results);
                    listView.setAdapter(resultAdapter);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "result null guru", Toast.LENGTH_LONG).show();
                }

                msgResponse.setText(parse.getResult().toString());
                hideProgressDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "Androidhive");
                params.put("email", "abc@androidhive.info");
                params.put("pass", "password123");

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
