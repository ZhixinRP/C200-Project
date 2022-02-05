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

import java.util.ArrayList;

public class TrackerFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tracker, container, false);
        ViewPager viewPager = v.findViewById(R.id.view_pager);
        TabLayout tabs = v.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        TrackerFragment.SectionsPagerAdapter sectionsPagerAdapter = new TrackerFragment.SectionsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
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

                    break;
                case 1:
                    fragment = TrackerSubFragment.newInstance("tracker", "latpulldown");

                    break;
                case 2:
                    fragment = TrackerSubFragment.newInstance("tracker", "treadmill");

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