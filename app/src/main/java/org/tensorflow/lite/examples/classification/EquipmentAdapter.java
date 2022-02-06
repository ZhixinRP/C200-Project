package org.tensorflow.lite.examples.classification;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class EquipmentAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Equipment> equipmentList;

    public EquipmentAdapter(Context context, int resource, ArrayList objects){
        super(context, resource, objects);
        parent_context = context;
        layout_id = resource;
        equipmentList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);

        TextView tvEquipmentTitle = rowView.findViewById(R.id.equipmentTitle);
        ImageView IvEquipment = rowView.findViewById(R.id.equipmentIMG);
        LinearLayout lyEquipment = rowView.findViewById(R.id.lyEquipment);

        Equipment current = equipmentList.get(position);
        tvEquipmentTitle.setText(current.getEqName());
        if(current.getEqName().equals("Leg Press")) {
            IvEquipment.setImageResource(R.drawable.leg_press_img);
        } else if(current.getEqName().equals("Lat Pulldown")) {
            IvEquipment.setImageResource(R.drawable.lat_pulldown_img);
        } else if (current.getEqName().equals("Treadmill")) {
            IvEquipment.setImageResource(R.drawable.treadmill_img);
        }

        lyEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current.getEqName().equals("Leg Press")) {
                    Toast.makeText(getContext(), "LEG PRESS CLICKED", Toast.LENGTH_SHORT).show();
                    parent_context.startActivity(new Intent(parent_context, LegPressActivity.class));
                } else if(current.getEqName().equals("Lat Pulldown")) {
                    Toast.makeText(getContext(), "LAT PULL CLICKED", Toast.LENGTH_SHORT).show();
                } else if (current.getEqName().equals("Treadmill")) {

                }
            }
        });
        return rowView;
    }
}
