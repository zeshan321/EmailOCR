package com.aslam.zeshan.emailocr;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.aslam.zeshan.emailocr.Adapter.ListHandler;
import com.aslam.zeshan.emailocr.Listeners.FloatListener;
import com.aslam.zeshan.emailocr.Util.EmailImport;
import com.aslam.zeshan.emailocr.Util.FilePath;
import com.aslam.zeshan.emailocr.Util.PostHandler;

import java.io.File;
import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    Context con;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.con = this;

        new ListHandler(this).initialSetup();
        new EmailImport(this, false).setup();

        new FloatListener(this).addEmail();
        new FloatListener(this).getImage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);

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
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.add(1, 1, 1, "Import emails");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == 1) {
            new EmailImport(this, true).setup();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 3:
                    File f = new File(Environment.getExternalStorageDirectory().toString());
                    for (final File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            try {
                                PostHandler postHandler = new PostHandler(con, f);
                                postHandler.uploadFile();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case 4:
                    f = new File(new FilePath(this, data.getData()).getPath());
                    try {
                        PostHandler postHandler = new PostHandler(con, f);
                        postHandler.uploadFile();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
