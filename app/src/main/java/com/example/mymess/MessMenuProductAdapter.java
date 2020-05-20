package com.example.mymess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

public class MessMenuProductAdapter extends RecyclerView.Adapter<MessMenuProductAdapter.ProductViewHolder> {
    private Context mCtx;
    private List<MessMenuProduct> productList;

    public MessMenuProductAdapter(Context mCtx, List<MessMenuProduct> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MessMenuProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.mess_menu_custom_view, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessMenuProductAdapter.ProductViewHolder holder, int position) {
        MessMenuProduct product = productList.get(position);
        holder.mess.setText(product.getMess());
        holder.mess_menu_photo.setImageResource(product.getImageResId());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView mess;
        PhotoView mess_menu_photo;

        ProductViewHolder(@NonNull final View itemView) {
            super(itemView);
            mess = itemView.findViewById(R.id.mess);
            mess_menu_photo = itemView.findViewById(R.id.mess_menu_photo);
        }
    }
}
