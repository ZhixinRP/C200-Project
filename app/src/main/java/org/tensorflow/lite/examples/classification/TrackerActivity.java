package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TrackerActivity extends AppCompatActivity {

    Button btnAdd,btnMenuHome,btnMenuTrack, btnMenuScan, btnMenuProgress, btnMenuProfile;
    ListView lvTrack;
    ArrayList<Workout> workoutList;
    ArrayAdapter<Workout> aaWorkout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        btnAdd = findViewById(R.id.buttonTrackAdd);
        btnMenuHome = findViewById(R.id.buttonMenuHome);
        btnMenuTrack = findViewById(R.id.buttonMenuTrack);
        btnMenuScan = findViewById(R.id.buttonMenuScan);
        btnMenuProgress = findViewById(R.id.buttonMenuProgress);
        btnMenuProfile = findViewById(R.id.buttonMenuProfile);
        lvTrack = findViewById(R.id.lvTrack);
        workoutList = new ArrayList<Workout>();
        DBHelper dbh = new DBHelper(TrackerActivity.this);
        workoutList.addAll(dbh.getAllWorkout());
        aaWorkout = new ArrayAdapter<Workout>(this, android.R.layout.simple_list_item_1, workoutList);
        lvTrack.setAdapter(aaWorkout);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.add_workout, null);

                // should be final access type
                DatePicker dpAddDate = viewDialog.findViewById(R.id.dpAddDate);
                Spinner spinnerAddExercise = viewDialog.findViewById(R.id.spinnerAddExercise);
                EditText etAddMetric1 = viewDialog.findViewById(R.id.etAddMetric1);
                EditText etAddMetric2 = viewDialog.findViewById(R.id.etAddMetric2);
                EditText etAddMetric3 = viewDialog.findViewById(R.id.etAddMetric3);
                TextView tvAddMetric1 = viewDialog.findViewById(R.id.tvAddMetric1);
                TextView tvAddMetric2 = viewDialog.findViewById(R.id.tvAddMetric2);
                TextView tvAddMetric3 = viewDialog.findViewById(R.id.tvAddMetric3);
                ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
                exerciseList.addAll(dbh.getAllExercise());
                ArrayAdapter<Exercise> aaExercise = new ArrayAdapter<Exercise>(TrackerActivity.this, android.R.layout.simple_list_item_1, exerciseList);
                spinnerAddExercise.setAdapter(aaExercise);

                spinnerAddExercise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(spinnerAddExercise.getSelectedItem() != null) {
                            Exercise exercise = (Exercise) spinnerAddExercise.getSelectedItem();
                            if(exercise.getName().equals("Treadmill")) {
                                tvAddMetric1.setText("Distance (km)");
                                tvAddMetric2.setText("Timing (hour:min:sec)");
                                tvAddMetric3.setText("Speed");
                            } else {
                                tvAddMetric1.setText("Sets");
                                tvAddMetric2.setText("Reps");
                                tvAddMetric3.setText("Weight (kg)");
                            }
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(TrackerActivity.this);
                myBuilder.setView(viewDialog);  // Set the view of the dialog
                myBuilder.setTitle("New Entry");
                myBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!etAddMetric1.getText().toString().trim().isEmpty() && !etAddMetric2.getText().toString().trim().isEmpty() && !etAddMetric3.getText().toString().trim().isEmpty() && spinnerAddExercise.getSelectedItem() != null) {
                            String date = String.format("%d/%d/%d", dpAddDate.getDayOfMonth(), dpAddDate.getMonth(), dpAddDate.getYear());
                            int metric1 = Integer.parseInt(etAddMetric1.getText().toString());
                            int metric2 = Integer.parseInt(etAddMetric2.getText().toString());
                            int metric3 = Integer.parseInt(etAddMetric3.getText().toString());
                            Exercise exercise = (Exercise) spinnerAddExercise.getSelectedItem();
                            int exerciseId = exercise.getExerciseId();
                            String exerciseName = exercise.getName();

                            Workout workout = new Workout(exerciseId, exerciseName, date, metric1, metric2, metric3);
                            long result = dbh.insertWorkout(workout);
                            if(result != -1) {
                                Toast.makeText(TrackerActivity.this, "Workout entry added", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(TrackerActivity.this, "Failed to add workout entry", Toast.LENGTH_SHORT).show();
                            }
                            workoutList.clear();
                            workoutList.addAll(dbh.getAllWorkout());
                            aaWorkout.notifyDataSetChanged();
                        } else {
                            Toast.makeText(TrackerActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                myBuilder.setNeutralButton("Cancel", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btnMenuHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrackerActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}