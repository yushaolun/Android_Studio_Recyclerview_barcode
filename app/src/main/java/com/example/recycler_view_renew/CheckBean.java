package com.example.recycler_view_renew;

import java.io.Serializable;

public class CheckBean implements Serializable {
    boolean isChecked;
    String name;
    int amount;


    public String getname() {
        return name;
    }


    public boolean ischeck() {
        return isChecked;
    }

    public int getamount(){
        return amount;
    }



    public void setname(String names) {
        this.name=names;
    }


    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public void amount() {
        this.amount=amount;
    }






}
