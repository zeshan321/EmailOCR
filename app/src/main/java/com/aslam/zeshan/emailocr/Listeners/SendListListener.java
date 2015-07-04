package com.aslam.zeshan.emailocr.Listeners;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aslam.zeshan.emailocr.Adapter.EmailObject;
import com.aslam.zeshan.emailocr.Adapter.ListHandler;
import com.aslam.zeshan.emailocr.R;

import java.util.ArrayList;
import java.util.List;

public class SendListListener {

    public static List<String> selectedEmails = new ArrayList<>();

    int selectedAmount = 0;
    Context con;

    public SendListListener(Context con) {
        this.con = con;
    }

    public void onClick(final ActionBar support) {
        ListView listView = (ListView) ((Activity) con).findViewById(R.id.sendList);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                EmailObject emailObject = ListHandler.emailsArrayAdapater.getItem(position);

                if (!emailObject.selected) {
                    selectedAmount++;

                    emailObject.selected = true;
                    selectedEmails.add(emailObject.email);
                } else {
                    selectedAmount--;

                    emailObject.selected = false;
                    selectedEmails.add(emailObject.email);
                }

                support.setTitle("Selected: " + selectedAmount);
                ListHandler.emailsArrayAdapater.notifyDataSetChanged();
            }
        });
    }

    public int getAmount() {
        return selectedAmount;
    }

    public List<String> getList() {
        return selectedEmails;
    }
}
