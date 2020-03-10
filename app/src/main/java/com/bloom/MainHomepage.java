package com.bloom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainHomepage extends AppCompatActivity {

    private ArrayList<ToDoItem> ToDoList;
    private ToDoAdapter mAdapter;
    private Button NextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        NextButton = findViewById(R.id.button2);
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open a new activity
                Intent intent = new Intent(MainHomepage.this, Main2Activity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        ViewPager viewPager = findViewById(R.id.viewpager);
        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);

        initList();

        Spinner spinnerCountries = findViewById(R.id.spinner1);

        mAdapter = new ToDoAdapter(this, ToDoList);
        spinnerCountries.setAdapter(mAdapter);

        spinnerCountries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToDoItem clickedItem = (ToDoItem) parent.getItemAtPosition(position);
                String clickedItemName = clickedItem.getWhatToDo();

/*
                FileOutputStream fOut = null;
                try {
                    fOut = openFileOutput("tag",MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fOut.write(clickedItemName.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                FileInputStream fin = null;
                try {
                    fin = openFileInput("tag");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                int c = -1;
                String temp="";
                while(true){
                    try {
                        if (!((c = fin.read()) != -1)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    temp = temp + (char) c;
                }
                String testdata = temp;
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Toast.makeText(MainHomepage.this, testdata + " selected", Toast.LENGTH_SHORT).show();*/
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

