package com.bloom;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

public class navBarListener {
    private BottomNavigationView navBar;
    final private Context context;

    public navBarListener(BottomNavigationView n, Context c){
        navBar = n;
        context = c;
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        Intent Bintent = new Intent(context, MainHomepage.class);
                        context.startActivity(Bintent);
                        break;
                    case R.id.nav_stats:
                        Intent Cintent = new Intent(context, SummaryActivity.class);
                        context.startActivity(Cintent);
                        break;
                }
                return false;
            }
        });
    }
}
