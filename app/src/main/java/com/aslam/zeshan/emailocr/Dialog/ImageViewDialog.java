package com.aslam.zeshan.emailocr.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aslam.zeshan.emailocr.Adapter.ListHandler;
import com.aslam.zeshan.emailocr.Database.EmailDatabase;
import com.aslam.zeshan.emailocr.R;
import com.aslam.zeshan.emailocr.Util.StringUtil;

import java.io.File;
import java.io.IOException;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageViewDialog {

    Context con;
    File file;

    public ImageViewDialog(Context con, File file) {
        this.con = con;
        this.file = file;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.image_view_dialog, null);

        final ImageView imageView = (ImageView) v.findViewById(R.id.imageView);

        try {
            imageView.setImageBitmap(getResizedBitmap(MediaStore.Images.Media.getBitmap(con.getContentResolver(), Uri.fromFile(file))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);
        attacher.update();

        builder.setView(v).setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder.create();
        builder.show();
    }

    private Bitmap getResizedBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return bitmap;
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int newWidth = 2048;
        int newHeight = 2048;

        if (w <= 2048 && h <= 2048) {
            return bitmap;
        }

        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0;
        float pivotY = 0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));


        return scaledBitmap;
    }
}
