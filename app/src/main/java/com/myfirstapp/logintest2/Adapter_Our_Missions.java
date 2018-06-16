package com.myfirstapp.logintest2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;


public class Adapter_Our_Missions extends RecyclerView.Adapter<Adapter_Our_Missions.ViewHolder> {

    private static final String TAG = Adapter_Our_Missions.class.getSimpleName();

    private Context context;
    private ArrayList<Data_Our_Missions> missions;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();


     public Adapter_Our_Missions(Context context, ArrayList<Data_Our_Missions> missions) {
        this.context = context;
        this.missions = missions;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox checkBox;
        Button deleteButton;

        public ViewHolder(Context context, ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.custom_listview_our_missions, parent, false));

            checkBox = (CheckBox) itemView.findViewById(R.id.custom_listview_todo_checkbox);
            deleteButton = (Button) itemView.findViewById(R.id.delete);

            deleteButton.setOnClickListener(this); //button onclick listener

        }

        @Override
        public void onClick(View v) {
            delete(getAdapterPosition()); //calls the method above to delete
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_our_missions, parent, false);
        return new ViewHolder(context, parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Data_Our_Missions mission = missions.get(position); // you can cache getItemCount() in a writer variable for more performance tuning


    }

    @Override
    public int getItemCount() {
        if (missions == null) return 0;
        else return missions.size();
    }

    public void delete(int position) { //removes the row
        missions.remove(position);
        notifyItemRemoved(position);
    }

    public void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }







}