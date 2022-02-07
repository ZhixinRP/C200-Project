package org.tensorflow.lite.examples.classification;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import cz.msebera.android.httpclient.Header;

public class ChartFragment extends Fragment {
    LineChart latPulldownChart;

    ArrayList<Entry> latPullDownEntries;
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
        latPulldownChart = v.findViewById(R.id.lat_pulldown_chart);

        latPullDownEntries = new ArrayList<>();

        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();

        //Fill ArrayLists with data
        asyncHttpClient.get(GET_LAT_PULLDOWN, requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Toast.makeText(getActivity(), "Line76", Toast.LENGTH_SHORT).show();
                    String previousDate = "";
                    for(int i=0; i < response.length(); i++) {
                        JSONObject obj = (JSONObject)response.get(i);
                        String date = obj.getString("date");
                        int weight = obj.getInt("weight");

                        //Check if entry is recorded on the same day. If so, add the numbers to the previous entry.
                        if (date.equals(previousDate)) {
                            float xValue = latPullDownEntries.get(latPullDownEntries.size() - 1).getX();
                            float previousWeight =  latPullDownEntries.get(latPullDownEntries.size() - 1).getY();
                            float newWeight = previousWeight + weight;
                            latPullDownEntries.set(latPullDownEntries.size() - 1,
                                    new Entry(xValue, newWeight));
                        }
                        else {
                            latPullDownEntries.add(new Entry(i + 1, weight));
                            previousDate = date;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        latPullDownSet = new LineDataSet(latPullDownEntries, "Lat Pulldown");

        latPullDownData = new LineData(latPullDownSet);

        latPulldownChart.setData(latPullDownData);

        return v;
    }
}