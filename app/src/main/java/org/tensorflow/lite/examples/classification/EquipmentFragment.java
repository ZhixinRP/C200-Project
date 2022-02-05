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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class EquipmentFragment extends Fragment {

    ListView lvEquipments;
    ArrayList equipmentsList;
    ArrayAdapter aaEquipments;

    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;

    String GET_EQUIPMENT_URL = UtilityManager.BASE_URL + "c200/getEquipment.php";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_equipment, container, false);

        lvEquipments = v.findViewById(R.id.lvEquipments);
        equipmentsList = new ArrayList<String>();
        aaEquipments = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, equipmentsList);
        lvEquipments.setAdapter(aaEquipments);

        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();

        asyncHttpClient.get(GET_EQUIPMENT_URL, requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    for(int i=0; i < response.length(); i++) {
                        JSONObject obj = (JSONObject)response.get(i);
                        String name = obj.getString("name");
                        //STORE RECORDS IN TO THE ARRAY
                        equipmentsList.add(name);
                    }
                    //UPDATE THE LIST VIEW
                    aaEquipments.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        lvEquipments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(position == 0) {
                    Intent i = new Intent(getActivity(), LegPressActivity.class);
                    startActivity(i);
                } else if(position == 1) {
                    Intent i = new Intent(getActivity(), LatPulldownActivity.class);
                    startActivity(i);
                } else if (position == 2) {
                    Intent i = new Intent(getActivity(), TreadmillActivity.class);
                    startActivity(i);
                }
            }
        });

        return v;
    }
}