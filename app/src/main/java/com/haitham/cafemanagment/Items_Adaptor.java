package com.haitham.cafemanagment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Items_Adaptor extends RecyclerView.Adapter<Items_Adaptor.Items_ViewHolder> {

    Context mContext;
    ArrayList<Drink_class> items_ArrList;

    public Items_Adaptor(Context context, ArrayList<Drink_class> items_ArrList) {

        this.mContext = context;
        this.items_ArrList = items_ArrList;

    }

    @NonNull
    @Override
    public Items_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recyclerview_items, parent, false);
        Items_ViewHolder holder = new Items_ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull Items_ViewHolder holder, int position) {

        holder.tv_name.setText(items_ArrList.get(position).item_name);
        holder.tv_price.setText("" + items_ArrList.get(position).price + "L.E");
        holder.tv_quantity.setText("" + items_ArrList.get(position).quantity);
        holder.itemView.setTag(items_ArrList.get(position).id);

    }

    @Override
    public int getItemCount() {
        return items_ArrList.size();
    }

    class Items_ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        TextView tv_price;
        TextView tv_quantity;

        public Items_ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_itemname);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);

        }

    }


}
