package com.aslam.zeshan.emailocr.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.aslam.zeshan.emailocr.R;

import java.util.ArrayList;
import java.util.List;

public class EmailsArrayAdapater extends ArrayAdapter<EmailObject> {

    List<EmailObject> emailsList = new ArrayList<EmailObject>();

    Context con;

    TextView email;
    CheckBox checkBox;

    public EmailsArrayAdapater(Context con, int resource) {
        super(con, resource);

        this.con = con;
    }

    @Override
    public void add(EmailObject object) {
        emailsList.add(object);
        super.add(object);
    }

    @Override
    public void clear() {
        this.emailsList.clear();

        super.clear();
    }

    public void add(int i, EmailObject object) {
        emailsList.add(i, object);
    }

    public void set(int i, EmailObject object) {
        emailsList.set(i, object);
    }

    public int getCount() {
        return this.emailsList.size();
    }

    public EmailObject getItem(int index) {
        return this.emailsList.get(index);
    }

    public void removeEmail(int index) {
        emailsList.remove(index);
    }

    public View getView(int position, View row, ViewGroup parent) {
        EmailObject emailObject = getItem(position);
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.email_list_view, parent, false);

        email = (TextView) row.findViewById(R.id.email);
        checkBox = (CheckBox) row.findViewById(R.id.emailSelect);

        email.setText(emailObject.email);
        checkBox.setSelected(emailObject.selected);

        return row;
    }
}
