package org.tensorflow.lite.examples.classification;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

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
    LinearLayout lyEquipment, lyChart;
    CircleImageView civProfile;

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
                                    startActivityForResult(intent, 51);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUriContent();
                civProfile.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}
