package com.example.mymess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChooseMessProductAdapter extends RecyclerView.Adapter<ChooseMessProductAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<ChooseMessProduct> productList;

    public ChooseMessProductAdapter(Context mCtx, List<ChooseMessProduct> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.choose_mess_custom_view, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ChooseMessProduct product = productList.get(position);
        holder.meal.setText(product.getMeal());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView meal;
        RadioGroup messGroup;

        ProductViewHolder(@NonNull final View itemView) {
            super(itemView);
            meal = itemView.findViewById(R.id.meal);
            messGroup = (RadioGroup) itemView.findViewById(R.id.messes);

            messGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton checkedRadioButton = (RadioButton) itemView.findViewById(checkedId);
                    String registered_mess = checkedRadioButton.getText().toString();
                    if (registered_mess.equals("Cancel"))
                        registered_mess = "Cancelled";

                    for (ChooseMessProduct product: productList) {
                        if (product.getMeal() == meal.getText())
                            product.setRegistered_mess(registered_mess);
                    }
                }
            });
        }
    }

}
