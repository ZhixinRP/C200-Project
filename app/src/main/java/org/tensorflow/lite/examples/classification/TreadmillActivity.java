package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TreadmillActivity extends AppCompatActivity {

    ListView lvTreadmill;
    Button btnAddTreadmill;
    ArrayList treadmillList;
    ArrayList<Equipment> equipmentList;
    ArrayAdapter aaTreadmill;

    String lastActivity;

    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;

    SessionManager sessionManager;

    String ADD_TREADMILL_URL = UtilityManager.BASE_URL + "c200/addTreadmill.php";
    String ALL_TREADMILL_URL = UtilityManager.BASE_URL + "c200/getTreadmill.php?";
    String ALL_EQUIPMENT_URL = UtilityManager.BASE_URL + "c200/getAllEquipment.php?";

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

        equipmentList = new ArrayList<Equipment>();

        asyncHttpClient.get(ALL_EQUIPMENT_URL, requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    for(int i=0; i < response.length(); i++) {
                        JSONObject obj = (JSONObject) response.get(i);
                        int id = obj.getInt("equipment_id");
                        String name = obj.getString("name");
                        String metric1 = obj.getString("metric1");
                        String metric2 = obj.getString("metric2");
                        String metric3 = obj.getString("metric3");
                        Equipment equipment;
                        switch(sessionManager.getUsername()) {
                            case "Kenneth":
                                switch(i) {
                                    case 0:
                                        equipment = new Equipment(id, name, metric1, metric2, metric3, 30);
                                        break;
                                    case 1:
                                        equipment = new Equipment(id, name, metric1, metric2, metric3, 24);
                                        break;
                                    case 2:
                                        equipment = new Equipment(id, name, metric1, metric2, metric3, 12.59);
                                        break;
                                    default:
                                        equipment = new Equipment(id, name, metric1, metric2, metric3, 50);
                                        break;
                                }
                                break;
                            case "Zhixin":
                                switch(i) {
                                    case 0:
                                        equipment = new Equipment(id, name, metric1, metric2, metric3, 32);
                                        break;
                                    case 1:
                                        equipment = new Equipment(id, name, metric1, metric2, metric3, 14);
                                        break;
                                    case 2:
                                        equipment = new Equipment(id, name, metric1, metric2, metric3, 11.45);
                                        break;
                                    default:
                                        equipment = new Equipment(id, name, metric1, metric2, metric3, 50);
                                        break;
                                }
                                break;
                            case "Amos":
                                switch(i) {
                                    case 0:
                                        equipment = new Equipment(id, name, metric1, metric2, metric3, 28);
                                        break;
                                    case 1:
                                        equipment = new Equipment(id, name, metric1, metric2, metric3, 20);
                                        break;
                                    case 2:
                                        equipment = new Equipment(id, name, metric1, metric2, metric3, 13.45);
                                        break;
                                    default:
                                        equipment = new Equipment(id, name, metric1, metric2, metric3, 50);
                                        break;
                                }
                                break;
                            default:
                                equipment = new Equipment(id, name, metric1, metric2, metric3, 50);
                                break;
                        }

                        //STORE RECORDS IN TO THE ARRAY
                        equipmentList.add(equipment);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        treadmillList.clear();
        requestParams.put("username", sessionManager.getUsername());

        asyncHttpClient.get(ALL_TREADMILL_URL, requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    for(int i=0; i < response.length(); i++) {
                        int id = i+1;
                        JSONObject obj = (JSONObject)response.get(i);
                        String date = obj.getString("date");
                        String time = obj.getString("time");
                        double distance = obj.getDouble("distance");
                        int timing = obj.getInt("timing");
                        double speed = obj.getDouble("speed");

                        int timingSeconds = timing % 60;
                        int timingMinutes = (timing - timingSeconds) / 60;

                        String entry = String.format("%s - %s \nDistance: %s \nTiming: %s \nAverage Speed: %.1f m/s",
                                date, time, distance / 1000 + "km", timingMinutes + " Minutes " + timingSeconds + " Seconds", speed);
                        //STORE RECORDS IN TO THE ARRAY
                        treadmillList.add(entry);
                    }
                    //UPDATE THE LIST VIEW
                    aaTreadmill.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnAddTreadmill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.add_treadmill, null);

                DatePicker dpTreadmill = viewDialog.findViewById(R.id.dp_Treadmill_Date);
                TimePicker tpTreadmill = viewDialog.findViewById(R.id.tp_Treadmill_Time);
                EditText etTreadmillDistance = viewDialog.findViewById(R.id.et_Treadmill_Distance);
                EditText etTreadmillMinutes = viewDialog.findViewById(R.id.et_Treadmill_Minutes);
                EditText etTreadmillSeconds = viewDialog.findViewById(R.id.et_Treadmill_Seconds);

                //DIALOG POPUP FOR ADDING NEW ENTRY
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(TreadmillActivity.this);
                myBuilder.setView(viewDialog);  // Set the view of the dialog
                myBuilder.setTitle("New Treadmill Entry");
                myBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean validInput = false;

                        if(!etTreadmillDistance.getText().toString().trim().isEmpty() &&
                                !etTreadmillMinutes.getText().toString().trim().isEmpty() &&
                                !etTreadmillSeconds.getText().toString().trim().isEmpty()) {
                            validInput = true;
                        }

                        if(validInput) {
                            String date = String.format("%d-%d-%d", dpTreadmill.getYear(), dpTreadmill.getMonth()+1, dpTreadmill.getDayOfMonth());
                            String time = String.format("%d:%d:00", tpTreadmill.getCurrentHour(), tpTreadmill.getCurrentMinute());
                            int distance = Integer.parseInt(etTreadmillDistance.getText().toString()) * 1000;
                            int timing = (Integer.parseInt(etTreadmillMinutes.getText().toString()) * 60) +
                                    Integer.parseInt(etTreadmillSeconds.getText().toString());
                            double speed = distance / timing;
                            int targetHit = 0;
                            if(distance <= 99999 && timing <= 9999 && speed <= 99.9) {
                                requestParams.put("equipment", "Treadmill");
                                requestParams.put("date", date);
                                requestParams.put("time", time);
                                requestParams.put("distance", distance);
                                requestParams.put("timing", timing);
                                requestParams.put("speed", speed);
                                requestParams.put("username", sessionManager.getUsername());
                                for(int i = 0; i < equipmentList.size(); i++) {
                                    if(equipmentList.get(i).getEqName().equalsIgnoreCase("Treadmill")) {
                                        if(distance >= equipmentList.get(i).getTarget()) {
                                            targetHit = 1;
                                            break;
                                        }
                                    }
                                }
                                requestParams.put("target_hit", targetHit);
                                asyncHttpClient.post(ADD_TREADMILL_URL, requestParams, new JsonHttpResponseHandler(){
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        super.onSuccess(statusCode, headers, response);
                                        try {
                                            boolean result = response.getBoolean("result");
                                            if (result) {
                                                Toast.makeText(TreadmillActivity.this, "Successfully added entry", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(TreadmillActivity.this, "Failed to add entry", Toast.LENGTH_SHORT).show();
                                            }

                                            // CLEAR THE LISTVIEW AND GET ALL RECORDS FROM THE DATABASE BASED ON USERNAME (WHEN NEW RECORD ADDED)
                                            treadmillList.clear();
                                            requestParams.put("username", sessionManager.getUsername());
                                            asyncHttpClient.get(ALL_TREADMILL_URL, requestParams, new JsonHttpResponseHandler(){
                                                @Override
                                                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                                    super.onSuccess(statusCode, headers, response);
                                                    try {
                                                        for(int i=0; i < response.length(); i++) {
                                                            int id = i+1;
                                                            JSONObject obj = (JSONObject)response.get(i);
                                                            String date = obj.getString("date");
                                                            String time = obj.getString("time");
                                                            float distance = obj.getInt("distance");
                                                            int timing = obj.getInt("timing");
                                                            double speed = obj.getDouble("speed");

                                                            int timingSeconds = timing % 60;
                                                            int timingMinutes = (timing - timingSeconds) / 60;

                                                            String entry = String.format("%s - %s \nDistance: %s \nTiming: %s \nAverage Speed: %.1f m/s",
                                                                    date, time, distance / 1000 + "km", timingMinutes + " Minutes " + timingSeconds + " Seconds", speed);
                                                            //STORE RECORDS IN TO THE ARRAY
                                                            treadmillList.add(entry);
                                                        }
                                                        //UPDATE THE LIST VIEW
                                                        aaTreadmill.notifyDataSetChanged();
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(TreadmillActivity.this, "Please fill in appropriate values!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(TreadmillActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                myBuilder.setNeutralButton("Cancel", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        // YOUTUBE PLAYER
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_treadmill_tutorial);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                //CHANGE THE VIDEO ID
                String videoId = "usScM1QZrQw";
                youTubePlayer.cueVideo(videoId, 0);
            }
        });

        // SCROLLABLE LISTVIEW
        lvTreadmill.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
    }
    //CUSTOM ACTION BAR MENU
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