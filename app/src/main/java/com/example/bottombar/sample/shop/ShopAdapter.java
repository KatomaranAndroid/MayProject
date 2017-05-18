package com.example.bottombar.sample.shop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bottombar.sample.R;

import java.util.List;

/**
 * Created by yarolegovich on 07.03.2017.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private List<Item> data;

    public ShopAdapter(List<Item> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_shop_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        Glide.with(holder.itemView.getContext())
//                .load(data.get(position).getImage())
//                .placeholder(R.drawable.washington)
//                .into(holder.image);
        holder.item_name.setText(data.get(position).getName());
        holder.item_price.setText(data.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private  TextView item_price;
        private  TextView item_name;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            //image = (ImageView) itemView.findViewById(R.id.image);
            item_name = (TextView) itemView.findViewById(R.id.tv_throw_location_name);
            item_price = (TextView) itemView.findViewById(R.id.tv_throw_creator_name_2);

        }
    }
}
