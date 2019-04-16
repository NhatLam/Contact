package com.example.contact;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdapterShowtopic extends RecyclerView.Adapter<AdapterShowtopic.ViewHolder> {
    List<Topic> topics;
    Context context;

    public AdapterShowtopic(List<Topic> topics, Context context) {
        this.topics = topics;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_showdetail, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.tvName.setText(topics.get(i).getName());
        viewHolder.tvTitle.setText(topics.get(i).getTitle());
        viewHolder.tvDes.setText(topics.get(i).getDescription());
        if (topics.get(i).getStatus().compareTo(1)==0) {
            viewHolder.checkSta.setChecked(true);
        }
        else {
            viewHolder.checkSta.setChecked(false);
        }
    }


    @Override
    public int getItemCount() {
        if (topics == null) {
            return 0;
        } else {
            return topics.size();
        }

    }


    class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        TextView tvName, tvTitle, tvDes;
        Switch checkSta;
        private SOService mService;

        ViewHolder(View itemView) {
            super(itemView);
            mService = ApiUtils.getSOService();

            tvName = itemView.findViewById(R.id.tv_name);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDes = itemView.findViewById(R.id.tv_description);
            checkSta = itemView.findViewById(R.id.tv_statusin);

            checkSta.setOnCheckedChangeListener(this);

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(getAdapterPosition()==0) {
                if (isChecked) {
                    String json = "{\"status\" : 1}";
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
                    mService.savePost("test_hr_inout",requestBody).enqueue(new Callback<RequestBody>() {
                        @Override
                        public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                        }

                        @Override
                        public void onFailure(Call<RequestBody> call, Throwable t) {
                        }
                    });
                } else {
                    String json = "{\"status\" : 0}";
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
                    mService.savePost("test_hr_inout",requestBody).enqueue(new Callback<RequestBody>() {
                        @Override
                        public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                        }

                        @Override
                        public void onFailure(Call<RequestBody> call, Throwable t) {
                        }
                    });
                }
            }
            if(getAdapterPosition()==1) {
                if (isChecked) {
                    String json = "{\"status\" : 1}";
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
                    mService.savePost("test_hr_leave",requestBody).enqueue(new Callback<RequestBody>() {
                        @Override
                        public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                        }

                        @Override
                        public void onFailure(Call<RequestBody> call, Throwable t) {
                        }
                    });
                } else {
                    String json = "{\"status\" : 0}";
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
                    mService.savePost("test_hr_leave",requestBody).enqueue(new Callback<RequestBody>() {
                        @Override
                        public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                        }

                        @Override
                        public void onFailure(Call<RequestBody> call, Throwable t) {
                        }
                    });
                }
            }
        }
    }
}
