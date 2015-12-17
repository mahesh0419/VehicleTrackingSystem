package com.example.mahesh.vehicletrackingsystem;

/**
 * Created by MAHESH on 12/11/2015.
 */
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.HashMap;


public class VehicleAdapter extends BaseAdapter{

    Context context;
    ArrayList<HashMap<String,String>> result;
    LayoutInflater inflater;
    public VehicleAdapter(Context context,ArrayList<HashMap<String,String>> result)
    {
        this.context=context;
        this.result=result;
    }
    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        return result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;

        if(convertView==null) {
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.vehicle_row, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.subjectView= (TextView) convertView.findViewById(R.id.subject);
            viewHolder.resultView=(TextView) convertView.findViewById(R.id.sub_code);
            HashMap<String,String> resultObject = result.get(position);
            String subJec=resultObject.get("VEHICLE_ID");
            String resul=resultObject.get("CUSTOMER_ID");
            Log.d("==", subJec + ": " + resul);
            viewHolder.subjectView.setText("Vehicle Id:" +                subJec);
            viewHolder.resultView.setText("Customer  Id:" +                resul);

            //convertView.setTag(viewHolder);


        }
        else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }
        return convertView;
    }
    static class ViewHolderItem {

        TextView subjectView;
        TextView resultView;

    }
}

