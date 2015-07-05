package com.aslam.zeshan.emailocr.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aslam.zeshan.emailocr.Adapter.EmailObject;
import com.aslam.zeshan.emailocr.R;

public class OptionsDialog {

    Context con;
    EmailObject emailObject;

    public OptionsDialog(Context con, EmailObject emailObject) {
        this.con = con;
        this.emailObject = emailObject;
    }

    public void show() {
        final AlertDialog builder = new AlertDialog.Builder(con).create();
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.options_dialog, null);

        ListView listView = (ListView) v.findViewById(R.id.optionsList);
        String[] values = new String[]{"Edit", "Delete"};

        // Change textview to selected name
        TextView textView = (TextView) v.findViewById(R.id.dialog);
        textView.setText(emailObject.name);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(con,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Edit
                        break;
                    default:
                        // Delete
                        break;
                }

                builder.dismiss();
            }
        });


        builder.setView(v);
        builder.show();
    }
}
