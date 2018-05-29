package com.myfirstapp.logintest2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class Data_Our_Missions {

    private static final String TAG = Data_Our_Missions.class.getSimpleName();

    private boolean isChecked;
    private String title;

    public Data_Our_Missions(boolean isChecked, String title) {
        this.title = title;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public String getTitle() {
        return title;
    }


    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}