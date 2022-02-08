package org.tensorflow.lite.examples.classification;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class TrackerSubFragment extends Fragment {

    Button btnTrackAdd, btnTrackEdit, btnTrackDelete;
    ListView lvTrack;
    ArrayList<Entry> trackList;
    ArrayList<Equipment> equipmentList;
    TrackerAdapter adapter;
    Spinner spinnerTrack;

    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;

    SessionManager sessionManager;

    String ADD_LEGPRESS_URL = UtilityManager.BASE_URL + "c200/addLegpress.php";
    String ALL_LEGPRESS_URL = UtilityManager.BASE_URL + "c200/getLegpress.php?";
    String ADD_LATPULLDOWN_URL = UtilityManager.BASE_URL + "c200/addLatPulldown.php";
    String ALL_LATPULLDOWN_URL = UtilityManager.BASE_URL + "c200/getLatPulldown.php?";
    String ADD_TREADMILL_URL = UtilityManager.BASE_URL + "c200/addTreadmill.php";
    String ALL_TREADMILL_URL = UtilityManager.BASE_URL + "c200/getTreadmill.php?";
    String ADD_TRACK_URL;
    String ALL_TRACK_URL;
    String FILTER_LATPULLDOWN_URL = UtilityManager.BASE_URL + "c200/getLatPulldownFilter.php?";
    String FILTER_LEGPRESS_URL = UtilityManager.BASE_URL + "c200/getLegpressFilter.php?";
    String FILTER_TREADMILL_URL = UtilityManager.BASE_URL + "c200/getTreadmillFilter.php?";
    String FILTER_TRACK_URL;
    String ALL_EQUIPMENT_URL = UtilityManager.BASE_URL + "c200/getAllEquipment.php";
    String EDIT_LATPULLDOWN_URL = UtilityManager.BASE_URL + "c200/EditLatPulldown.php";
    String EDIT_LEGPRESS_URL = UtilityManager.BASE_URL + "c200/EditLegpress.php";
    String EDIT_TRACK_URL;



    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrackerSubFragment() {
        // Required empty public constructor
    }

    public static TrackerSubFragment newInstance(String param1, String param2) {
        TrackerSubFragment fragment = new TrackerSubFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("Trackertab", "Tracker tab onCreateView is run, tab view created is " + mParam1 + " and " + mParam2);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tracker_sub, container, false);
        btnTrackAdd = v.findViewById(R.id.buttonTrackAdd);
        btnTrackEdit = v.findViewById(R.id.buttonTrackEdit);
        btnTrackDelete = v.findViewById(R.id.buttonTrackDelete);
        lvTrack = v.findViewById(R.id.lvTrack);
        spinnerTrack = v.findViewById(R.id.spinnerTrack);
        trackList = new ArrayList<Entry>();
        adapter = new TrackerAdapter(getActivity().getApplicationContext(), R.layout.tracker_layout, trackList);
        lvTrack.setAdapter(adapter);

        equipmentList = new ArrayList<Equipment>();

        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        sessionManager = new SessionManager(getActivity().getApplicationContext());

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

        btnTrackAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mParam2.equalsIgnoreCase("Treadmill")) {

                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewDialog = inflater.inflate(R.layout.add_legpress, null);
                    DatePicker dp_LP_Date = viewDialog.findViewById(R.id.dp_LP_Date);
                    TimePicker tp_LP_Time = viewDialog.findViewById(R.id.tp_LP_Time);
                    EditText et_LP_Weight = viewDialog.findViewById(R.id.et_LP_Weight);
                    EditText et_LP_Reps = viewDialog.findViewById(R.id.et_LP_Reps);
                    EditText et_LP_Sets = viewDialog.findViewById(R.id.et_LP_Sets);

                    dp_LP_Date.setMaxDate(new Date().getTime());

                    //DIALOG POPUP FOR ADDING NEW ENTRY
                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(getActivity());
                    myBuilder.setView(viewDialog);  // Set the view of the dialog
                    if(mParam2.equalsIgnoreCase("Leg Press")) {
                        myBuilder.setTitle("New Leg Press Entry");
                    } else {
                        myBuilder.setTitle("New Lat Pulldown Entry");
                    }
                    myBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(!et_LP_Weight.getText().toString().trim().isEmpty() && !et_LP_Reps.getText().toString().trim().isEmpty() && !et_LP_Sets.getText().toString().trim().isEmpty()) {
                                String date = String.format("%d-%d-%d", dp_LP_Date.getYear(), dp_LP_Date.getMonth()+1, dp_LP_Date.getDayOfMonth());
                                String time = String.format("%d:%d:00", tp_LP_Time.getHour(), tp_LP_Time.getMinute());
                                int sets = Integer.parseInt(et_LP_Sets.getText().toString());
                                int reps = Integer.parseInt(et_LP_Reps.getText().toString());
                                double weight = Double.parseDouble(et_LP_Weight.getText().toString());
                                if(sets <= 100 && reps <= 100 && weight <= 200) {
                                    if(mParam2.equalsIgnoreCase("Leg Press")) {
                                        requestParams.put("equipment", "Leg Press");
                                        ADD_TRACK_URL = ADD_LEGPRESS_URL;
                                        ALL_TRACK_URL = ALL_LEGPRESS_URL;
                                    } else {
                                        requestParams.put("equipment", "Lat Pulldown");
                                        ADD_TRACK_URL = ADD_LATPULLDOWN_URL;
                                        ALL_TRACK_URL = ALL_LATPULLDOWN_URL;
                                    }
                                    requestParams.put("date", date);
                                    requestParams.put("time", time);
                                    requestParams.put("sets", sets);
                                    requestParams.put("reps", reps);
                                    requestParams.put("weight", weight);
                                    requestParams.put("username", sessionManager.getUsername());
                                    Equipment currentEquipmentToAdd = null;
                                    for(int i = 0; i < equipmentList.size(); i++) {
                                        if(equipmentList.get(i).getEqName().equalsIgnoreCase(mParam2)) {
                                            currentEquipmentToAdd = equipmentList.get(i);
                                            break;
                                        }
                                    }
                                    if(weight >= currentEquipmentToAdd.getTarget()) {
                                        requestParams.put("targetHit", 1);
                                    } else {
                                        requestParams.put("targetHit", 0);
                                    }

                                    // ADD THE NEW RECORD INTO THE DATABASE
                                    asyncHttpClient.post(ADD_TRACK_URL, requestParams, new JsonHttpResponseHandler(){
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                            super.onSuccess(statusCode, headers, response);
                                            try {
                                                boolean result = response.getBoolean("result");
                                                if (result) {
                                                    Toast.makeText(getActivity(), "Successfully added entry", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(getActivity(), "Failed to add entry", Toast.LENGTH_SHORT).show();
                                                }

                                                // CLEAR THE LISTVIEW AND GET ALL RECORDS FROM THE DATABASE BASED ON USERNAME (WHEN NEW RECORD ADDED)
                                                trackList.clear();
                                                requestParams.put("username", sessionManager.getUsername());
                                                asyncHttpClient.get(ALL_TRACK_URL, requestParams, new JsonHttpResponseHandler(){
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
                                                                int targetHit = obj.getInt("target_hit");
                                                                boolean targetBool = false;
                                                                if(targetHit == 1) {
                                                                    targetBool = true;
                                                                }
                                                                Equipment currentEquipmentToGet = null;
                                                                for(int x = 0; x < equipmentList.size(); x++) {
                                                                    if(equipmentList.get(x).getEqName().equalsIgnoreCase(mParam2)) {
                                                                        currentEquipmentToGet = equipmentList.get(x);
                                                                        break;
                                                                    }
                                                                }
                                                                Entry currentEntry = new Entry(currentEquipmentToGet, id, date, time, sets, reps, weight, targetBool);
                                                                //STORE RECORDS IN TO THE ARRAY
                                                                trackList.add(currentEntry);
                                                            }
                                                            //UPDATE THE LIST VIEW
                                                            adapter.notifyDataSetChanged();
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
                                    Log.d("entry", "toast not showing");
                                    Toast.makeText(getActivity().getApplicationContext(), "Please fill in appropriate values!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    myBuilder.setNeutralButton("Cancel", null);
                    AlertDialog myDialog = myBuilder.create();
                    myDialog.show();
                } else {
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewDialog = inflater.inflate(R.layout.add_treadmill, null);

                    DatePicker dpTreadmill = viewDialog.findViewById(R.id.dp_Treadmill_Date);
                    TimePicker tpTreadmill = viewDialog.findViewById(R.id.tp_Treadmill_Time);
                    EditText etTreadmillDistance = viewDialog.findViewById(R.id.et_Treadmill_Distance);
                    EditText etTreadmillMinutes = viewDialog.findViewById(R.id.et_Treadmill_Minutes);
                    EditText etTreadmillSeconds = viewDialog.findViewById(R.id.et_Treadmill_Seconds);
                }
            }

        });

        btnTrackEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mParam2.equalsIgnoreCase("Treadmill")) {
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewDialog = inflater.inflate(R.layout.edit_legpress, null);
                    DatePicker edit_dp_LP_Date = viewDialog.findViewById(R.id.edit_dp_LP_Date);
                    TimePicker edit_tp_LP_Time = viewDialog.findViewById(R.id.edit_tp_LP_Time);
                    EditText edit_et_LP_Weight = viewDialog.findViewById(R.id.edit_et_LP_Weight);
                    EditText edit_et_LP_Reps = viewDialog.findViewById(R.id.edit_et_LP_Reps);
                    EditText edit_et_LP_Sets = viewDialog.findViewById(R.id.edit_et_LP_Sets);

                    edit_dp_LP_Date.setMaxDate(new Date().getTime());

                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(getActivity());
                    myBuilder.setView(viewDialog);  // Set the view of the dialog
                    if(mParam2.equalsIgnoreCase("Leg Press")) {
                        myBuilder.setTitle("Edit Leg Press Entry");
                    } else {
                        myBuilder.setTitle("Edit Lat Pulldown Entry");
                    }
                    myBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(!edit_et_LP_Weight.getText().toString().trim().isEmpty() && !edit_et_LP_Reps.getText().toString().trim().isEmpty() && !edit_et_LP_Sets.getText().toString().trim().isEmpty()) {
                                int year = edit_dp_LP_Date.getYear();
                                int month = edit_dp_LP_Date.getMonth()+1;
                                int day = edit_dp_LP_Date.getDayOfMonth();
                                int hour = edit_tp_LP_Time.getHour();
                                int minute = edit_tp_LP_Time.getMinute();
                                String sMonth = Integer.toString(edit_dp_LP_Date.getMonth()+1);
                                String sDay = Integer.toString(edit_dp_LP_Date.getDayOfMonth());
                                String sHour = Integer.toString(edit_tp_LP_Time.getHour());
                                String sMinute = Integer.toString(edit_tp_LP_Time.getMinute());
                                if (month < 10) {
                                    sMonth = "0" + month;
                                }
                                if (day < 10) {
                                    sDay = "0" + day;
                                }
                                if (hour < 10) {
                                    sHour = "0" + hour;
                                }
                                if(minute < 10) {
                                    sMinute = "0" + minute;
                                }
                                String date = String.format("%d-%s-%s", year, sMonth, sDay);
                                String time = String.format("%s:%s:00", sHour, sMinute);
                                Log.d("datetime", "date: " + date + " time: " + time);
                                int editId = -1;
                                for(int i = 0; i < trackList.size(); i++) {
                                    Entry e = trackList.get(i);
                                    if(e.getDate().equalsIgnoreCase(date) && e.getTime().equalsIgnoreCase(time)) {
                                        editId = e.getEntryId();
                                        break;
                                    }
                                }
                                if(editId != -1) {
                                    int sets = Integer.parseInt(edit_et_LP_Sets.getText().toString());
                                    int reps = Integer.parseInt(edit_et_LP_Reps.getText().toString());
                                    double weight = Double.parseDouble(edit_et_LP_Weight.getText().toString());
                                    if(sets <= 100 && reps <= 100 && weight <= 200) {
                                        requestParams.put("record_id", editId);
                                        if(mParam2.equalsIgnoreCase("Leg Press")) {
                                            EDIT_TRACK_URL = EDIT_LEGPRESS_URL;
                                            ALL_TRACK_URL = ALL_LEGPRESS_URL;
                                        } else {
                                            EDIT_TRACK_URL = EDIT_LATPULLDOWN_URL;
                                            ALL_TRACK_URL = ALL_LATPULLDOWN_URL;
                                        }
                                        requestParams.put("sets", sets);
                                        requestParams.put("reps", reps);
                                        requestParams.put("weight", weight);
                                        requestParams.put("username", sessionManager.getUsername());
                                        Equipment currentEquipmentToAdd = null;
                                        for(int i = 0; i < equipmentList.size(); i++) {
                                            if(equipmentList.get(i).getEqName().equalsIgnoreCase(mParam2)) {
                                                currentEquipmentToAdd = equipmentList.get(i);
                                                break;
                                            }
                                        }
                                        if(weight >= currentEquipmentToAdd.getTarget()) {
                                            requestParams.put("targetHit", 1);
                                        } else {
                                            requestParams.put("targetHit", 0);
                                        }

                                        // ADD THE NEW RECORD INTO THE DATABASE
                                        asyncHttpClient.post(EDIT_TRACK_URL, requestParams, new JsonHttpResponseHandler(){
                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                super.onSuccess(statusCode, headers, response);
                                                try {
                                                    boolean result = response.getBoolean("result");
                                                    if (result) {
                                                        Toast.makeText(getActivity(), "Successfully added entry", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(getActivity(), "Failed to add entry", Toast.LENGTH_SHORT).show();
                                                    }

                                                    // CLEAR THE LISTVIEW AND GET ALL RECORDS FROM THE DATABASE BASED ON USERNAME (WHEN NEW RECORD ADDED)
                                                    trackList.clear();
                                                    requestParams.put("username", sessionManager.getUsername());
                                                    asyncHttpClient.get(ALL_TRACK_URL, requestParams, new JsonHttpResponseHandler(){
                                                        @Override
                                                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                                            super.onSuccess(statusCode, headers, response);
                                                            try {
                                                                for(int i=0; i < response.length(); i++) {
                                                                    JSONObject obj = (JSONObject)response.get(i);
                                                                    int id = obj.getInt("record_id");
                                                                    String date = obj.getString("date");
                                                                    String time = obj.getString("time");
                                                                    int sets = obj.getInt("sets");
                                                                    int reps = obj.getInt("reps");
                                                                    double weight = obj.getDouble("weight");
                                                                    int targetHit = obj.getInt("target_hit");
                                                                    boolean targetBool = false;
                                                                    if(targetHit == 1) {
                                                                        targetBool = true;
                                                                    }
                                                                    Equipment currentEquipmentToGet = null;
                                                                    for(int x = 0; x < equipmentList.size(); x++) {
                                                                        if(equipmentList.get(x).getEqName().equalsIgnoreCase(mParam2)) {
                                                                            currentEquipmentToGet = equipmentList.get(x);
                                                                            break;
                                                                        }
                                                                    }
                                                                    Entry currentEntry = new Entry(currentEquipmentToGet, id, date, time, sets, reps, weight, targetBool);
                                                                    //STORE RECORDS IN TO THE ARRAY
                                                                    trackList.add(currentEntry);
                                                                }
                                                                //UPDATE THE LIST VIEW
                                                                adapter.notifyDataSetChanged();
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
                                        Log.d("entry", "toast not showing");
                                        Toast.makeText(getActivity().getApplicationContext(), "Please fill in appropriate values!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Entry does not exist", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    myBuilder.setNeutralButton("Cancel", null);
                    AlertDialog myDialog = myBuilder.create();
                    myDialog.show();
                } else {
                    /*AMOS*/
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewDialog = inflater.inflate(R.layout.add_treadmill, null);

                    DatePicker dpTreadmill = viewDialog.findViewById(R.id.dp_Treadmill_Date);
                    TimePicker tpTreadmill = viewDialog.findViewById(R.id.tp_Treadmill_Time);
                    EditText etTreadmillDistance = viewDialog.findViewById(R.id.et_Treadmill_Distance);
                    EditText etTreadmillMinutes = viewDialog.findViewById(R.id.et_Treadmill_Minutes);
                    EditText etTreadmillSeconds = viewDialog.findViewById(R.id.et_Treadmill_Seconds);

                    dpTreadmill.setMaxDate(new Date().getTime());

                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(getActivity());
                    myBuilder.setView(viewDialog);  // Set the view of the dialog
                    myBuilder.setTitle("Edit Treadmill Entry");


                }
            }
        });

        spinnerTrack.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                requestParams.put("username",sessionManager.getUsername());
                switch(position) {
                    case 0:
                        requestParams.put("filter", "none");
                        break;
                    case 1:
                        requestParams.put("filter", "month");
                        break;
                    case 2:
                        requestParams.put("filter", "year");
                        break;
                    default:
                        Log.d("Track filter duration", "Error with filter spinner position");
                        break;
                }
                switch(mParam2) {
                    case "Leg Press":
                        FILTER_TRACK_URL = FILTER_LEGPRESS_URL;
                        break;
                    case "Lat Pulldown":
                        FILTER_TRACK_URL = FILTER_LATPULLDOWN_URL;
                        break;
                    case "Treadmill":
                        FILTER_TRACK_URL = FILTER_TREADMILL_URL;
                        break;
                    default:
                        Log.d("Track filter equipment", "Error with filter equipment");
                        break;
                }
                trackList.clear();
                asyncHttpClient.get(FILTER_TRACK_URL, requestParams, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            for(int i=0; i < response.length(); i++) {
                                JSONObject obj = (JSONObject)response.get(i);
                                int id = obj.getInt("record_id");
                                String date = obj.getString("date");
                                String time = obj.getString("time");
                                int sets = obj.getInt("sets");
                                int reps = obj.getInt("reps");
                                double weight = obj.getDouble("weight");
                                int targetHit = obj.getInt("target_hit");
                                boolean targetBool = false;
                                if(targetHit == 1) {
                                    targetBool = true;
                                }
                                //STORE RECORDS IN TO THE ARRAY
                                if(equipmentList.isEmpty()) {
                                    equipmentList.clear();
                                    Equipment eq1 = new Equipment(1, "Leg Press", "Sets", "Reps", "Weight", 20);
                                    Equipment eq2 = new Equipment(2, "Lat Pulldown", "Sets", "Reps", "Weight", 15);
                                    Equipment eq3 = new Equipment(2, "Treadmill", "Sets", "Reps", "Weight", 50);
                                    equipmentList.add(eq1);
                                    equipmentList.add(eq2);
                                    equipmentList.add(eq3);
                                }
                                Equipment currentEquipmentToGet = null;
                                for(int x = 0; x < equipmentList.size(); x++) {
                                    if(equipmentList.get(x).getEqName().equalsIgnoreCase(mParam2)) {
                                        currentEquipmentToGet = equipmentList.get(x);
                                        break;
                                    }
                                }
                                Entry currentEntry = new Entry(currentEquipmentToGet, id, date, time, sets, reps, weight, targetBool);
                                trackList.add(currentEntry);
                            }
                            //UPDATE THE LIST VIEW
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }
}