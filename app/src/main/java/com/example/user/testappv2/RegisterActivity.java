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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.et_Name)EditText etName;
    @BindView(R.id.et_Email)EditText etEmail;
    @BindView(R.id.et_Password)EditText etPassword;
    @BindView(R.id.btn_Register)Button   btnRegister;
    ProgressDialog loadingProgress;

    Context context;
    ApiBaseService apiBaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        context = this;
        apiBaseService = UtilsApi.getAPIService();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingProgress = ProgressDialog.show(context,null,"Loading...Wait Please",true,false);
                requestRegister();
            }
        });
    }

    private void requestRegister() {
        apiBaseService.registerRequest(etName.getText().toString(),etEmail.getText().toString(), etPassword.getText().toString())
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
                                    Toast.makeText(context, "Registration Granted.", Toast.LENGTH_SHORT).show();
                                    String name = jsonResult.getJSONObject("user").getString("name");
                                    startActivity(new Intent(context, LoginActivity.class));
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
                        Toast.makeText(context,"Somethings wrong with Internet connection",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
