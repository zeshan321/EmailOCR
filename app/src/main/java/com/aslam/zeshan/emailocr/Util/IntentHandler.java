package com.aslam.zeshan.emailocr.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

public class IntentHandler {

    Context con;

    public IntentHandler(Context con) {
        this.con = con;
    }

    public void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        ((Activity) con).startActivityForResult(intent, 3);
    }

    public void fileIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        ((Activity)con).startActivityForResult(Intent.createChooser(intent, "Select Picture"), 4);
    }

    public void emailIntant() {

    }
}
