package com.aslam.zeshan.emailocr.Adapter;

public class EmailObject {

    public String name;
    public String email;
    public Boolean selected;

    public EmailObject(String name, String email, boolean selected) {
        super();

        this.name = name;
        this.email = email;
        this.selected = selected;
    }
}
