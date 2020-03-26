package com.bloom;

import android.graphics.drawable.Icon;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class locationAdapter extends RecyclerView.Adapter<locationAdapter.locationViewHolder> {
    private ArrayList<locationItem> location_list;
    private OnItemClickListener Listener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener l){
        this.Listener = l;
    }


    public static class locationViewHolder extends RecyclerView.ViewHolder{
        //assign values for the two textView
        public TextView TextView_label;
        public TextView TextView_location;
        public ImageView Icon_delete;

        public locationViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            // references to textviews
            TextView_label = itemView.findViewById(R.id.textView_label);
            TextView_location = itemView.findViewById(R.id.textView_location);
            Icon_delete = itemView.findViewById(R.id.icon_delete);
            Icon_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            listener.onItemClick(pos);
                        }

                    }
                }
            });

        }
    }

    // get the data from array list into adapter through constructor
    public locationAdapter(ArrayList<locationItem> ll){
        this.location_list = ll;
    }

    @Override
    public locationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent,false);
        locationViewHolder vh = new locationViewHolder(v, Listener);
        return vh;
    }
// get element from array list at the position, and replace the contents of the view with that element
    @Override
    public void onBindViewHolder(locationViewHolder holder, int position) {
        locationItem item = location_list.get(position);
        holder.TextView_label.setText(item.getText_label());
        holder.TextView_location.setText(item.getText_location());
    }

    @Override
    public int getItemCount() {
        return location_list.size();
    }
}
