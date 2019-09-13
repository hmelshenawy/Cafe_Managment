package com.haitham.cafemanagment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView mMenu_listView;
    mListView_Adaptor adapter;
    Drink_class esspresso;
    Drink_class Double_Esspresso;
    Drink_class latee;
    Drink_class miciato;
    Drink_class ice_Esspresso;
    ArrayList<Drink_class> mDrink_Result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Create Drinks item which will be shown in cafe menu.
        esspresso = new Drink_class("Esspresso", 20, 1, 0, 2);
        Double_Esspresso = new Drink_class("Double Esspresso", 25, 2, 0, 2);
        latee = new Drink_class("latte", 25, 2, 2, 3);
        miciato = new Drink_class("Miciato", 20, 2, 1, 2);
        ice_Esspresso = new Drink_class("Ice Esspresso", 25, 2, 1, 3);


        ArrayList<Drink_class> menuList = new ArrayList<>();

        //Add all drinks item in an ArrayList to be shown in ListView.
        menuList.add(esspresso);
        menuList.add(Double_Esspresso);
        menuList.add(latee);
        menuList.add(miciato);
        menuList.add(ice_Esspresso);
        menuList.add(new Drink_class("Cuppuccino", 30, 2, 4, 2));
        menuList.add(new Drink_class("Turkish Coffee", 20, 2, 0, 3));

        mMenu_listView = findViewById(R.id.mMenu_listview);
        adapter = new mListView_Adaptor(this, menuList);
        mMenu_listView.setAdapter(adapter);

        //Set onItemClickListener for ListView EventHandling
        mMenu_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Create new intent to open Selected_Item Activity and send Drink_Item to Activity.
                String itemName = adapter.getItem(i).item_name;
                double itemPrice = adapter.getItem(i).price;
                Intent intent = new Intent(MainActivity.this, Selected_Item.class);
                intent.putExtra("item", itemName);
                intent.putExtra("price", itemPrice);
                intent.putExtra("Coffee", adapter.getItem(i).Coffee_Unit);
                intent.putExtra("milk", adapter.getItem(i).Milk_Unit);
                intent.putExtra("suger", adapter.getItem(i).Suger_Unit);
                startActivity(intent);

            }
        });
    }

    @Override
    public void supportInvalidateOptionsMenu() {

        super.supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.cart) {

            Intent in = new Intent(this, Ordered_items.class);
            startActivityForResult(in, 1);
        } else if (item.getItemId() == R.id.inventory) {

            Intent intent = new Intent(this, Inventory.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.report) {

            SEC_DB_Adaptor sec_db_adaptor = new SEC_DB_Adaptor(this);
            sec_db_adaptor.csv_Writer();
            Toast.makeText(this, "Records Exported!!", Toast.LENGTH_SHORT).show();
        }


        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Check Request code for the mainActivity intent
        if (requestCode == 1) {

            //check result code is same as in Ordered_items Activity
            if (resultCode == RESULT_OK) {

                mDrink_Result = data.getParcelableArrayListExtra("drinks");

                double total = data.getDoubleExtra("test", 110);

            }
        }

    }

    public int[] inventory_Subtract(ArrayList<Drink_class> Arr_Drinks) {

        int coffee = 0;
        int milk = 0;
        int suger = 0;
        int[] cons = {coffee, milk, suger};

        for (int i = 0; i < Arr_Drinks.size(); i++) {

            coffee = coffee + Arr_Drinks.get(i).Coffee_Unit * Arr_Drinks.get(i).quantity;
            milk = milk + Arr_Drinks.get(i).Milk_Unit * Arr_Drinks.get(i).quantity;
            suger = suger + Arr_Drinks.get(i).Suger_Unit * Arr_Drinks.get(i).quantity;

        }
        System.out.println("Coffee consumed: " + coffee + " unit, Milk consumed: " + milk + " unit, Suger Consumed: " + suger + "unit.");
        Toast.makeText(this,
                "Coffee consumed: " + coffee + " unit, Milk consumed: " + milk + " unit, Suger Consumed: " + suger + "unit.",
                Toast.LENGTH_LONG).show();

        return cons;
    }
}