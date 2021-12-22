package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {

    EditText et_register_username, et_register_email, et_register_password;
    Button btnRegister;

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
        btnRegister = findViewById(R.id.btn_register);

        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();

    }
}