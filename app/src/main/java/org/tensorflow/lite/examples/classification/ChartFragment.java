package org.tensorflow.lite.examples.classification;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
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

    SessionManager sessionManager;

    String GET_LAT_PULLDOWN_URL = UtilityManager.BASE_URL + "c200/getLatPulldownAsc.php?";
    String GET_TREADMILL_URL = UtilityManager.BASE_URL + "c200/getTreadmillAsc.php?";
    String GET_LEG_PRESS_URL = UtilityManager.BASE_URL + "c200/getLegpressAsc.php?";

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
        sessionManager = new SessionManager(getActivity().getApplicationContext());

        latPulldownChart = v.findViewById(R.id.latpulldown_chart);
        treadmillChart = v.findViewById(R.id.treadmill_chart);
        legpressChart = v.findViewById(R.id.legpress_chart);

        ArrayList<BarEntry> lpEntries = new ArrayList<>();
        ArrayList<BarEntry> tmEntries = new ArrayList<>();
        ArrayList<BarEntry> lgEntries = new ArrayList<>();

        requestParams.put("username", sessionManager.getUsername());
        asyncHttpClient.get("http://10.0.2.2/c200/getLegpressAsc.php", requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    for(int i=0; i < response.length(); i++) {
                        JSONObject obj = (JSONObject)response.get(i);
                        int reps = obj.getInt("reps");

                        //Initialise chart entry
                        BarEntry lgEntry = new BarEntry(i + 1, reps);

                        //Add values in array list
                        lgEntries.add(lgEntry);
                    }

                    //Initialise bar data set
                    BarDataSet lgDataSet = new BarDataSet(lgEntries, "Reps");

                    //Set colours
                    lgDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                    //Hide draw value
                    lgDataSet.setDrawValues(false);

                    //Set bar data
                    legpressChart.setData(new BarData(lgDataSet));

                    //Set animation
                    legpressChart.animateY(5000);

                    //Set Y-Axis to start from 0
                    YAxis left = legpressChart.getAxisLeft();
                    left.setAxisMinimum(0f);

                    //Set description text and color
                    legpressChart.getDescription().setText("Leg Press");
                    legpressChart.getDescription().setTextColor(Color.BLUE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        asyncHttpClient.get("http://10.0.2.2/c200/getLatPulldownAsc.php", requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    for(int i=0; i < response.length(); i++) {
                        JSONObject obj = (JSONObject)response.get(i);
                        int reps = obj.getInt("reps");

                        //Initialise chart entry
                        BarEntry lpEntry = new BarEntry(i + 1, reps);

                        //Add values in array list
                        lpEntries.add(lpEntry);
                    }

                    //Initialise bar data set
                    BarDataSet lpDataSet = new BarDataSet(lpEntries, "Reps");

                    //Set colours
                    lpDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                    //Hide draw value
                    lpDataSet.setDrawValues(false);

                    //Set bar data
                    latPulldownChart.setData(new BarData(lpDataSet));

                    //Set animation
                    latPulldownChart.animateY(5000);

                    //Set Y Axis to start from 0
                    YAxis left = latPulldownChart.getAxisLeft();
                    left.setAxisMinimum(0f);

                    //Set description text and color
                    latPulldownChart.getDescription().setText("Lat Pulldown");
                    latPulldownChart.getDescription().setTextColor(Color.BLUE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        asyncHttpClient.get("http://10.0.2.2/c200/getTreadmillAsc.php", requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    for(int i=0; i < response.length(); i++) {
                        JSONObject obj = (JSONObject)response.get(i);
                        int distance = obj.getInt("distance");

                        //Initialise chart entry
                        BarEntry tmEntry = new BarEntry(i + 1, distance);

                        //Add values in array list
                        tmEntries.add(tmEntry);
                    }
                    //Initialise bar data set
                    BarDataSet tmDataSet = new BarDataSet(tmEntries, "Distance");

                    //Set colours
                    tmDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                    //Hide draw value
                    tmDataSet.setDrawValues(false);

                    //Set bar data
                    treadmillChart.setData(new BarData(tmDataSet));

                    //Set animation
                    treadmillChart.animateY(5000);

                    //Set Y Axis to start from 0
                    YAxis left = treadmillChart.getAxisLeft();
                    left.setAxisMinimum(0f);

                    //Set description text and color
                    treadmillChart.getDescription().setText("Treadmill");
                    treadmillChart.getDescription().setTextColor(Color.BLUE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return v;
    }
}




















































