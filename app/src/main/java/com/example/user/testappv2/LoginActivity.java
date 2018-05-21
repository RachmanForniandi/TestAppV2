package com.example.user.testappv2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.testappv2.ApiHelper.ApiBaseService;
import com.example.user.testappv2.ApiHelper.UtilsApi;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail;
    EditText etPassword;
    Button btnLogin;
    TextView txtRegister;
    ProgressDialog loadingProgress;

    Context context;
    ApiBaseService apiBaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        context = this;
        apiBaseService = UtilsApi.getAPIService();
        initComponents();
    }

    private void initComponents() {
        etEmail = (EditText) findViewById(R.id.et_Email);
        etPassword =(EditText)findViewById(R.id.et_Password);
        btnLogin = (Button) findViewById(R.id.btn_Login);
        txtRegister = (TextView)findViewById(R.id.sign_up_order);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingProgress = ProgressDialog.show(context,null,"Loading...Wait Please",true,false);
                requestLogin();
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, RegisterActivity.class));
            }
        });
    }

    private void requestLogin() {
        apiBaseService.loginRequest(etEmail.getText().toString(), etPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: Success");
                            loadingProgress.dismiss();
                            try {
                                JSONObject jsonResult = new JSONObject(response.body().string());
                                if (jsonResult.getString("error").equals("false")) {
                                    // Jika login berhasil maka data nama yang ada di response API
                                    // akan diparsing ke activity selanjutnya
                                    Toast.makeText(context, "Access Granted.", Toast.LENGTH_SHORT).show();
                                    String name = jsonResult.getJSONObject("user").getString("name");
                                    Intent intent = new Intent(context, MainActivity.class);
                                    intent.putExtra("result_name", name);
                                    startActivity(intent);
                                } else {
                                    String error_message = jsonResult.getString("error_msg");
                                    Toast.makeText(context, error_message, Toast.LENGTH_SHORT).show();
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }else {
                            Log.i("debug","onResponse: Failure");
                            loadingProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug","onFailure: ERROR >" + t.toString());
                        loadingProgress.dismiss();
                    }
                });
    }
}
