package org.tensorflow.lite.examples.classification;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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
    BarChart barChart;

    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;

    String GET_LAT_PULLDOWN_URL = UtilityManager.BASE_URL + "c200/getLatPulldownAsc.php";

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

        barChart = v.findViewById(R.id.bar_chart);

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        asyncHttpClient.get(GET_LAT_PULLDOWN_URL, requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    for(int i=0; i < response.length(); i++) {
                        JSONObject obj = (JSONObject)response.get(i);
                        int weight = obj.getInt("weight");
                        //STORE RECORDS IN TO THE ARRAY

                        //Initialise chart entry
                        BarEntry barEntry = new BarEntry(i, weight);

                        //Add values in array list
                        barEntries.add(barEntry);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //Initialise bar data set
        BarDataSet barDataSet = new BarDataSet(barEntries, "Employees");

        //Set colours
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        //Hide draw value
        barDataSet.setDrawValues(false);

        //Set bar data
        barChart.setData(new BarData(barDataSet));
        //Set animation
        barChart.animateY(5000);

        //Set description text and color
        barChart.getDescription().setText("Employees Chart");
        barChart.getDescription().setTextColor(Color.BLUE);
        return v;
    }
}




















































