package com.aslam.zeshan.emailocr.Adapter;

public class EmailObject {

    public int ID;
    public String name;
    public String email;
    public Boolean selected;

    public EmailObject(int ID, String name, String email, boolean selected) {
        super();

        this.ID = ID;
        this.name = name;
        this.email = email;
        this.selected = selected;
    }
}
