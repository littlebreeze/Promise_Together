package com.myfirstapp.logintest2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class Adapter_Todo extends RecyclerView.Adapter<Adapter_Todo.ViewHolder> {

    private static final String TAG = Adapter_Todo.class.getSimpleName();

    private Context context;
    private ArrayList<TODO> missions;

    public Adapter_Todo(Context context, ArrayList<TODO> missions) {
        this.context = context;
        this.missions = missions;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout frameLayout;
        CheckBox checkBox;

        public ViewHolder(Context context, ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.custom_listview_our_missions, parent, false));

            frameLayout = (FrameLayout) itemView.findViewById(R.id.custom_listview_todo_framelayout);
            checkBox = (CheckBox) itemView.findViewById(R.id.custom_listview_todo_checkbox);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(context, parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final TODO todo = missions.get(position); // you can cache getItemCount() in a writer variable for more performance tuning

        holder.checkBox.setChecked(todo.isChecked());
        holder.checkBox.setText(todo.getTitle());

        holder.checkBox.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context, TodoDetailActivity.class);
                intent.putExtra(STATIC.EXTRA_TODO, todo);
                context.startActivity(intent);
                return false;
            }
        });

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.uploadTodoChange(todo.getDocumentId(), isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (missions == null) return 0;
        else return missions.size();
    }
}