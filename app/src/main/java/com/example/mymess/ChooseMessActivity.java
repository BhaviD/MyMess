package com.example.mymess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class ChooseMessActivity extends OptionsMenuActivity {
    final int BREAKFAST = 1;
    final int LUNCH = 2;
    final int DINNER = 3;

    private Toolbar mtoolbar;

    RecyclerView recyclerView;
    ProductAdapter adapter;

    List<Product> productList;

    Integer mnDay, mnMonth, mnYear;
    String msDay;
    boolean mBreakfastChecked = false, mLunchChecked = false, mDinnerChecked = false, mAllChecked = false;

    Button register_mess_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mess);

        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        msDay = getIntent().getStringExtra("sDay");
        mnYear = getIntent().getIntExtra("nYear", 0);
        mnMonth = getIntent().getIntExtra("nMonth", 0);
        mnDay = getIntent().getIntExtra("nDay", 0);
        mBreakfastChecked = getIntent().getBooleanExtra("breakfastChecked", false);
        mLunchChecked = getIntent().getBooleanExtra("lunchChecked", false);
        mDinnerChecked = getIntent().getBooleanExtra("dinnerChecked", false);
        mAllChecked = getIntent().getBooleanExtra("allChecked", false);

        productList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); // try removing it!

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (mAllChecked) {
            productList.add(new Product(BREAKFAST, "Breakfast"));
            productList.add(new Product(LUNCH, "Lunch"));
            productList.add(new Product(DINNER, "Dinner"));
        }
        else {
            if (mBreakfastChecked)
                productList.add(new Product(BREAKFAST, "Breakfast"));
            if (mLunchChecked)
                productList.add(new Product(LUNCH, "Lunch"));
            if (mDinnerChecked)
                productList.add(new Product(DINNER, "Dinner"));
        }

        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        register_mess_btn = findViewById(R.id.register_mess);
        register_mess_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "";
                if (msDay != null) {
                    str = msDay + " | ";
                } else {
                    str = str + mnDay + "/" + mnMonth + "/" + mnYear + " | ";
                }
                for (Product product: productList) {
                    switch (product.getId()) {
                        case BREAKFAST:

                            break;
                        case LUNCH:
                            break;
                        case DINNER:
                            break;
                    }
                }
            }
        });
    }
}
