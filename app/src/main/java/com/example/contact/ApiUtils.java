package com.example.contact;

public class ApiUtils {
    public static final String BASE_URL = "https://rtcenter.org/";


    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
