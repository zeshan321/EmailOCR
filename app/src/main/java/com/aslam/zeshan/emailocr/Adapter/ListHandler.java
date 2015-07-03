package com.aslam.zeshan.emailocr.Adapter;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import com.aslam.zeshan.emailocr.Database.EmailDatabase;
import com.aslam.zeshan.emailocr.R;

import java.util.HashMap;

public class ListHandler {

    public static EmailsArrayAdapter emailsArrayAdapater;
    public static ListView listView;

    Context con;

    public ListHandler(Context con) {
        this.con = con;
    }

    public void initialSetup() {
        emailsArrayAdapater = new EmailsArrayAdapter(con, R.layout.email_list_view);
        listView = (ListView) ((Activity) con).findViewById(R.id.emailList);

        EmailDatabase emailDatabase = new EmailDatabase(con);

        HashMap<String, String> map = emailDatabase.getEmails();
        for (String key : map.keySet()) {
            emailsArrayAdapater.add(new EmailObject(key, map.get(key), false));
        }

        listView.setAdapter(emailsArrayAdapater);
    }

    public void add(String name, String email) {
        emailsArrayAdapater.add(new EmailObject(name, email, false));

        emailsArrayAdapater.notifyDataSetChanged();
    }
}
