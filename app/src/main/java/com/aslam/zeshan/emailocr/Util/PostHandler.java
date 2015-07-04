package com.aslam.zeshan.emailocr.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.aslam.zeshan.emailocr.PostActivity;
import com.aslam.zeshan.emailocr.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PostHandler {

    Context con;
    File file;

    String ID;

    ProgressDialog progressDialog;

    public PostHandler(Context con, File file) {
        this.con = con;
        this.file = file;
    }

    public void uploadFile() throws IOException {
        progressDialog = new ProgressDialog(con);
        progressDialog.setTitle("EmailOCR");
        progressDialog.setMessage("Working...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("file", file);

        client.post("http://api.newocr.com/v1/upload?key=fdfa2801261915bb84f9e9379f740f6f", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String str = new String(bytes, StandardCharsets.UTF_8);
                ID = new JSONUtil().getFileID(str);

                try {
                    getText();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                 progressDialog.setMessage("Failed!" );
            }
        });
    }

    private void getText() throws IOException {
        AsyncHttpClient client = new AsyncHttpClient();

        String URL = "http://api.newocr.com/v1/ocr?key=fdfa2801261915bb84f9e9379f740f6f&file_id=" + ID + "&page=1&lang=eng&psm=6";
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String str = new String(bytes, StandardCharsets.UTF_8);

                Intent intent = new Intent(con, PostActivity.class);
                intent.putExtra("post", new JSONUtil().getText(str));
                intent.putExtra("path", file.getAbsolutePath());

                con.startActivity(intent);
                ((Activity)con).overridePendingTransition(R.transition.activity_to_1, R.transition.activity_to_2);

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                 progressDialog.setMessage("Failed!");
            }
        });
    }
}