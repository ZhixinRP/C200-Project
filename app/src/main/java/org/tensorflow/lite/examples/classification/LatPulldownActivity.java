package org.tensorflow.lite.examples.classification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

import java.time.LocalTime;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class LatPulldownActivity extends AppCompatActivity {

    ListView lvLatPulldown;
    Button btnAddLatPulldown;
    ArrayList latPulldownList;
    ArrayAdapter aaLatPulldown;
    String lastActivity;
    ArrayList<Equipment> equipmentList;

    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;

    SessionManager sessionManager;

    String ADD_LATPULLDOWN_URL = UtilityManager.BASE_URL + "c200/addLatPulldown.php";
    String ALL_LATPULLDOWN_URL = UtilityManager.BASE_URL + "c200/getLatPulldown.php?";
    String ALL_EQUIPMENT_URL = UtilityManager.BASE_URL + "c200/getAllEquipment.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lat_pulldown);
        getSupportActionBar().setTitle("Lat Pulldown");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent get = getIntent();

        lastActivity = get.getStringExtra("last");

        lvLatPulldown = findViewById(R.id.lvLatPulldown);
        btnAddLatPulldown = findViewById(R.id.btn_Add_LatPulldown);

        sessionManager = new SessionManager(getApplicationContext());

        latPulldownList = new ArrayList();
        aaLatPulldown = new ArrayAdapter(this, android.R.layout.simple_list_item_1, latPulldownList);
        lvLatPulldown.setAdapter(aaLatPulldown);

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

        latPulldownList.clear();
        requestParams.put("username", sessionManager.getUsername());
        asyncHttpClient.get(ALL_LATPULLDOWN_URL, requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    for(int i=0; i < response.length(); i++) {
                        int id = i+1;
                        JSONObject obj = (JSONObject)response.get(i);
                        String date = obj.getString("date");
                        String time = obj.getString("time");
                        int sets = obj.getInt("sets");
                        int reps = obj.getInt("reps");
                        double weight = obj.getDouble("weight");

                        String entry = String.format("%s - %s \nSets: %d \nReps: %d \nWeight: %.1f kg", date, time, sets, reps, weight);
                        //STORE RECORDS IN TO THE ARRAY
                        latPulldownList.add(entry);
                    }
                    //UPDATE THE LIST VIEW
                    aaLatPulldown.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnAddLatPulldown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.add_legpress, null);

                DatePicker dp_LP_Date = viewDialog.findViewById(R.id.dp_LP_Date);
                TimePicker tp_LP_Time = viewDialog.findViewById(R.id.tp_LP_Time);
                EditText et_LP_Weight = viewDialog.findViewById(R.id.et_LP_Weight);
                EditText et_LP_Reps = viewDialog.findViewById(R.id.et_LP_Reps);
                EditText et_LP_Sets = viewDialog.findViewById(R.id.et_LP_Sets);

                //DIALOG POPUP FOR ADDING NEW ENTRY
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(LatPulldownActivity.this);
                myBuilder.setView(viewDialog);  // Set the view of the dialog
                myBuilder.setTitle("New Lat Pulldown Entry");
                myBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!et_LP_Weight.getText().toString().trim().isEmpty() && !et_LP_Reps.getText().toString().trim().isEmpty() && !et_LP_Sets.getText().toString().trim().isEmpty()) {
                            String date = String.format("%d-%d-%d", dp_LP_Date.getYear(), dp_LP_Date.getMonth()+1, dp_LP_Date.getDayOfMonth());
                            String time = String.format("%d:%d:00", tp_LP_Time.getHour(), tp_LP_Time.getMinute());
                            int sets = Integer.parseInt(et_LP_Sets.getText().toString());
                            int reps = Integer.parseInt(et_LP_Reps.getText().toString());
                            double weight = Double.parseDouble(et_LP_Weight.getText().toString());
                            int targetHit = 0;
                            if(sets <= 100 && reps <= 100 && weight <= 200) {
                                requestParams.put("equipment", "Lat Pulldown");
                                requestParams.put("date", date);
                                requestParams.put("time", time);
                                requestParams.put("sets", sets);
                                requestParams.put("reps", reps);
                                requestParams.put("weight", weight);
                                requestParams.put("username", sessionManager.getUsername());
                                for(int i = 0; i < equipmentList.size(); i++) {
                                    if(equipmentList.get(i).getEqName().equalsIgnoreCase("Lat Pulldown")) {
                                        if(weight >= equipmentList.get(i).getTarget()) {
                                            targetHit = 1;
                                            break;
                                        }
                                    }
                                }
                                requestParams.put("targetHit", targetHit);
                                asyncHttpClient.post(ADD_LATPULLDOWN_URL, requestParams, new JsonHttpResponseHandler(){
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        super.onSuccess(statusCode, headers, response);
                                        try {
                                            boolean result = response.getBoolean("result");
                                            if (result) {
                                                Toast.makeText(LatPulldownActivity.this, "Successfully added entry", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(LatPulldownActivity.this, "Failed to add entry", Toast.LENGTH_SHORT).show();
                                            }

                                            // CLEAR THE LISTVIEW AND GET ALL RECORDS FROM THE DATABASE BASED ON USERNAME (WHEN NEW RECORD ADDED)
                                            latPulldownList.clear();
                                            requestParams.put("username", sessionManager.getUsername());
                                            asyncHttpClient.get(ALL_LATPULLDOWN_URL, requestParams, new JsonHttpResponseHandler(){
                                                @Override
                                                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                                    super.onSuccess(statusCode, headers, response);
                                                    try {
                                                        for(int i=0; i < response.length(); i++) {
                                                            int id = i+1;
                                                            JSONObject obj = (JSONObject)response.get(i);
                                                            String date = obj.getString("date");
                                                            String time = obj.getString("time");
                                                            int sets = obj.getInt("sets");
                                                            int reps = obj.getInt("reps");
                                                            double weight = obj.getDouble("weight");

                                                            String entry = String.format("%s - %s \nSets: %d \nReps: %d \nWeight: %.1f kg", date, time, sets, reps, weight);
                                                            //STORE RECORDS IN TO THE ARRAY
                                                            latPulldownList.add(entry);
                                                        }
                                                        //UPDATE THE LIST VIEW
                                                        aaLatPulldown.notifyDataSetChanged();
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
                                Toast.makeText(LatPulldownActivity.this, "Please fill in appropriate values!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LatPulldownActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                myBuilder.setNeutralButton("Cancel", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        // YOUTUBE PLAYER
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                //CHANGE THE VIDEO ID
                String videoId = "AOpi-p0cJkc";
                youTubePlayer.cueVideo(videoId, 0);
            }
        });

        // SCROLLABLE LISTVIEW
        lvLatPulldown.setOnTouchListener(new ListView.OnTouchListener() {
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
                Intent intent = new Intent(LatPulldownActivity.this, ClassifierActivity.class);
                startActivity(intent);
            }
        }
        finish();
        return true;
    }

}