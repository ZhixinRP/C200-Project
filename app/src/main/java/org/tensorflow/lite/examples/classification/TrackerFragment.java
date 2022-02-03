package org.tensorflow.lite.examples.classification;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.tensorflow.lite.examples.classification.databinding.FragmentTrackerBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrackerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackerFragment extends Fragment {

//    Button btnAdd;
//    ListView lvTrack;
//    ArrayList<Workout> workoutList;
//    ArrayAdapter<Workout> aaWorkout;
    private FragmentTrackerBinding binding;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrackerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackerFragment newInstance(String param1, String param2) {
        TrackerFragment fragment = new TrackerFragment();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tracker, container, false);
//        binding = FragmentTrackerBinding.inflate(getLayoutInflater());
//        getActivity().setContentView(binding.getRoot());
//        ViewPager viewPager = binding.viewPager;
//        TabLayout tabs = binding.tabs;
        ViewPager viewPager = v.findViewById(R.id.view_pager);
        TabLayout tabs = v.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        TrackerFragment.SectionsPagerAdapter sectionsPagerAdapter = new TrackerFragment.SectionsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
//        btnAdd = v.findViewById(R.id.buttonTrackAdd);
//        lvTrack = v.findViewById(R.id.lvTrack);
//        workoutList = new ArrayList<Workout>();
//        // changed context of dbhelper from TrackerActivity.this to getActivity()
//        DBHelper dbh = new DBHelper(getActivity());
//        workoutList.addAll(dbh.getAllWorkout());
//        // changed context of aaWorkout from this to getActivity()
//        aaWorkout = new ArrayAdapter<Workout>(getActivity(), android.R.layout.simple_list_item_1, workoutList);
//        lvTrack.setAdapter(aaWorkout);
//
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // couldn't use getSystemService on it's own, so I added getActivity() at the front. hopefully it works lol
//                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View viewDialog = inflater.inflate(R.layout.add_workout, null);
//
//                // should be final access type
//                DatePicker dpAddDate = viewDialog.findViewById(R.id.dpAddDate);
//                Spinner spinnerAddExercise = viewDialog.findViewById(R.id.spinnerAddExercise);
//                EditText etAddMetric1 = viewDialog.findViewById(R.id.etAddMetric1);
//                EditText etAddMetric2 = viewDialog.findViewById(R.id.etAddMetric2);
//                EditText etAddMetric3 = viewDialog.findViewById(R.id.etAddMetric3);
//                TextView tvAddMetric1 = viewDialog.findViewById(R.id.tvAddMetric1);
//                TextView tvAddMetric2 = viewDialog.findViewById(R.id.tvAddMetric2);
//                TextView tvAddMetric3 = viewDialog.findViewById(R.id.tvAddMetric3);
//                ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
//                exerciseList.addAll(dbh.getAllExercise());
//                // changed context of aaExercise from TrackerActivity.this to getActivity()
//                ArrayAdapter<Exercise> aaExercise = new ArrayAdapter<Exercise>(getActivity(), android.R.layout.simple_list_item_1, exerciseList);
//                spinnerAddExercise.setAdapter(aaExercise);
//
//                spinnerAddExercise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        if(spinnerAddExercise.getSelectedItem() != null) {
//                            Exercise exercise = (Exercise) spinnerAddExercise.getSelectedItem();
//                            if(exercise.getName().equals("Treadmill")) {
//                                tvAddMetric1.setText("Distance (km)");
//                                tvAddMetric2.setText("Timing (hour:min:sec)");
//                                tvAddMetric3.setText("Speed");
//                            } else {
//                                tvAddMetric1.setText("Sets");
//                                tvAddMetric2.setText("Reps");
//                                tvAddMetric3.setText("Weight (kg)");
//                            }
//                        }
//                    }
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });
//
//                // changed context of myBuilder from TrackerActivity.this to getActivity()
//                AlertDialog.Builder myBuilder = new AlertDialog.Builder(getActivity());
//                myBuilder.setView(viewDialog);  // Set the view of the dialog
//                myBuilder.setTitle("New Entry");
//                myBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if(!etAddMetric1.getText().toString().trim().isEmpty() && !etAddMetric2.getText().toString().trim().isEmpty() && !etAddMetric3.getText().toString().trim().isEmpty() && spinnerAddExercise.getSelectedItem() != null) {
//                            String date = String.format("%d/%d/%d", dpAddDate.getDayOfMonth(), dpAddDate.getMonth(), dpAddDate.getYear());
//                            int metric1 = Integer.parseInt(etAddMetric1.getText().toString());
//                            int metric2 = Integer.parseInt(etAddMetric2.getText().toString());
//                            int metric3 = Integer.parseInt(etAddMetric3.getText().toString());
//                            Exercise exercise = (Exercise) spinnerAddExercise.getSelectedItem();
//                            int exerciseId = exercise.getExerciseId();
//                            String exerciseName = exercise.getName();
//
//                            Workout workout = new Workout(exerciseId, exerciseName, date, metric1, metric2, metric3);
//                            long result = dbh.insertWorkout(workout);
//                            // changed context of all the makeText from TrackerActivity.this to getActivity()
//                            if(result != -1) {
//                                Toast.makeText(getActivity(), "Workout entry added", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(getActivity(), "Failed to add workout entry", Toast.LENGTH_SHORT).show();
//                            }
//                            workoutList.clear();
//                            workoutList.addAll(dbh.getAllWorkout());
//                            aaWorkout.notifyDataSetChanged();
//                        } else {
//                            Toast.makeText(getActivity(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//                myBuilder.setNeutralButton("Cancel", null);
//                AlertDialog myDialog = myBuilder.create();
//                myDialog.show();
//            }
//        });
        return v;

    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = TrackerSubFragment.newInstance("tracker", "legpress");
//                    fragment = new TrackerSubFragment();
//                    fragment.getArguments();

                    break;
                case 1:
                    fragment = TrackerSubFragment.newInstance("tracker", "latpulldown");
//                    fragment = new TrackerSubFragment();

                    break;
                case 2:
                    fragment = TrackerSubFragment.newInstance("tracker", "treadmill");
//                    fragment = new TrackerSubFragment();

                    break;
                default:
                    fragment = new HomeFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Leg Press";
                case 1:
                    return "Lat Pulldown";
                case 2:
                    return "Treadmill";
                default:
                    return "Error";
            }


        }
    }
}