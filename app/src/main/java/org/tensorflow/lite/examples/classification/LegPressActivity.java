package org.tensorflow.lite.examples.classification;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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


public class LegPressActivity extends AppCompatActivity {

    ListView lvLegPress;
    Button btnAddLegPress;
    ArrayList legPressList;
    ArrayAdapter aaLegPress;
    String lastActivity;

    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;

    SessionManager sessionManager;

    String ADD_LEGPRESS_URL = UtilityManager.BASE_URL + "c200/addLegpress.php";
    String ALL_LEGPRESS_URL = UtilityManager.BASE_URL + "c200/getLegpress.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leg_press);
        getSupportActionBar().setTitle("Leg Press");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent get = getIntent();
        lastActivity = get.getStringExtra("last");

        lvLegPress = findViewById(R.id.lvLegPress);
        btnAddLegPress = findViewById(R.id.btn_Add_LegPress);

        sessionManager = new SessionManager(getApplicationContext());

        legPressList = new ArrayList();
        aaLegPress = new ArrayAdapter(this, android.R.layout.simple_list_item_1, legPressList);
        lvLegPress.setAdapter(aaLegPress);

        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();

        // CLEAR THE LISTVIEW AND GET ALL RECORDS FROM THE DATABASE BASED ON USERNAME (WHEN ACTIVITY LOADED)
        legPressList.clear();
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
                        //STORE RECORDS IN TO THE ARRAY
                        legPressList.add("ID: " + ID + "\n" + "Date: " + date + "\n" + "Sets: " + sets + "\n" + "Reps: " + reps + "\n" + "Weight: " + weight + "KG");
                    }
                    //UPDATE THE LIST VIEW
                    aaLegPress.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        btnAddLegPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.add_legpress, null);

                DatePicker dp_LP_Date = viewDialog.findViewById(R.id.dp_LP_Date);
                EditText et_LP_Weight = viewDialog.findViewById(R.id.et_LP_Weight);
                EditText et_LP_Reps = viewDialog.findViewById(R.id.et_LP_Reps);
                EditText et_LP_Sets = viewDialog.findViewById(R.id.et_LP_Sets);

                //DIALOG POPUP FOR ADDING NEW ENTRY
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(LegPressActivity.this);
                myBuilder.setView(viewDialog);  // Set the view of the dialog
                myBuilder.setTitle("New Leg Press Entry");
                myBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!et_LP_Weight.getText().toString().trim().isEmpty() && !et_LP_Reps.getText().toString().trim().isEmpty() && !et_LP_Sets.getText().toString().trim().isEmpty()) {
                            if (Integer.parseInt(et_LP_Weight.getText().toString()) <= 200 && Integer.parseInt(et_LP_Reps.getText().toString()) <= 50 && Integer.parseInt(et_LP_Sets.getText().toString()) <= 10) {
                                String date = String.format("%d/%d/%d", dp_LP_Date.getDayOfMonth(), dp_LP_Date.getMonth()+1, dp_LP_Date.getYear());
                                requestParams.put("date", date);
                                requestParams.put("weight", Integer.parseInt(et_LP_Weight.getText().toString()));
                                requestParams.put("reps", Integer.parseInt(et_LP_Reps.getText().toString()));
                                requestParams.put("sets", Integer.parseInt(et_LP_Sets.getText().toString()));
                                requestParams.put("equipment", "Leg Press");
                                requestParams.put("username", sessionManager.getUsername());

                                // ADD THE NEW RECORD INTO THE DATABASE
                                asyncHttpClient.post(ADD_LEGPRESS_URL, requestParams, new JsonHttpResponseHandler(){
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        super.onSuccess(statusCode, headers, response);
                                        try {
                                            boolean result = response.getBoolean("result");
                                            if (result) {
                                                Toast.makeText(LegPressActivity.this, "Added Succesfully", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(LegPressActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                            }

                                            // CLEAR THE LISTVIEW AND GET ALL RECORDS FROM THE DATABASE BASED ON USERNAME (WHEN NEW RECORD ADDED)
                                            legPressList.clear();
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
                                                            legPressList.add("ID: " + ID + "\n" + "Date: " + date + "\n" + "Sets: " + sets + "\n" + "Reps: " + reps + "\n" + "Weight: " + weight + "\n" + "Username: " + username);
                                                        }
                                                        //UPDATE THE LIST VIEW
                                                        aaLegPress.notifyDataSetChanged();
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
                                Toast.makeText(LegPressActivity.this, "Please enter valid value", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LegPressActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
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
                String videoId = "XNvaNipSycI";
                youTubePlayer.cueVideo(videoId, 0);
            }
        });

        // SCROLLABLE LISTVIEW
        lvLegPress.setOnTouchListener(new ListView.OnTouchListener() {
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
        if(lastActivity.equalsIgnoreCase("scan")) {
            Intent intent = new Intent(LegPressActivity.this, ClassifierActivity.class);
            startActivity(intent);
        }
        finish();
        return true;
    }

}