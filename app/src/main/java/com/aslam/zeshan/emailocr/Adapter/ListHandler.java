package com.aslam.zeshan.emailocr.Adapter;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import com.aslam.zeshan.emailocr.Database.EmailDatabase;
import com.aslam.zeshan.emailocr.R;

public class ListHandler {

    public static EmailsArrayAdapter emailsArrayAdapater;
    public static ListView listView;

    Context con;

    public ListHandler(Context con) {
        this.con = con;
    }

    public void initialSetup(boolean type) {
        if (!type) {
            emailsArrayAdapater = new EmailsArrayAdapter(con, R.layout.email_list_view);
            listView = (ListView) ((Activity) con).findViewById(R.id.emailList);
        } else {
            emailsArrayAdapater = new EmailsArrayAdapter(con, R.layout.email_list_view);
            listView = (ListView) ((Activity) con).findViewById(R.id.sendList);
        }

        EmailDatabase emailDatabase = new EmailDatabase(con);

        for (EmailObject emailObject : emailDatabase.getEmails()) {
            emailsArrayAdapater.add(emailObject);
        }

        listView.setAdapter(emailsArrayAdapater);
    }

    public void add(int ID, String name, String email) {
        emailsArrayAdapater.add(new EmailObject(ID, name, email, false));

        emailsArrayAdapater.notifyDataSetChanged();
    }
}
