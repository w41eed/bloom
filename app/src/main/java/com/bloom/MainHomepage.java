package com.bloom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainHomepage extends AppCompatActivity {

    private ArrayList<ToDoItem> ToDoList;
    private ToDoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        initList();

        Spinner spinnerCountries = findViewById(R.id.spinner1);

        mAdapter = new ToDoAdapter(this, ToDoList);
        spinnerCountries.setAdapter(mAdapter);

        spinnerCountries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToDoItem clickedItem = (ToDoItem) parent.getItemAtPosition(position);
                String clickedCountryName = clickedItem.getWhatToDo();
                Toast.makeText(MainHomepage.this, clickedCountryName + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initList() {
        ToDoList = new ArrayList<>();
        ToDoList.add(new ToDoItem("study", R.drawable.study));
        ToDoList.add(new ToDoItem("workout", R.drawable.workout));
        ToDoList.add(new ToDoItem("fun", R.drawable.fun));
        ToDoList.add(new ToDoItem("meeting", R.drawable.meeting));
        ToDoList.add(new ToDoItem("meditation", R.drawable.meditation));
        ToDoList.add(new ToDoItem("social", R.drawable.social));
        ToDoList.add(new ToDoItem("cook", R.drawable.cook));
        ToDoList.add(new ToDoItem("housework", R.drawable.housework));
        ToDoList.add(new ToDoItem("other", R.drawable.other));

    }
}

