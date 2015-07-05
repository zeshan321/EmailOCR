package com.aslam.zeshan.emailocr.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aslam.zeshan.emailocr.Adapter.EmailObject;
import com.aslam.zeshan.emailocr.Adapter.ListHandler;
import com.aslam.zeshan.emailocr.Database.EmailDatabase;
import com.aslam.zeshan.emailocr.R;
import com.aslam.zeshan.emailocr.Util.StringUtil;
import com.aslam.zeshan.emailocr.Util.ToastUtil;

public class OptionsDialog {

    Context con;
    EmailObject emailObject;

    public OptionsDialog(Context con, EmailObject emailObject) {
        this.con = con;
        this.emailObject = emailObject;
    }

    public void show() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(con);
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.options_dialog, null);

        ListView listView = (ListView) v.findViewById(R.id.optionsList);
        String[] values = new String[]{"Edit", "Delete"};

        // Change textview to selected name
        TextView textView = (TextView) v.findViewById(R.id.dialog);
        textView.setText(emailObject.name);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(con,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

        final AlertDialog dialog = builder.create();
        dialog.setView(v);
        dialog.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        showEdit();
                        dialog.dismiss();
                        break;
                    case 1:
                        EmailDatabase emailDatabase = new EmailDatabase(con);
                        emailDatabase.deleteEmail(emailObject.ID);

                        ListHandler.emailsArrayAdapater.remove(emailObject);
                        ListHandler.emailsArrayAdapater.notifyDataSetChanged();

                        dialog.dismiss();
                        break;
                }

            }
        });
    }

    private void showEdit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.edit_dialog, null);

        // Change textview to selected name
        TextView textView = (TextView) v.findViewById(R.id.dialog);
        textView.setText(emailObject.name);

        // Textview changes
        final TextView nameView = (TextView) v.findViewById(R.id.editName);
        final TextView emailView = (TextView) v.findViewById(R.id.editEmail);

        nameView.setText(emailObject.name);
        emailView.setText(emailObject.email);

        builder.setView(v).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String name = nameView.getText().toString();
                String email = emailView.getText().toString();

                if (!new StringUtil().checkString(name)) {
                    new ToastUtil(con, con.getResources().getString(R.string.name_not_valid), Toast.LENGTH_SHORT).sendToast();
                    return;
                }

                if (!new StringUtil().isEmail(email)) {
                    new ToastUtil(con, con.getResources().getString(R.string.email_not_valid), Toast.LENGTH_SHORT).sendToast();
                    return;
                }

                // Update database
                EmailDatabase emailDatabase = new EmailDatabase(con);
                emailDatabase.update(emailObject.ID, name, email);


                // Update listview object
                emailObject.name = name;
                emailObject.email = email;

                // Update listview
                ListHandler.emailsArrayAdapater.notifyDataSetChanged();

                dialog.dismiss();
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
