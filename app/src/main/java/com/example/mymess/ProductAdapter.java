package com.example.mymess;

import android.content.Context;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Product> productList;

    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.custom_view, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.meal.setText(product.getMeal());
//        RadioGroup messGroup = holder.messGroup;
//        int id_offset = 0;
//        switch (product.getMeal()) {
//            case "Breakfast":
//                id_offset = 100;
//                break;
//            case "Lunch":
//                id_offset = 200;
//                break;
//            case "Dinner":
//                id_offset = 300;
//                break;
//            default:
//                break;
//        }
//        int count = holder.messGroup.getChildCount();
//        ArrayList<RadioButton> radioButtons = new ArrayList<RadioButton>();
//        for (int i = 0; i < count; i++) {
//            View rb = messGroup.getChildAt(i);
//            if (rb instanceof RadioButton) {
//                rb.setId(id_offset + i);
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView meal;
        RadioGroup messGroup;

        public ProductViewHolder(@NonNull final View itemView) {
            super(itemView);
            meal = itemView.findViewById(R.id.meal);
            messGroup = (RadioGroup) itemView.findViewById(R.id.messes);

            messGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton checkedRadioButton = (RadioButton) itemView.findViewById(checkedId);
                    String registered_mess = checkedRadioButton.getText().toString();

                    for (Product product: productList) {
                        if (product.getMeal() == meal.getText())
                            product.setRegistered_mess(registered_mess);
                    }
                }
            });
        }
    }

}
