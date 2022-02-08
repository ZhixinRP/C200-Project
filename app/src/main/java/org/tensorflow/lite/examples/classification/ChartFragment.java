package org.tensorflow.lite.examples.classification;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ChartFragment extends Fragment {
    BarChart latPulldownChart;
    BarChart treadmillChart;
    BarChart legpressChart;

    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;

    String GET_LAT_PULLDOWN_URL = UtilityManager.BASE_URL + "c200/getLatPulldownAsc.php";
    String GET_TREADMILL_URL = UtilityManager.BASE_URL + "c200/getTreadmillAsc.php";
    String GET_LEG_PRESS_URL = UtilityManager.BASE_URL + "c200/getLegpressAsc.php";

    public ChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chart, container, false);
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();

        latPulldownChart = v.findViewById(R.id.latpulldown_chart);
        treadmillChart = v.findViewById(R.id.treadmill_chart);
        legpressChart = v.findViewById(R.id.legpress_chart);

        ArrayList<BarEntry> latPulldownEntries = new ArrayList<>();
        ArrayList<BarEntry> treadmillEntries = new ArrayList<>();
        ArrayList<BarEntry> legpressEntries = new ArrayList<>();

        asyncHttpClient.get(GET_LAT_PULLDOWN_URL, requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    for(int i=0; i < response.length(); i++) {
                        JSONObject obj = (JSONObject)response.get(i);
                        int reps = obj.getInt("reps");

                        //STORE RECORDS IN TO THE ARRAY

                        //Initialise chart entry
                        BarEntry latPulldownEntry = new BarEntry(i, reps);

                        //Add values in array list
                        latPulldownEntries.add(latPulldownEntry);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //Initialise bar data set
        BarDataSet latPulldownSet = new BarDataSet(latPulldownEntries, "Weight (kg)");
        BarDataSet treadmillSet = new BarDataSet(latPulldownEntries, "Distance (km)");
        BarDataSet legpressSet = new BarDataSet(latPulldownEntries, "Weight (kg)");

        //Set colours
        latPulldownSet.setColors(ColorTemplate.COLORFUL_COLORS);
        treadmillSet.setColors(ColorTemplate.COLORFUL_COLORS);
        legpressSet.setColors(ColorTemplate.COLORFUL_COLORS);

        //Hide draw value
        latPulldownSet.setDrawValues(false);
        treadmillSet.setDrawValues(false);
        legpressSet.setDrawValues(false);

        //Set bar data
        latPulldownChart.setData(new BarData(latPulldownSet));
        treadmillChart.setData(new BarData(treadmillSet));
        legpressChart.setData(new BarData(legpressSet));

        //Set animation
        latPulldownChart.animateY(5000);
        treadmillChart.animateY(5000);
        legpressChart.animateY(5000);

        //Set description text and color
        latPulldownChart.getDescription().setText("Lat Pulldown");
        latPulldownChart.getDescription().setTextColor(Color.BLUE);

        treadmillChart.getDescription().setText("Treadmill");
        treadmillChart.getDescription().setTextColor(Color.BLUE);

        legpressChart.getDescription().setText("Leg Press");
        legpressChart.getDescription().setTextColor(Color.BLUE);
        return v;
    }
}




















































