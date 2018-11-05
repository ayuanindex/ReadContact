package com.ayuan.readcontact;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Contact> contacts = ReadContastUtils.readContact(this);
        Iterator<Contact> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            Contact next = iterator.next();
            String id = next.getId();
            String name = next.getName();
            String phone = next.getPhone();
            String email = next.getEmail();
            String address = next.getAddress();
            Log.i(TAG, "\nid:" + id + "\nname:" + name + "\nphone:" + phone + "\nemail:" + email + "\naddress:" + address);
        }
    }
}
