package com.aslam.zeshan.emailocr.Listeners;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.aslam.zeshan.emailocr.Dialog.ImageViewDialog;
import com.aslam.zeshan.emailocr.R;

import java.io.File;

public class PostButtons {

    Context con;

    public PostButtons(Context con) {
        this.con = con;
    }

    public void viewImage(final File file) {
        final Button button = (Button) ((Activity)con).findViewById(R.id.viewImage);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new ImageViewDialog(con, file).show();
            }
        });
    }
}
