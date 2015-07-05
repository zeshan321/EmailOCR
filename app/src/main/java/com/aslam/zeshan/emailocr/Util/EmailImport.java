package com.aslam.zeshan.emailocr.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.aslam.zeshan.emailocr.Adapter.ListHandler;
import com.aslam.zeshan.emailocr.Database.EmailDatabase;
import com.aslam.zeshan.emailocr.R;

import java.util.ArrayList;
import java.util.HashSet;

public class EmailImport {

    Context con;
    boolean force;

    public EmailImport(Context con, boolean force) {
        this.con = con;
        this.force = force;
    }

    public void setup() {
        SettingsManager settingsManager = new SettingsManager(con);

        if (!settingsManager.contains("firstTime") || force) {

            AlertDialog.Builder builder = new AlertDialog.Builder(con);
            LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View v = inflater.inflate(R.layout.import_dialog, null);


            builder.setView(v).setPositiveButton("Import", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    new Thread(new Runnable() {
                        public void run() {
                            final EmailDatabase emailDatabase = new EmailDatabase(con);
                            for (String s : getNameEmailDetails()) {
                                final String[] sp = s.split(" <s> ");

                                if (!emailDatabase.contains(sp[1])) {

                                    emailDatabase.addEmail(sp[0], sp[1]);
                                    ((Activity) con).runOnUiThread(new Runnable() {
                                        public void run() {
                                            new ListHandler(con).add(emailDatabase.lastID(), sp[0], sp[1]);
                                        }
                                    });
                                }
                            }
                        }
                    }).start();
                }

            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            builder.create();
            builder.show();

            settingsManager.set("firstTime", false);
        }
    }

    private ArrayList<String> getNameEmailDetails() {
        ArrayList<String> emlRecs = new ArrayList<String>();
        HashSet<String> emlRecsHS = new HashSet<String>();
        ContentResolver cr = con.getContentResolver();
        String[] PROJECTION = new String[] { ContactsContract.RawContacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.CommonDataKinds.Email.DATA,
                ContactsContract.CommonDataKinds.Photo.CONTACT_ID };
        String order = "CASE WHEN "
                + ContactsContract.Contacts.DISPLAY_NAME
                + " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
                + ContactsContract.Contacts.DISPLAY_NAME
                + ", "
                + ContactsContract.CommonDataKinds.Email.DATA
                + " COLLATE NOCASE";
        String filter = ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE ''";
        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, filter, null, order);
        if (cur.moveToFirst()) {
            do {
                // names comes in hand sometimes
                String name = cur.getString(1);
                String emlAddr = cur.getString(3);

                // keep unique only
                if (emlRecsHS.add(emlAddr.toLowerCase())) {
                    emlRecs.add(name + " <s> " + emlAddr);
                }
            } while (cur.moveToNext());
        }

        cur.close();
        return emlRecs;
    }
}
