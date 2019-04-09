package com.example.contact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditpeopleActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edEdname,edEdnumberphone,edEdmail;
    Button btnOk;
    People peopleedit;
    String id;

    @Override
    protected void onStart() {
        super.onStart();

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpeople);
        edEdname=findViewById(R.id.ed_edname);
        edEdnumberphone=findViewById(R.id.ed_ednumberphone);
        edEdmail=findViewById(R.id.ed_edmail);
        btnOk=findViewById(R.id.btn_edsubmit);
        peopleedit= (People) getIntent().getSerializableExtra("data1");
        edEdmail.setText(peopleedit.getMail());
        edEdnumberphone.setText(peopleedit.getNumberPhone());
        edEdname.setText(peopleedit.getName());

        Intent nhanvitri=getIntent();
        Bundle nhandata= nhanvitri.getBundleExtra("dataid");

        id=nhandata.getString("id");




        btnOk.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_edsubmit)
        {
            People people=new People();
            people.setName(edEdname.getText().toString());
            people.setNumberPhone(edEdnumberphone.getText().toString());
            people.setMail(edEdmail.getText().toString());


            Intent screenmain=new Intent(this,MainActivity.class);
            screenmain.putExtra("dataedit",people);

            Bundle posi=new Bundle();
            posi.putString("id",id);
            screenmain.putExtra("dataid",posi);

            startActivity(screenmain);

        }
    }
}
