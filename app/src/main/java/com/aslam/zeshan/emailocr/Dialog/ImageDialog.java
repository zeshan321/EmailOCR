package com.aslam.zeshan.emailocr.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.aslam.zeshan.emailocr.R;
import com.aslam.zeshan.emailocr.Util.IntentHandler;

public class ImageDialog {

    Context con;

    public ImageDialog(Context con) {
        this.con = con;
    }

    public void show() {
        final AlertDialog builder = new AlertDialog.Builder(con).create();
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.image_dialog, null);

        ListView listView = (ListView) v.findViewById(R.id.listView);
        String[] values = new String[]{"Take Picture", "Gallery"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(con,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        new IntentHandler(con).cameraIntent();
                        break;
                    default:
                        new IntentHandler(con).fileIntent();
                        break;
                }

                builder.dismiss();
            }
        });


        builder.setView(v);
        builder.show();
    }
}
