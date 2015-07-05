package com.aslam.zeshan.emailocr.Util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aslam.zeshan.emailocr.R;

public class ToastUtil {

    Context con;
    String message;
    int length;

    public ToastUtil(Context con, String message, int length) {
        this.con = con;
        this.message = message;
        this.length = length;
    }


    public void sendToast() {
        LayoutInflater inflater = ((Activity) con).getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) ((Activity) con).findViewById(R.id.toast_layout_root));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);

        Toast toast = new Toast(con);
        toast.setGravity(Gravity.BOTTOM, 0, 160);
        toast.setDuration(length);
        toast.setView(layout);
        toast.show();
    }
}
