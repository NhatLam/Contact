package com.example.contact;

import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.util.ArrayList;

public class AdapterPeople extends RecyclerView.Adapter<AdapterPeople.ViewHolder> {


    ArrayList<People> dsPeople;
    Context context;

    public AdapterPeople(ArrayList<People> dsPeople, Context context) {
        super();
        this.dsPeople = dsPeople;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewgroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewgroup.getContext());
        View itemView=layoutInflater.inflate(R.layout.item_people,viewgroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {
        viewHolder.tvname.setText(dsPeople.get(i).name);

        if( !(dsPeople.get(i).urlPicture ==null) && !dsPeople.get(i).urlPicture.isEmpty() )        {

            Uri myUri = Uri.parse(dsPeople.get(i).urlPicture);

            viewHolder.imgavatar.setImageURI(myUri);
        }
        else {
            viewHolder.imgavatar.setImageResource(R.drawable.empty);

        }

    }
    public void update(ArrayList<People> newList){
        dsPeople=new ArrayList();
        dsPeople.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dsPeople.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgavatar;
        TextView tvname;


        RelativeLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            imgavatar=itemView.findViewById(R.id.img_person);
            tvname=itemView.findViewById(R.id.tv_name);
            layout=itemView.findViewById(R.id.layout);
            layout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.layout)
            {
                Intent screen2=new Intent(context,SecondActivity.class);
                screen2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                screen2.putExtra("data",dsPeople.get(getAdapterPosition()));

                Bundle posi=new Bundle();
                posi.putString("id",dsPeople.get(getAdapterPosition()).getId());
                screen2.putExtra("dataid",posi);
                context.startActivity(screen2);

            }
        }
    }
}
