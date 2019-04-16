package com.example.contact;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface SOService {
    @GET("services/get-topics?project_code=C026&username=rta_lamtran")
    Call<Data> getAnswers();

    @POST("services/subscribe-topic?topic_name=&project_code=C026&username=rta_lamtran")
    Call<RequestBody> savePost(@Query("topic_name") String topic, @Body RequestBody requestBody);

}
