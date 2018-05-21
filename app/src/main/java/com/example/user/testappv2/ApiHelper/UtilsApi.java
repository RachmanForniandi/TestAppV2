package com.example.user.testappv2.ApiHelper;

public class UtilsApi {
    public static final String API_BASE_URL ="http://192.168.1.10/kepoo/";

    public static ApiBaseService getAPIService(){
        return RetrofitClient.getClient(API_BASE_URL).create(ApiBaseService.class);
    }
}
