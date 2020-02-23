package com.bloom;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import java.util.ArrayList;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;



public class ToDoAdapter extends ArrayAdapter<ToDoItem> {
    public ToDoAdapter(Context context, ArrayList<ToDoItem> countryList) {
        super(context, 0, countryList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_row, parent, false
            );
        }

        ImageView imageViewFlag = convertView.findViewById(R.id.image_view_icon);
        TextView textViewName = convertView.findViewById(R.id.text_view_todo);

        ToDoItem currentItem = getItem(position);

        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem.getIconImage());
            textViewName.setText(currentItem.getWhatToDo());
        }

        return convertView;
    }
}

