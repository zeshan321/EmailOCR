package com.aslam.zeshan.emailocr.Util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Patterns;

import com.aslam.zeshan.emailocr.Database.EmailDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FirstTimeSetup {

    Context con;
    List<String> tempEmails = new ArrayList<>();

    public FirstTimeSetup(Context con) {
        this.con = con;
    }

    public void setup() {
        SettingsManager settingsManager = new SettingsManager(con);

        if (!settingsManager.contains("firstTime")) {
            EmailDatabase emailDatabase = new EmailDatabase(con);

            Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
            Account[] accounts = AccountManager.get(con).getAccounts();
            for (Account account : accounts) {
                if (emailPattern.matcher(account.name).matches()) {
                    String possibleEmail = account.name;

                    if (!tempEmails.contains(possibleEmail)) {
                        tempEmails.add(possibleEmail);
                        emailDatabase.addEmail(possibleEmail);
                    }
                }
            }

            if (accounts.length == 0) {
                emailDatabase.addEmail("Example@example.com");
            }

            settingsManager.set("firstTime", false);
        }
    }
}
