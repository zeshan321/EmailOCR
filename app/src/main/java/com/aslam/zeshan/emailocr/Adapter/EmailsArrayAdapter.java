package com.aslam.zeshan.emailocr.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.aslam.zeshan.emailocr.R;
import com.aslam.zeshan.emailocr.Util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class EmailsArrayAdapter extends ArrayAdapter<EmailObject> {

    List<EmailObject> emailsList = new ArrayList<EmailObject>();
    List<EmailObject> etList = new ArrayList<EmailObject>();

    String last;

    Context con;

    TextView email;
    TextView name;

    ImageView imageLetter;
    ImageView imageCheck;


    public EmailsArrayAdapter(Context con, int resource) {
        super(con, resource);

        this.con = con;
    }

    @Override
    public void add(EmailObject object) {
        emailsList.add(object);
        super.add(object);
    }

    @Override
    public void clear() {
        this.emailsList.clear();

        super.clear();
    }

    public void add(int i, EmailObject object) {
        emailsList.add(i, object);
    }

    public void set(int i, EmailObject object) {
        emailsList.set(i, object);
    }

    public int getCount() {
        return this.emailsList.size();
    }

    public EmailObject getItem(int index) {
        return this.emailsList.get(index);
    }

    public void removeEmail(int index) {
        emailsList.remove(index);
    }

    public View getView(int position, View row, ViewGroup parent) {
        EmailObject emailObject = getItem(position);
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.email_list_view, parent, false);

        email = (TextView) row.findViewById(R.id.email);
        name = (TextView) row.findViewById(R.id.name);
        imageLetter = (ImageView) row.findViewById(R.id.selected);
        imageCheck = (ImageView) row.findViewById(R.id.check_icon);

        email.setText(emailObject.email);
        name.setText(emailObject.name);

        if (emailObject.selected) {
            imageLetter.setVisibility(View.GONE);
            imageCheck.setVisibility(View.VISIBLE);
        } else {
            imageCheck.setVisibility(View.GONE);
            imageLetter.setVisibility(View.VISIBLE);
        }

        ColorGenerator generator = ColorGenerator.DEFAULT;

        String s = String.valueOf(emailObject.name.charAt(0)).toUpperCase();
        int color2 = generator.getColor(s);

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(97)
                .height(97)
                .endConfig()
                .buildRect(s, color2);


        TextDrawable drawable1 = TextDrawable.builder()
                .beginConfig()
                .width(97)
                .height(97)
                .endConfig()
                .buildRect("\u2714", con.getResources().getColor(R.color.main));

        imageLetter.setImageDrawable(drawable);
        imageCheck.setImageDrawable(drawable1);

        return row;
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (last == null) {
                last = constraint.toString();
            }

            FilterResults filterResults = new FilterResults();
            ArrayList<EmailObject> tempList = new ArrayList<>();

            for (EmailObject emailObject: emailsList) {
                if (!etList.contains(emailObject)) {
                    etList.add(emailObject);
                }
            }

            if (!last.equals(constraint.toString())) {
                emailsList = etList;
            }

            if (new StringUtil().checkString(constraint.toString())) {
                for (EmailObject emailObject : emailsList) {

                    String name = emailObject.name.toLowerCase();
                    String email = emailObject.email.toLowerCase();

                    if (name.contains(constraint.toString().toLowerCase()) || email.contains(constraint.toString().toLowerCase())) {
                        tempList.add(emailObject);
                    }
                }
            } else {
                filterResults.values = emailsList;
                filterResults.count = emailsList.size();
                last = constraint.toString();

                return filterResults;
            }

            filterResults.values = tempList;
            filterResults.count = tempList.size();
            last = constraint.toString();

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence contraint, FilterResults results) {
            emailsList = (ArrayList<EmailObject>) results.values;
            notifyDataSetChanged();
        }
    };

    public void restoreList() {
        if (etList != null)
        emailsList = etList;
    }
}
