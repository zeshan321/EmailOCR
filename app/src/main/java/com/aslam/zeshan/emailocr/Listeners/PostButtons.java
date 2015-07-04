package com.aslam.zeshan.emailocr.Listeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.aslam.zeshan.emailocr.Dialog.ImageViewDialog;
import com.aslam.zeshan.emailocr.R;
import com.aslam.zeshan.emailocr.SendActivity;

import java.io.File;

public class PostButtons {

    Context con;

    public PostButtons(Context con) {
        this.con = con;
    }

    public void viewImage(final File file) {
        final Button button = (Button) ((Activity) con).findViewById(R.id.viewImage);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new ImageViewDialog(con, file).show();
            }
        });
    }

    public void sendText(final String text, final String path) {
        final Button button = (Button) ((Activity) con).findViewById(R.id.sendEmail);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(con, SendActivity.class);
                intent.putExtra("post", text);
                intent.putExtra("path", path);

                con.startActivity(intent);
                ((Activity) con).overridePendingTransition(R.transition.activity_to_1, R.transition.activity_to_2);
            }
        });
    }
}
