package com.example.hp.cpcnews;

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

public class RegisterActivity extends AppCompatActivity {

    private EditText etDisplayName;
    private EditText etConfirm;
    private EditText etPassword;
    private EditText etUsername;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etDisplayName = (EditText)findViewById(R.id.etDisplayname);
        etConfirm = (EditText)findViewById(R.id.etConfirm);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etUsername = (EditText)findViewById(R.id.etUsername);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    //TODO
                    //ติดต่อกับ server
                    //เรียกใช้คลาสที่เขียน
                    new Register(etUsername.getText().toString(),
                            etPassword.getText().toString(),
                            etConfirm.getText().toString(),
                            etDisplayName.getText().toString()).execute();
                }else {
                    Toast.makeText(RegisterActivity.this,"กรุณากรอกข้อมูลให้ครบถ้วน",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validate() {
        if(etUsername.getText().toString().isEmpty()) return false;
        if(etPassword.getText().toString().isEmpty()) return false;
        if(!etConfirm.getText().toString().equals(etPassword.getText().toString())) return false;
        if(etDisplayName.getText().toString().isEmpty()) return false;
        return true;
    }

    private class Register extends AsyncTask<Void, Void, String> {
        private String user;
        private String password;
        private String confirm;
        private String display;

        public Register(String user, String password, String confirm, String display) {
            this.user = user;
            this.password = password;
            this.confirm = confirm;
            this.display = display;
        }

        @Override
        protected String doInBackground(Void... voids) {
            //เขียนการเชื่อมต่อ server
            //add library ok
            OkHttpClient client = new OkHttpClient();
            Request request;
            Response response;

            //สร้างตัวส่งข้อมูลคือ data ใช้ RequestBody
            RequestBody requestBody = new FormBody.Builder()
                    .add("username", user)
                    .add("password", password)
                    .add("password_con", confirm)
                    .add("display_name", display)
                    .build();

            //บอกว่าจะส่งไปที่ด้วย ด้วย method post แล้วส่งข้อมูลอะไรไปคือ requestbdy
            request = new Request.Builder()
                    .url("http://kimhun55.com/pollservices/signup.php")
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();

            try {
                JSONObject rootObj = new JSONObject(s);
                if(rootObj.has("result")) {
                    JSONObject resultObj = rootObj.getJSONObject("result");
                    if (resultObj.getInt("result") ==1) {
                        Toast.makeText(RegisterActivity.this, resultObj.getString("result_desc"),Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(RegisterActivity.this, resultObj.getString("result_desc"),Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}















