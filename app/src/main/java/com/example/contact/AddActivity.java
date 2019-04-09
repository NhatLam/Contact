package com.example.contact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAdd;
    EditText edName,edNumberphone,edMail;
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpeople);
        btnAdd=findViewById(R.id.btn_submit);
        edMail=findViewById(R.id.ed_mail);
        edName=findViewById(R.id.ed_name);
        edNumberphone=findViewById(R.id.ed_numberphone);

        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_submit){
            People peo=new People();
            peo.setName(edName.getText().toString());
            peo.setNumberPhone(edNumberphone.getText().toString());
            peo.setMail(edMail.getText().toString());
            Intent add=new Intent(this,MainActivity.class);

             add.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             add.putExtra("data",peo);

            startActivity(add);


        }
    }
}
