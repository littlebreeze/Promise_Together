package com.myfirstapp.logintest2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Adapter_Todo extends RecyclerView.Adapter<Adapter_Todo.ViewHolder> {

    private static final String TAG = Adapter_Todo.class.getSimpleName();

    private Context context;
    private ArrayList<TODO> missions;

    public Adapter_Todo(Context context, ArrayList<TODO> missions) {
        this.context = context;
        this.missions = missions;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        FrameLayout frameLayout;
        CheckBox checkBox;
        Button deleteButton;

        public ViewHolder(Context context, ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.custom_listview_our_missions, parent, false));

            frameLayout = (FrameLayout) itemView.findViewById(R.id.custom_listview_todo_framelayout);
            checkBox = (CheckBox) itemView.findViewById(R.id.custom_listview_todo_checkbox);
            deleteButton = (Button) itemView.findViewById(R.id.delete);
        }

        @Override
        public void onClick(View v) {
            delete(getAdapterPosition()); //calls the method above to delete
        }
    }

    private void delete(int adapterPosition) {
        missions.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
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

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the clicked item label
                TODO itemLabel = missions.get(position);

                // Remove the item on remove/button click
                missions.remove(position);

                /*
                    public final void notifyItemRemoved (int position)
                        Notify any registered observers that the item previously located at position
                        has been removed from the data set. The items previously located at and
                        after position may now be found at oldPosition - 1.

                        This is a structural change event. Representations of other existing items
                        in the data set are still considered up to date and will not be rebound,
                        though their positions may be altered.

                    Parameters
                        position : Position of the item that has now been removed
                */
                notifyItemRemoved(position);

                /*
                    public final void notifyItemRangeChanged (int positionStart, int itemCount)
                        Notify any registered observers that the itemCount items starting at
                        position positionStart have changed. Equivalent to calling
                        notifyItemRangeChanged(position, itemCount, null);.

                        This is an item change event, not a structural change event. It indicates
                        that any reflection of the data in the given position range is out of date
                        and should be updated. The items in the given range retain the same identity.

                    Parameters
                        positionStart : Position of the first item that has changed
                        itemCount : Number of items that have changed
                */
                notifyItemRangeChanged(position,missions.size());

                // Show the removed item label
                Toast.makeText(context,"Mission Removed " ,Toast.LENGTH_SHORT).show();

                //Delete on firestore DB
                MainActivity.uploadTodoDelete(todo.getDocumentId());
            }
        });


    }

    @Override
    public int getItemCount() {
        if (missions == null) return 0;
        else return missions.size();
    }


}