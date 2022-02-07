package org.tensorflow.lite.examples.classification;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class ChartFragment extends Fragment {
    LineChart lineChart;

    ArrayList<Entry> latPullDownSetEntries;
    ArrayList<Entry> latPullDownRepEntries;
    ArrayList<Entry> latPullDownWeightEntries;

    ArrayList<Entry> legPressEntries;
    ArrayList<Entry> treadmillEntries;

    LineDataSet latPullDownSet;
    LineDataSet legPressSet;
    LineDataSet treadmillSet;

    LineData latPullDownData;
    LineData legPressData;
    LineData treadmillData;

    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;

    String GET_LAT_PULLDOWN = UtilityManager.BASE_URL + "c200/getLatPulldown.php";
    String GET_TREADMILL = UtilityManager.BASE_URL + "c200/getTreadmill.php";
    String GET_LEG_PRESS = UtilityManager.BASE_URL + "c200/getLegPress.php";

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
        lineChart = v.findViewById(R.id.line_chart);

        latPullDownSetEntries = new ArrayList<>();
        latPullDownRepEntries = new ArrayList<>();
        latPullDownWeightEntries = new ArrayList<>();

        legPressEntries = new ArrayList<>();
        treadmillEntries = new ArrayList<>();

        //Fill ArrayLists with data
        asyncHttpClient.get(GET_LAT_PULLDOWN, requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String previousDate = "";
                    for(int i=0; i < response.length(); i++) {
                        JSONObject obj = (JSONObject)response.get(i);
                        String date = obj.getString("metric1");
                        int sets = obj.getInt("sets");
                        int reps = obj.getInt("reps");
                        int weight = obj.getInt("weight");

                        //Check if entry is recorded on the same day. If so, add the numbers to the previous entry.
                        if (date.equals(previousDate)) {

                            latPullDownSetEntries.set(latPullDownSetEntries.size() - 1,
                                    latPullDownSetEntries.get(latPullDownSetEntries.size()-1 + sets));

                            latPullDownWeightEntries.set(latPullDownWeightEntries.size() - 1,
                                    latPullDownWeightEntries.get(latPullDownWeightEntries.size()-1 + weight));

                            latPullDownRepEntries.set(latPullDownRepEntries.size() - 1,
                                    latPullDownRepEntries.get(latPullDownRepEntries.size()-1 + reps));
                        }
                        else {
                            latPullDownSetEntries.add(new Entry(i + 1, sets));
                            latPullDownWeightEntries.add(new Entry(i + 1, weight));
                            latPullDownRepEntries.add(new Entry(i + 1, reps));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //latPullDownSet = new LineDataSet(latPullDownEntries, "Lat Pulldown");
        legPressSet = new LineDataSet(legPressEntries, "Leg Press");
        treadmillSet = new LineDataSet(treadmillEntries, "Treadmill");

        latPullDownData = new LineData(latPullDownSet);
        legPressData = new LineData(legPressSet);
        treadmillData = new LineData(treadmillSet);

        return v;
    }
}