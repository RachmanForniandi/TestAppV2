package com.example.user.testappv2.ApiHelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiBaseService {
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody>loginRequest(@Field("email") String email,
                                   @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody>registerRequest(@Field("name") String name,
                                      @Field("email") String email,
                                      @Field("password") String password);
}
