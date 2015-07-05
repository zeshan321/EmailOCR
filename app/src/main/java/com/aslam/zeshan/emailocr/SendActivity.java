package com.aslam.zeshan.emailocr;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.aslam.zeshan.emailocr.Adapter.ListHandler;
import com.aslam.zeshan.emailocr.Listeners.SendButtons;
import com.aslam.zeshan.emailocr.Listeners.SendListListener;

import java.io.File;

public class SendActivity extends ActionBarActivity {

    Context con;
    SearchView searchView;

    String text;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        this.con = this;

        // Clean up old data
        SendListListener.selectedEmails.clear();

        // Action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Selected: 0");

        // Intent data
        Intent intent = getIntent();
        text = intent.getStringExtra("post");
        path = intent.getStringExtra("path");

        // List view
        new ListHandler(this).initialSetup(true);
        new SendListListener(this).onClick(getSupportActionBar());

        // Button listener
        new SendButtons(this).onClick(new File(path), text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, PostActivity.class);
                intent.putExtra("post", text);
                intent.putExtra("path", path);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.setIconified(true);
                ListHandler.emailsArrayAdapater.restoreList();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    searchView.setIconified(true);
                    ListHandler.emailsArrayAdapater.restoreList();
                    return false;
                }

                ListHandler.emailsArrayAdapater.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.transition.activity_from_1, R.transition.activity_from_2);
        finish();
    }
}
