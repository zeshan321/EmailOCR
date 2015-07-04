package com.aslam.zeshan.emailocr.Listeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.aslam.zeshan.emailocr.MainActivity;
import com.aslam.zeshan.emailocr.R;
import com.aslam.zeshan.emailocr.Util.IntentHandler;

import java.io.File;

public class SendButtons {

    Context con;

    public SendButtons(Context con) {
        this.con = con;
    }

    public void onClick(final File file, final String text) {
        final Button button = (Button) ((Activity) con).findViewById(R.id.sendEmailIn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SendListListener sendListListener = new SendListListener(con);

                String[] emails = sendListListener.getList().toString().replace("[", "").replace("]", "").split(", ");

                Intent intent = new Intent(con, MainActivity.class);
                intent.putExtra("emailSent", true);
                intent.putExtra("path", file.getAbsolutePath());
                intent.putExtra("post", text);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                con.startActivity(intent);
                ((Activity)con).overridePendingTransition(R.transition.activity_from_1, R.transition.activity_from_2);

                new IntentHandler(con).emailIntent(emails, file, text);
            }
        });
    }
}
