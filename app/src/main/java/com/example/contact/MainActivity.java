package com.example.contact;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;




public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private final static int MESSAGE_UPDATE_TEXT_CHILD_THREAD = 1;
    ArrayList<People> dsPeople;
    ProgressDialog progressDialog;
    private Database database;
    private PeopleDao dao;
    AdapterPeople adapter;
    Toolbar toolbar;
    Spinner spinner;
    RecyclerView rv;
    private Handler updateUIHandler = null;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        database = Database.getInstance(this);

        permission();

        dao = database.dao();
        dsPeople = new ArrayList();

        //giao dien
        rv = findViewById(R.id.recyclerscreen1);
        rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        //Ktra xem co nguoi duoc add hay khong?
        People peoAdd = (People) getIntent().getSerializableExtra("data");

        if (peoAdd != null) {
            peoAdd.setId(getSaltString());
            dao.insert(peoAdd);
        }
        //Ktra xem co nguoi duoc sua hay khong?
        People peopleEdit = (People) getIntent().getSerializableExtra("dataedit");
        if (peopleEdit != null) {
            Intent goi = getIntent();
            Bundle goibundle = goi.getBundleExtra("dataid");
            String id = goibundle.getString("id");
            peopleEdit.setId(id);
            dao.updateUser(peopleEdit);
        }
        for (int i = 0; i < dao.getAll().size(); i++) {
            dsPeople.add(dao.getAll().get(i));
        }
        //loc a-z
        Collections.sort(dsPeople, new Comparator<People>() {

            @Override
            public int compare(People o1, People o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        //end
        adapter = new AdapterPeople(dsPeople, getApplicationContext());
        rv.setAdapter(adapter);
        //add spin
        spinner = findViewById(R.id.spinner);
        ArrayList<String> list = new ArrayList();
        list.add("All");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinner.setAdapter(arrayAdapter);

    }
    private  void permission(){
        int Permission_All = 1;

        String[] Permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE,Manifest.permission.INTERNET };
        if(!hasPermissions(this, Permissions)){
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
        }
    }
    private void createUpdateUiHandler() {
        if (updateUIHandler == null) {
            updateUIHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // Means the message is sent from child thread.
                    if (msg.what == MESSAGE_UPDATE_TEXT_CHILD_THREAD) {
                        adapter = new AdapterPeople(dsPeople, getApplicationContext());
                        rv.setAdapter(adapter);
                    }
                }
            };
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        createUpdateUiHandler();
        if (dao.getAll().size() == 0) {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("Wait");
            progressDialog.setMessage("Process is running");
            progressDialog.setMax(getReadContacts().size());
            progressDialog.setCancelable(false);
            progressDialog.show();

        }
        if (dao.getAll().size() == 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<People> listPeople=getReadContacts();
                    for (int i = 0; i < listPeople.size(); i++) {
                        People peoAddlist = listPeople.get(i);
                        dao.insert(peoAddlist);
                        progressDialog.setProgress(i);
                    }
                    progressDialog.dismiss();

                    for (int i = 0; i < dao.getAll().size(); i++) {
                        dsPeople.add(dao.getAll().get(i));
                        if (i == dao.getAll().size() - 1) {
                            Collections.sort(dsPeople, new Comparator<People>() {

                                @Override
                                public int compare(People o1, People o2) {
                                    return o1.getName().compareTo(o2.getName());
                                }
                            });
                            Message message = new Message();
                            // Set message type.
                            message.what = MESSAGE_UPDATE_TEXT_CHILD_THREAD;
                            // Send message to main thread Handler.
                            updateUIHandler.sendMessage(message);
                        }

                    }
                }
            }).start();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        MenuItem menuitemsearch = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuitemsearch.getActionView();

        searchView.setOnQueryTextListener(this);
        return true;

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addnew:
                Intent add = new Intent(this, AddActivity.class);
                add.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(add);
                break;
            case R.id.action_info   :
                Intent showDetail=new Intent(this,ShowdetailActivity.class);
                startActivity(showDetail);

                break;

        }
        return super.onOptionsItemSelected(item);

    }

    public String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public ArrayList<People> getReadContacts() {
        ArrayList<People> contactList = new ArrayList();
        ContentResolver cr = getContentResolver();
        Cursor mainCursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (mainCursor != null) {
            while (mainCursor.moveToNext()) {
                People peopleadd = new People();
                String id = mainCursor.getString(mainCursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = mainCursor.getString(mainCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String uri = mainCursor.getString(mainCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)) != null ? mainCursor.getString(mainCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)) : "";
                peopleadd.setName(name);
                peopleadd.setUrlPicture(uri);
                peopleadd.setId(id);
                Cursor phoneCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{
                        id
                }, null);
                if (phoneCursor != null) {
                    while (phoneCursor.moveToNext()) {
                        String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        peopleadd.setNumberPhone(phone);
                    }
                }

                Cursor emailCursor = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{
                        id
                }, null);
                if (emailCursor != null) {
                    while (emailCursor.moveToNext()) {
                        String email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        peopleadd.setMail(email);
                    }
                }

                contactList.add(peopleadd);

            }
        }
        return contactList;
    }




    public static boolean hasPermissions(Context context, String... permissions){

        if(Build.VERSION.SDK_INT>=23 && context!=null && permissions!=null){
            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission)!=PackageManager.PERMISSION_GRANTED){
                    return  false;
                }
            }
        }
        return true;
    }



    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {

        String input = newText.toLowerCase();
        ArrayList<People> newList = new ArrayList();
        for (People find : dsPeople) {
            if (find.getName().toLowerCase().contains(input)) {
                newList.add(find);
            }
        }
        adapter.update(newList);
        return true;
    }
}















