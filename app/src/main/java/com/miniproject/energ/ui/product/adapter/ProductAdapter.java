package com.miniproject.energ.ui.product.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miniproject.energ.R;
import com.miniproject.energ.data.model.ProductModel;
import com.miniproject.energ.ui.product.listener.ProductListener;
import com.miniproject.energ.utils.Utils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rajesh Pradeep G on 30-10-2019
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context mContext;
    private ArrayList<Integer> productImgList;
    private ArrayList<ProductModel> productList;
    ProductListener productListener;

    public ProductAdapter(Context mContext, ArrayList<Integer> productImgList, ArrayList<ProductModel> productList,
                          ProductListener productListener) {
        this.mContext = mContext;
        this.productImgList = productImgList;
        this.productList = productList;
        this.productListener = productListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Glide.with(mContext).load(productImgList.get(position)).into(holder.ic_product_item);
//        holder.ic_product_item.setImageResource(productImgList.get(position));
        holder.product_val_tview.setText(productList.get(position).getProductName());
        holder.product_price_lbl.setText(productList.get(position).getProductMRP());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_item_llay)
        LinearLayout product_item_llay;
        @BindView(R.id.ic_product_item)
        ImageView ic_product_item;
        @BindView(R.id.product_price_lbl)
        TextView product_price_lbl;
        @BindView(R.id.product_val_tview)
        TextView product_val_tview;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Utils.setRegularFonts(mContext, new TextView[]{product_price_lbl});
            Utils.setBoldFonts(mContext, new TextView[]{product_val_tview});
            product_item_llay.setOnClickListener(view -> productListener.productItem(productImgList.get(getAdapterPosition()), productList.get(getAdapterPosition())));
        }
    }
}
