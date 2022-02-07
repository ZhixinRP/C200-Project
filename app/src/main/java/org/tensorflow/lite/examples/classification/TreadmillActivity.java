package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
        asyncHttpClient.get(ALL_LEGPRESS_URL, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    for (int i = 0; i < response.length(); i++) {
                        int ID = i + 1;
                        JSONObject obj = (JSONObject) response.get(i);
                        String date = obj.getString("date");
                        String time = obj.getString("time");
                        String distance = obj.getString("distance");
                        //STORE RECORDS IN TO THE ARRAY
                        treadmillList.add("ID: " + ID + "\n" + "Date: " + date + "\n" + "Time: " + time + "\n" + "Distance: " + distance + "KM");
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

                DatePicker treadmillDate = viewDialog.findViewById(R.id.dp_Treadmill_Date);
                EditText treadmillDistance = viewDialog.findViewById(R.id.et_Treadmill_Distance);
                EditText treadmillTime = viewDialog.findViewById(R.id.et_Treadmill_Time);

                //DIALOG POPUP FOR ADDING NEW ENTRY
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(TreadmillActivity.this);
                myBuilder.setView(viewDialog);  // Set the view of the dialog
                myBuilder.setTitle("New Treadmill Entry");
                myBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!treadmillDistance.getText().toString().trim().isEmpty() && !treadmillTime.getText().toString().trim().isEmpty()) {
                            String date = String.format("%d/%d/%d", treadmillDate.getDayOfMonth(), treadmillDate.getMonth() + 1, treadmillDate.getYear());
                            requestParams.put("date", date);
                            requestParams.put("distance", Integer.parseInt(treadmillDistance.getText().toString()));
                            requestParams.put("time", Integer.parseInt(treadmillTime.getText().toString()));
                            requestParams.put("equipment", "Treadmill");
                            requestParams.put("username", sessionManager.getUsername());

                            // ADD THE NEW RECORD INTO THE DATABASE
                            asyncHttpClient.post(ADD_LEGPRESS_URL, requestParams, new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                                    try {
                                        boolean result = response.getBoolean("result");
                                        if (result) {
                                            Toast.makeText(TreadmillActivity.this, "Added Succesfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(TreadmillActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                        }

                                        // CLEAR THE LISTVIEW AND GET ALL RECORDS FROM THE DATABASE BASED ON USERNAME (WHEN NEW RECORD ADDED)
                                        treadmillList.clear();
                                        requestParams.put("username", sessionManager.getUsername());
                                        asyncHttpClient.get(ALL_LEGPRESS_URL, requestParams, new JsonHttpResponseHandler() {
                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                                super.onSuccess(statusCode, headers, response);
                                                try {
                                                    for (int i = 0; i < response.length(); i++) {
                                                        int ID = i + 1;
                                                        JSONObject obj = (JSONObject) response.get(i);
                                                        String date = obj.getString("date");
                                                        String distance = obj.getString("sets");
                                                        String time = obj.getString("reps");
                                                        String username = obj.getString("username");
                                                        //STORE RECORDS IN TO THE ARRAY
                                                        treadmillList.add("ID: " + ID + "\n" + "Date: " + date + "\n" + "Time: " + time + "\n" + "Distance: " + distance + "KM" + "Username: " + username);
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