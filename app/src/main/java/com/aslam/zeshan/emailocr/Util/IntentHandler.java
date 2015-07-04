package com.aslam.zeshan.emailocr.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class IntentHandler {

    Context con;

    public IntentHandler(Context con) {
        this.con = con;
    }

    public void cameraIntent() {
        UUID uuid = UUID.randomUUID();

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(android.os.Environment.getExternalStorageDirectory(), uuid.toString() + ".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        ((Activity) con).startActivityForResult(intent, 3);
    }

    public void fileIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        ((Activity)con).startActivityForResult(Intent.createChooser(intent, "Select Picture"), 4);
    }

    public void emailIntent(String[] emails, File file, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "EmailOCR");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.putExtra(Intent.EXTRA_EMAIL, emails);

        ArrayList<Uri> uris = new ArrayList<>();
        uris.add(Uri.fromFile(file));
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);

        ((Activity)con).startActivityForResult(Intent.createChooser(intent, "Select Email"), 5);
    }
}
