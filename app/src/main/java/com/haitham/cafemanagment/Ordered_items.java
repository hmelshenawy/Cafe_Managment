package com.haitham.cafemanagment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import de.siegmar.fastcsv.writer.CsvAppender;
import de.siegmar.fastcsv.writer.CsvWriter;

public class Ordered_items extends AppCompatActivity {

    TextView tv_total;
    ArrayList<Drink_class> mDrink_classes;
    Items_DataBase_Adaptor DB_Adaptor;
    SEC_DB_Adaptor secDB_Adaptor;
    RecyclerView mRecyclerView;
    Items_Adaptor items_adaptor;

    int coffee = 0;
    int milk = 0;
    int suger = 0;

    int coffee_cons = 0;
    int milk_cons = 0;
    int suger_cons = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_items);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_total = findViewById(R.id.tv_total);
        mRecyclerView = findViewById(R.id.mRecyclerView);

        // load Resources data from shared prefrences.
        loadData();

        DB_Adaptor = new Items_DataBase_Adaptor(this);
        secDB_Adaptor = new SEC_DB_Adaptor(this);

        mDrink_classes = new ArrayList<>();
        mDrink_classes = DB_Adaptor.getAllData();
        items_adaptor = new Items_Adaptor(this, mDrink_classes);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(items_adaptor);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                long id = (long) viewHolder.itemView.getTag();

                for (int i = 0; i < mDrink_classes.size(); i++) {

                    if (mDrink_classes.get(i).id == id) {

                        mDrink_classes.remove(mDrink_classes.get(i));

                    }
                }

                DB_Adaptor.removeRow_FirstTable(id);
                items_adaptor.notifyDataSetChanged();
                tv_total.setText(total_Invoice() + " L.E");
            }

        }).attachToRecyclerView(mRecyclerView);

        tv_total.setText(total_Invoice() + " L.E");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.oredered_items_menu, menu);
        return true;
    }

    public void order_Confirm(View view) {

        // First step to check if there is enough resources to make the customer order.
        resources_Availablity(mDrink_classes);

        if (resources_Availablity(mDrink_classes) == false) {

            // if resoursces are not available show message to user to Add resources.

            Toast.makeText(this, "You are out of resources !!", Toast.LENGTH_LONG).show();

        } else { // if resources are available will proceed in the order

            inventory_Subtract();

            //Return intent for mainActivity with result mDrink_List of purchased items.
            Intent in = new Intent();
            in.putParcelableArrayListExtra("drinks", mDrink_classes);
            in.putExtra("test", total_Invoice());
            setResult(RESULT_OK, in);
            finish();

            // Add purchased items to final records Data_Base
            for (int i = 0; i < mDrink_classes.size(); i++) {

                secDB_Adaptor.insertData(mDrink_classes.get(i).item_name, mDrink_classes.get(i).price, mDrink_classes.get(i).quantity, mDrink_classes.get(i).Coffee_Unit, mDrink_classes.get(i).Milk_Unit, mDrink_classes.get(i).Suger_Unit);
            }

            DB_Adaptor.removeAllData_FirstTable();
            mDrink_classes.clear();
            items_adaptor.notifyDataSetChanged();
            tv_total.setText("0.00");
        }

    }


    public double total_Invoice() {

        double total = 0;

        for (int i = 0; i < mDrink_classes.size(); i++) {

            total = total + mDrink_classes.get(i).price * mDrink_classes.get(i).quantity;
        }
        return total;
    }


    public void storeData() {

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("coffee", coffee);
        editor.putInt("milk", milk);
        editor.putInt("suger", suger);

        editor.apply();
    }


    public void loadData() {

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        coffee = prefs.getInt("coffee", 0);
        milk = prefs.getInt("milk", 0);
        suger = prefs.getInt("suger", 0);

    }


    public void inventory_Subtract() {

        int[] cons = {coffee_cons, milk_cons, suger_cons};

        coffee = coffee - coffee_cons;
        milk = milk - milk_cons;
        suger = suger - milk_cons;

        storeData();
    }


    public boolean resources_Availablity(ArrayList<Drink_class> Arr_Drinks) {

        for (int i = 0; i < Arr_Drinks.size(); i++) {

            coffee_cons = coffee_cons + Arr_Drinks.get(i).Coffee_Unit * Arr_Drinks.get(i).quantity;
            milk_cons = milk_cons + Arr_Drinks.get(i).Milk_Unit * Arr_Drinks.get(i).quantity;
            suger_cons = suger_cons + Arr_Drinks.get(i).Suger_Unit * Arr_Drinks.get(i).quantity;
        }

        return coffee_cons <= coffee && milk_cons <= milk && suger_cons <= suger;
    }


    public void csv_Writer() {

        File f = new File(getExternalFilesDir(null).getPath() + File.separator + "AnalysisData.csv");
        CsvWriter writer = new CsvWriter();
        CsvAppender csvAppender;
        Collection<String[]> collection = new ArrayList<>();

        for (int i = 0; i < mDrink_classes.size(); i++) {

            String[] arr = new String[8];
            arr[0] = mDrink_classes.get(i).item_name;
            arr[1] = String.valueOf(mDrink_classes.get(i).quantity);
            arr[2] = String.valueOf(mDrink_classes.get(i).price);

            collection.add(arr);
        }

        // File exist
        if (f.exists() && !f.isDirectory()) {

            try {
                writer.write(f, StandardCharsets.UTF_8, collection);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
