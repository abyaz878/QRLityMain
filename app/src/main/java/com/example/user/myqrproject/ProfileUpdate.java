package com.example.user.myqrproject;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.util.List;

public class ProfileUpdate {

    String usrName;
    String ursEmail;

public ProfileUpdate(){

    }


    public ProfileUpdate(String usrName, String ursEmail) {
        this.usrName = usrName;
        this.ursEmail = ursEmail;
    }

    public String getUsrName() {
        return usrName;
    }

    public String getUrsEmail() {
        return ursEmail;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public void setUrsEmail(String ursEmail) {
        this.ursEmail = ursEmail;
    }
}
