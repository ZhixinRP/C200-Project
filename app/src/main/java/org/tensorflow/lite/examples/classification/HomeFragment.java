package org.tensorflow.lite.examples.classification;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


import de.hdodenhof.circleimageview.CircleImageView;



public class HomeFragment extends Fragment {

    TextView tv_username;
    SessionManager sessionManager;
    LinearLayout lyEquipment, lyChart, lyScanner, lyTracker;
    CircleImageView civProfile;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        tv_username = v.findViewById(R.id.tv_username);
        lyEquipment = v.findViewById(R.id.equipmentBtn);
        lyChart = v.findViewById(R.id.chartBtn);
        lyScanner = v.findViewById(R.id.scannerBtn);
        lyTracker = v.findViewById(R.id.trackerBtn);
        civProfile = v.findViewById(R.id.profile_img);

        sessionManager = new SessionManager(getActivity().getApplicationContext());

        String username = sessionManager.getUsername();

        tv_username.setText(username);

        lyEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.flHome, new EquipmentFragment()).commit();
                BottomNavigationView bnv = getActivity().findViewById(R.id.bottomNavigation);
                bnv.setSelectedItemId(R.id.navigation_equipment);
            }
        });

        lyChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.flHome, new ChartFragment()).commit();
                BottomNavigationView bnv = getActivity().findViewById(R.id.bottomNavigation);
                bnv.setSelectedItemId(R.id.navigation_chart);
            }
        });

        lyTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.flHome, new TrackerFragment()).commit();
                BottomNavigationView bnv = getActivity().findViewById(R.id.bottomNavigation);
                bnv.setSelectedItemId(R.id.navigation_tracker);
            }
        });

        lyScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ClassifierActivity.class);
                startActivity(intent);
            }
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult resultIMG = CropImage.getActivityResult(result.getData());
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Uri resultUri = resultIMG.getUriContent();
                        civProfile.setImageURI(resultUri);
                    } else if (result.getResultCode() == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = resultIMG.getError();
                    }
                }
            }
        });

        civProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getActivity()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(getActivity());
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        if(permissionDeniedResponse.isPermanentlyDenied()){
                            androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Permission Required");
                            builder.setMessage("Permission to access your device storage is required to select profile image, Please enable permission to access storage in settings");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.setData(Uri.fromParts("package", getActivity().getPackageName(),null));
                                    activityResultLauncher.launch(intent);
                                }
                            });
                            builder.setNegativeButton("Cancel",null);
                            builder.show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                        .check();
            }
        });

        return v;
    }

}
