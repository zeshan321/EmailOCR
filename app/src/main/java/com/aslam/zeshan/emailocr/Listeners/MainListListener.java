package com.aslam.zeshan.emailocr.Listeners;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aslam.zeshan.emailocr.Adapter.EmailObject;
import com.aslam.zeshan.emailocr.Adapter.ListHandler;
import com.aslam.zeshan.emailocr.Dialog.OptionsDialog;
import com.aslam.zeshan.emailocr.R;

public class MainListListener {

    Context con;

    public MainListListener(Context con) {
        this.con = con;
    }

    public void onClick() {
        ListView listView = (ListView) ((Activity) con).findViewById(R.id.emailList);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                EmailObject emailObject = ListHandler.emailsArrayAdapater.getItem(position);

                new OptionsDialog(con, emailObject).show();
            }
        });
    }
}
