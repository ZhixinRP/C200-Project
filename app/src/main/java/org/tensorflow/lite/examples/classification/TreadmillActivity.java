package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TreadmillActivity extends AppCompatActivity {

    ListView lvTreadmill;
    Button btnAddTreadmill;
    ArrayList treadmillList;
    ArrayAdapter aaTreadmill;
    String lastActivity;


    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;

    SessionManager sessionManager;

    String ADD_LEGPRESS_URL = UtilityManager.BASE_URL + "c200/addTreadmill.php";
    String ALL_LEGPRESS_URL = UtilityManager.BASE_URL + "c200/getTreadmill.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treadmill);
        getSupportActionBar().setTitle("Treadmill");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent get = getIntent();
        lastActivity = get.getStringExtra("last");

        lvTreadmill = findViewById(R.id.lvTreadmill);
        btnAddTreadmill = findViewById(R.id.btn_Add_Treadmill);

        sessionManager = new SessionManager(getApplicationContext());

        treadmillList = new ArrayList();
        aaTreadmill = new ArrayAdapter(this, android.R.layout.simple_list_item_1, treadmillList);
        lvTreadmill.setAdapter(aaTreadmill);

        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();

        // CLEAR THE LISTVIEW AND GET ALL RECORDS FROM THE DATABASE BASED ON USERNAME (WHEN ACTIVITY LOADED)
        treadmillList.clear();
        requestParams.put("username", sessionManager.getUsername());

        asyncHttpClient.get(ALL_LEGPRESS_URL, requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    for(int i=0; i < response.length(); i++) {
                        int ID = i + 1;
                        JSONObject obj = (JSONObject)response.get(i);
                        String date = obj.getString("date");
                        String sets = obj.getString("sets");
                        String reps = obj.getString("reps");
                        String weight = obj.getString("weight");
                        String username = obj.getString("username");
                        //STORE RECORDS IN TO THE ARRAY
                        treadmillList.add("ID: " + ID + "\n" + "Date: " + date + "\n" + "Sets: " + sets + "\n" + "Reps: " + reps + "\n" + "Weight: " + weight + "KG");
                    }
                    //UPDATE THE LIST VIEW
                    aaTreadmill.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(lastActivity != null) {
            if(lastActivity.equalsIgnoreCase("scan")) {
                Intent intent = new Intent(TreadmillActivity.this, ClassifierActivity.class);
                startActivity(intent);
            }
        }
        finish();
        return true;

    }
}