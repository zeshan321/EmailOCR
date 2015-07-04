package com.aslam.zeshan.emailocr.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.aslam.zeshan.emailocr.PostActivity;
import com.aslam.zeshan.emailocr.R;
import com.aslam.zeshan.emailocr.SendActivity;

public class SentDialog {

    Context con;

    public SentDialog(Context con) {
        this.con = con;
    }

    public void show(final String text, final String path) {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.sent_dialog, null);

        builder.setView(v).setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        }).setNegativeButton("Resend", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(con, PostActivity.class);
                intent.putExtra("post", text);
                intent.putExtra("path", path);

                con.startActivity(intent);
                ((Activity) con).overridePendingTransition(R.transition.activity_to_1, R.transition.activity_to_2);
            }
        });

        builder.create();
        builder.show();
    }
}
