package org.tensorflow.lite.examples.classification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TrackerAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<Entry> trackerList;

    public TrackerAdapter(Context context, int resource, ArrayList objects){
        super(context, resource, objects);
        parent_context = context;
        layout_id = resource;
        trackerList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        // date, equipment, metric1 header+text, metric2 header+text, metric3 header+text
        LinearLayout linearLayoutTrack = rowView.findViewById(R.id.lvTrackLinearLayout);
        LinearLayout linearLayoutBorder = rowView.findViewById(R.id.lvTrackBorderLinearLayout);
        TextView tvTrackDate = rowView.findViewById(R.id.lvTrackDate);
        TextView tvTrackTime = rowView.findViewById(R.id.lvTrackTime);
        ImageView ivTrackImg = rowView.findViewById(R.id.lvTrackTargetImg);
        TextView tvTrackMetricHeader1 = rowView.findViewById(R.id.lvTrackMetric1Header);
        TextView tvTrackMetricHeader2 = rowView.findViewById(R.id.lvTrackMetric2Header);
        TextView tvTrackMetricHeader3 = rowView.findViewById(R.id.lvTrackMetric3Header);
        TextView tvTrackMetric1 = rowView.findViewById(R.id.lvTrackMetric1);
        TextView tvTrackMetric2 = rowView.findViewById(R.id.lvTrackMetric2);
        TextView tvTrackMetric3 = rowView.findViewById(R.id.lvTrackMetric3);


        // Obtain the Entry information based on the position
        Entry current = trackerList.get(position);

        // Set values to the TextView to display the corresponding information
        tvTrackDate.setText(current.getDate());
        tvTrackTime.setText(current.getTime());

        tvTrackMetricHeader1.setText(current.getMetric1());
        tvTrackMetricHeader2.setText(current.getMetric2());
        tvTrackMetricHeader3.setText(current.getMetric3());
        tvTrackMetric1.setText(current.getValue1() + "");
        tvTrackMetric2.setText(current.getValue2() + "");
        tvTrackMetric3.setText(current.getValue3() + "kg");
        boolean targetHit = current.isTargetHit();
        if(targetHit) {
            ivTrackImg.setImageResource(R.drawable.ic_check_lg);
            linearLayoutBorder.setBackgroundColor(Color.parseColor("#308F46"));
            linearLayoutTrack.setBackgroundColor(Color.parseColor("#CAFAD8"));
        } else {
            ivTrackImg.setImageResource(R.drawable.ic_x_lg);
            linearLayoutBorder.setBackgroundColor(Color.parseColor("#993D3D"));
            linearLayoutTrack.setBackgroundColor(Color.parseColor("#FAC8C8"));
        }

        return rowView;
    }

}
