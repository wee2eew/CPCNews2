package com.example.hp.cpcnews;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    //TODO
                    //ติดต่อกับ server
                    //เรียกใช้คลาสที่เขียน
                    new Login(etUsername.getText().toString(),
                            etPassword.getText().toString()).execute();
                }else {
                    Toast.makeText(LoginActivity.this,"กรุณากรอกข้อมูลให้ครบถ้วน",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validate() {
        if(etUsername.getText().toString().isEmpty()) return false;
        if(etPassword.getText().toString().isEmpty()) return false;
        return true;
    }

    private class Login extends AsyncTask<Void, Void, String> {
        private String user;
        private String password;

        public Login(String user, String password) {
            this.user = user;
            this.password = password;
        }

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            Request request;
            Response response;

            //สร้างตัวส่งข้อมูลคือ data ใช้ RequestBody
            RequestBody requestBody = new FormBody.Builder()
                    .add("username", user)
                    .add("password", password)
                    .build();

            //บอกว่าจะส่งไปที่ด้วย ด้วย method post แล้วส่งข้อมูลอะไรไปคือ requestbdy
            request = new Request.Builder()
                    .url("http://kimhun55.com/pollservices/login.php")
                    .post(requestBody)
                    .build();

            try {
                response = client.newCall(request).execute();

                //ต้องเชค respond เป็น 200 หรือป่าว
                if (response.isSuccessful()) {

                    return response.body().string();
                }else {

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();

            try {
                JSONObject rootObj = new JSONObject(s);
                if(rootObj.has("result")) {
                    JSONObject resultObj = rootObj.getJSONObject("result");
                    if (resultObj.getInt("result") ==1) {
                        Toast.makeText(LoginActivity.this, resultObj.getString("result_desc"),Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(LoginActivity.this, NewsActivity.class);
                        startActivity(i);
                    }else {
                        Toast.makeText(LoginActivity.this, resultObj.getString("result_desc"),Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
