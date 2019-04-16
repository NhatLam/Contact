package com.example.contact;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class InfoDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView urlPic;
    ImageView imgMail;
    ImageView btnBack;
    ImageView imgVip;
    ImageView imgPhone;
    TextView tvName;
    TextView tvNumberphone;
    TextView tvEmail;
    ImageView imgEdit;
    People people;
    String viTri;
    MainActivity main;
    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infodetail);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        urlPic = findViewById(R.id.img_backgroundscreen2);
        imgMail = findViewById(R.id.img_mail);
        tvName = findViewById(R.id.tv_namescreen2);
        tvNumberphone = findViewById(R.id.tv_number);
        tvEmail = findViewById(R.id.tv_mail);
        imgEdit = findViewById(R.id.btn_edit);
        imgPhone=findViewById(R.id.img_numberphone);
        people = (People) getIntent().getSerializableExtra("data");
        Intent nhanvitri = getIntent();
        Bundle nhandata = nhanvitri.getBundleExtra("dataid");

        viTri = nhandata.getString("id");
        tvName.setText(people.getName());
        tvNumberphone.setText(people.getNumberPhone());
        tvEmail.setText(people.getMail());
        imgVip = findViewById(R.id.btn_vip);
        if (people.getMail() == null) {
            imgMail.setVisibility(View.INVISIBLE);
        }
        if (people.getUrlPicture() == null || people.getUrlPicture().isEmpty()) {
            urlPic.setImageResource(R.drawable.aaa);

        } else {

            people.setUrlPicture(people.getUrlPicture());
        }

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
        imgPhone.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_back) {
            Intent screen1 = new Intent(this, MainActivity.class);

            startActivity(screen1);

        }
        if (v.getId() == R.id.btn_edit) {
            Intent screenedit = new Intent(this, EditpeopleActivity.class);
            screenedit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            screenedit.putExtra("data1", people);
            Bundle posi = new Bundle();
            posi.putString("id", viTri);
            screenedit.putExtra("dataid", posi);
            startActivity(screenedit);

        }
        if(v.getId()==R.id.img_numberphone){

            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tvNumberphone.getText()));
            startActivity(intent);
        }
    }
}
