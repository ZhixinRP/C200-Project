package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LatPulldownActivity extends AppCompatActivity {

    Button btnBack1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lat_pulldown);
        btnBack1 = findViewById(R.id.btnBack1);

        btnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LatPulldownActivity.this, ClassifierActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}