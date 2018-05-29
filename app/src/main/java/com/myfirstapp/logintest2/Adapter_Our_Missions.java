package com.myfirstapp.logintest2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;

public class Adapter_Our_Missions extends RecyclerView.Adapter<Adapter_Our_Missions.ViewHolder> {

    private static final String TAG = Adapter_Our_Missions.class.getSimpleName();

    private Context context;
    private ArrayList<Data_Our_Missions> missions;

    public Adapter_Our_Missions(Context context, ArrayList<Data_Our_Missions> missions) {
        this.context = context;
        this.missions = missions;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public ViewHolder(Context context, ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.custom_listview_our_missions, parent, false));

            checkBox = (CheckBox) itemView.findViewById(R.id.custom_list_view_our_missions_checkbox);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(context, parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Data_Our_Missions mission = missions.get(position); // you can cache getItemCount() in a writer variable for more performance tuning

        holder.checkBox.setChecked(mission.isChecked());
        holder.checkBox.setText(mission.getTitle());
    }

    @Override
    public int getItemCount() {
        if (missions == null) return 0;
        else return missions.size();
    }
}