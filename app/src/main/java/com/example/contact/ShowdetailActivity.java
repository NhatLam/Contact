package com.example.contact;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowdetailActivity extends AppCompatActivity {
    private SOService mService;
    AdapterShowtopic adapterShowtopic;
    RecyclerView rvDetail;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtask);
        mService = ApiUtils.getSOService();

        rvDetail = findViewById(R.id.recyler_newtask);
        rvDetail.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDetail.setLayoutManager(layoutManager);

        loadData();

    }


//Code Okhttp de dung ve sau
//    private void sendData() {
//        final MediaType mediaType
//                = MediaType.parse("application/json");
//
//        OkHttpClient httpClient = new OkHttpClient();
//        String url = "https://rtcenter.org/services/subscribe-topic?topic_name=test_hr_inout&project_code=C026&username=rta_lamtran";
//
//        String jsonStr = "{\"status\" : 1} " ;
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(RequestBody.create(mediaType, jsonStr))
//                .build();
//        okhttp3.Response response = null;
//        try {
//            response = httpClient.newCall(request).execute();
//            if (response.isSuccessful()) {
//                Log.e("a", "Got response from server for JSON post using OkHttp ");
//            }
//
//        } catch (IOException e) {
//            Log.e("a", "error in getting response for json post request okhttp");
//        }
//    }


    private void loadData() {

        mService.getAnswers().enqueue(new Callback<Data>() {

            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {

                ArrayList<Topic> tops= new ArrayList();
                tops.addAll(response.body().getTopics());
                adapterShowtopic=new AdapterShowtopic(tops,ShowdetailActivity.this);
                rvDetail.setAdapter(adapterShowtopic);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(ShowdetailActivity.this, "" + t, Toast.LENGTH_SHORT).show();
            }
        });

    }


}