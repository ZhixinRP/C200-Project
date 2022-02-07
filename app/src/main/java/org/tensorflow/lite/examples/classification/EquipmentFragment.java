package org.tensorflow.lite.examples.classification;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.examples.classification.databinding.ActivityHomeBinding;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class EquipmentFragment extends Fragment {

    ListView lvEquipments;
    ArrayList<Equipment> equipmentList;
    EquipmentAdapter adapter;

    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;

    String GET_EQUIPMENT_URL = UtilityManager.BASE_URL + "c200/getAllEquipment.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_equipment, container, false);

        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        equipmentList = new ArrayList<Equipment>();

        lvEquipments = v.findViewById(R.id.lvEquipments);

        adapter = new EquipmentAdapter(getActivity(), R.layout.equipment_layout, equipmentList);
        lvEquipments.setAdapter(adapter);

        asyncHttpClient.get(GET_EQUIPMENT_URL, requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    for(int i=0; i < response.length(); i++) {
                        JSONObject obj = (JSONObject)response.get(i);
                        int id = obj.getInt("equipment_id");
                        String name = obj.getString("name");
                        String metric1 = obj.getString("metric1");
                        String metric2 = obj.getString("metric2");
                        String metric3 = obj.getString("metric3");
                        //STORE RECORDS IN TO THE ARRAY
                        Equipment equipment = new Equipment(id, name, metric1, metric2, metric3, 100);
                        equipmentList.add(equipment);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return v;
    }

}