package com.example.mymess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class MessMenuActivity extends OptionsMenuActivity {
    RecyclerView recyclerView;
    MessMenuProductAdapter adapter;
    List<MessMenuProduct> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); // try removing it
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList.add(new MessMenuProduct("South", R.drawable.south_mess_menu));
        productList.add(new MessMenuProduct("North", R.drawable.south_mess_menu));
        productList.add(new MessMenuProduct("Yuktahaar", R.drawable.south_mess_menu));
        productList.add(new MessMenuProduct("Kadamba Veg", R.drawable.south_mess_menu));
        productList.add(new MessMenuProduct("Kadamba Non-Veg", R.drawable.south_mess_menu));

        adapter = new MessMenuProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);
    }
}
