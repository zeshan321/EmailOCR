package com.aslam.zeshan.emailocr.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.aslam.zeshan.emailocr.Adapter.ListHandler;
import com.aslam.zeshan.emailocr.Database.EmailDatabase;
import com.aslam.zeshan.emailocr.R;
import com.aslam.zeshan.emailocr.Util.StringUtil;

public class EmailDialog {

    Context con;

    public EmailDialog(Context con) {
        this.con = con;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.email_dialog, null);

        final TextView nameView = (TextView) v.findViewById(R.id.createName);
        final TextView emailView = (TextView) v.findViewById(R.id.createEmail);

        builder.setView(v).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String name = nameView.getText().toString();
                String email = emailView.getText().toString();

                if (!new StringUtil().checkString(name) || !new StringUtil().checkString(email)) {
                    return;
                }

                EmailDatabase emailDatabase = new EmailDatabase(con);
                emailDatabase.addEmail(name, email);

                new ListHandler(con).add(emailDatabase.lastID(), name, email);
            }

        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.create();
        builder.show();
    }
}
