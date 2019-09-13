package com.haitham.cafemanagment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class mListView_Adaptor extends ArrayAdapter<Drink_class> {


    public mListView_Adaptor(@NonNull Context context, ArrayList<Drink_class> arr_ItemClass) {
        super(context, R.layout.firstmenu_item_layout, arr_ItemClass);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.firstmenu_item_layout, parent, false);

        TextView itemName = view.findViewById(R.id.tvi_itemName);
        itemName.setText(getItem(position).item_name);

        TextView itemPrice = view.findViewById(R.id.tvi_itemPrice);
        itemPrice.setText(getItem(position).price + "L.E");
        return view;
    }
}
