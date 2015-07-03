package com.aslam.zeshan.emailocr.Listeners;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.aslam.zeshan.emailocr.Dialog.EmailDialog;
import com.aslam.zeshan.emailocr.Dialog.ImageDialog;
import com.aslam.zeshan.emailocr.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class FloatListener {

    Context con;

    public  FloatListener(Context con) {
        this.con = con;
    }

    public void addEmail() {
        final FloatingActionButton actionA = (FloatingActionButton) ((Activity) con).findViewById(R.id.addEmail);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatingActionsMenu actionM = (FloatingActionsMenu) ((Activity) con).findViewById(R.id.multiple_actions);
                actionM.collapse();

                new EmailDialog(con).show();
            }
        });
    }

    public void getImage() {
        final FloatingActionButton actionA = (FloatingActionButton) ((Activity) con).findViewById(R.id.takePic);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatingActionsMenu actionM = (FloatingActionsMenu) ((Activity) con).findViewById(R.id.multiple_actions);
                actionM.collapse();

                new ImageDialog(con).show();
            }
        });
    }
}
