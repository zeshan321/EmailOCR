package com.aslam.zeshan.emailocr.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.aslam.zeshan.emailocr.PostActivity;
import com.aslam.zeshan.emailocr.R;
import com.aslam.zeshan.emailocr.SendActivity;
import com.aslam.zeshan.emailocr.Util.SettingsManager;

public class LangDialog {

    Context con;

    public LangDialog(Context con) {
        this.con = con;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.lang_dialog, null);

        // Spinner
        final Spinner spinner = (Spinner) v.findViewById(R.id.langType);
        String[] lang = new String[]{ "Afrikaans", "Arabic", "Azerbaijani", "Belarusian", "Bengali", "Bihari", "Bulgarian", "Catalan", "Czech", "Chinese (Simplified)", "Chinese (Traditional)", "Cherokee", "Danish", "Danish (Fraktur)", "German", "German (Fraktur)", "Greek", "English", "Middle English (1100-1500)", "Esperanto", "Math / equation detection", "Estonian", "Basque", "Finnish", "French", "Frankish", "Middle French (ca. 1400-1600)", "Galician", "Ancient Greek", "Gujarati", "Hebrew", "Hindi", "Hindi #2", "Croatian", "Hungarian", "IAST", "Indonesian", "Icelandic", "Italian", "Italian (Old)", "Japanese", "Kannada", "Konkani", "Korean", "Latvian", "Lithuanian", "Malayalam", "Macedonian", "Maltese", "Malay", "Dutch", "Norwegian", "Oriya", "Punjabi", "Polish", "Portuguese", "Romanian", "Russian", "Sanskrit (Sa?sk?ta)", "Slovak", "Slovak (Fraktur)", "Slovenian", "Spanish", "Spanish (Old)", "Albanian", "Serbian", "Swahili", "Swedish", "Tamil", "Telugu", "Tagalog", "Thai", "Turkish", "Ukrainian", "Vietnamese"};

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(con, android.R.layout.simple_spinner_item, lang);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setSelection(new SettingsManager(con).getInt("lang"));

        builder.setView(v).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                int pos = spinner.getSelectedItemPosition();

                new SettingsManager(con).set("lang", pos);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.create();
        builder.show();
    }
}
