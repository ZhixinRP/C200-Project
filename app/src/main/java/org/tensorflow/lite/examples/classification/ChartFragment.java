package org.tensorflow.lite.examples.classification;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ChartFragment extends Fragment {

    BarChart barChart;
    PieChart pieChart;

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
        barChart = v.findViewById(R.id.tv_username);
        pieChart = v.findViewById(R.id.equipmentBtn);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            float value = (float) (i * 10.0);

            //Initialise chart entry
            BarEntry barEntry = new BarEntry(i,value);
            PieEntry pieEntry = new PieEntry(i,value);

            //Add values in array list
            barEntries.add(barEntry);
            pieEntries.add(pieEntry);
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Employees");
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Students");

        //Set colours
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        //Hide draw value
        barDataSet.setDrawValues(false);
        pieDataSet.setDrawValues(false);

        //Set bar data
        barChart.setData(new BarData(barDataSet));
        pieChart.setData(new PieData(pieDataSet));

        //Set animation
        barChart.animateY(5000);
        pieChart.animateXY(5000, 5000);

        //Set description text and color
        barChart.getDescription().setText("Employees Chart");
        barChart.getDescription().setTextColor(Color.BLUE);

        pieChart.getDescription().setText("Students Chart");
        pieChart.getDescription().setTextColor(Color.RED);
        return v;

    }
}