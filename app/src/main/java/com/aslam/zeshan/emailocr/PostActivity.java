package com.aslam.zeshan.emailocr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.aslam.zeshan.emailocr.Listeners.PostButtons;

import java.io.File;


public class PostActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // Action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Intent data
        Intent intent = getIntent();
        String text = intent.getStringExtra("post");
        String path = intent.getStringExtra("path");

        // Display converted text
        TextView textView = (TextView) findViewById(R.id.postText);
        textView.setText(text);

        // Button listeners
        PostButtons postButtons = new PostButtons(this);
        postButtons.viewImage(new File(path));
        postButtons.sendText(text, path);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(R.transition.activity_from_1, R.transition.activity_from_2);
                finish();
                return true;
            case 1:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.transition.activity_from_1, R.transition.activity_from_2);
        finish();
    }
}
