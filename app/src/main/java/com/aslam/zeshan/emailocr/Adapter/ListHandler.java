package com.aslam.zeshan.emailocr.Adapter;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import com.aslam.zeshan.emailocr.Database.EmailDatabase;
import com.aslam.zeshan.emailocr.R;

public class ListHandler {

    protected static EmailsArrayAdapater emailsArrayAdapater;
    protected static ListView listView;

    Context con;

    public ListHandler(Context con) {
        this.con = con;
    }

    public void initialSetup() {
        emailsArrayAdapater = new EmailsArrayAdapater(con, R.layout.email_list_view);
        listView = (ListView) ((Activity)con).findViewById(R.id.emailList);

        EmailDatabase emailDatabase = new EmailDatabase(con);

        for (String s: emailDatabase.getEmails()) {
            emailsArrayAdapater.add(new EmailObject(s, false));
        }

        listView.setAdapter(emailsArrayAdapater);
    }

    public void add(String email) {
        emailsArrayAdapater.add(new EmailObject(email, false));

        emailsArrayAdapater.notifyDataSetChanged();
    }
}
