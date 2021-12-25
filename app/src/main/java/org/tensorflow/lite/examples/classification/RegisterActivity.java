package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {

    EditText et_register_username, et_register_email, et_register_password , et_confirm_password;
    Button btnRegister;
    TextView tv_to_login;

    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;

    String ADD_USER_URL = "http://10.0.2.2/c200/addUser.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_register_username = findViewById(R.id.et_register_username);
        et_register_email = findViewById(R.id.et_register_email);
        et_register_password = findViewById(R.id.et_register_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        btnRegister = findViewById(R.id.btn_register);
        tv_to_login = findViewById(R.id.tv_to_login);

        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = et_register_username.getText().toString();
                String email = et_register_email.getText().toString();
                String password = et_register_password.getText().toString();
                String confirm_password = et_confirm_password.getText().toString();

                if (username.equals("") || email.equals("") || password.equals("") || confirm_password.equals("")) {

                    Toast.makeText(RegisterActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();

                } else {

                    requestParams.put("username", username);
                    requestParams.put("email", email);
                    requestParams.put("password", password);
                    requestParams.put("con_password", confirm_password);

                    asyncHttpClient.post(ADD_USER_URL, requestParams, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);

                            try {
                                Integer result = response.getInt("result");

                                if (result == 1) {
                                    Toast.makeText(RegisterActivity.this, "Sorry.. Username already exists", Toast.LENGTH_SHORT).show();
                                } else if (result == 2) {
                                    Toast.makeText(RegisterActivity.this, "Sorry.. Email already exists", Toast.LENGTH_SHORT).show();
                                } else if (result == 3) {
                                    Toast.makeText(RegisterActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                                } else if (result == 4) {
                                    Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }

            }
        });

        tv_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}