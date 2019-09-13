package com.haitham.cafemanagment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class Selected_Item extends AppCompatActivity {

    TextView tv_item;
    Button btn_plus;
    Button btn_minus;
    EditText ed_count;
    int count = 1;
    String itemName = "";
    double price = 0;
    int coffee = 0;
    int milk = 0;
    int suger = 0;
    Items_DataBase_Adaptor DB_Adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected__item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        tv_item = findViewById(R.id.tv_item);
        btn_plus = findViewById(R.id.btn_add);
        btn_minus = findViewById(R.id.btn_minus);
        ed_count = findViewById(R.id.ed_count);
        DB_Adaptor = new Items_DataBase_Adaptor(this);

        Intent intent = getIntent();
        itemName = intent.getStringExtra("item");
        price = intent.getDoubleExtra("price", price);
        coffee = intent.getIntExtra("Coffee", 0);
        milk = intent.getIntExtra("milk", 0);
        suger = intent.getIntExtra("suger", 0);
        tv_item.setText(itemName);

    }

    public void itemCount(View view) {


        if (view.getId() == R.id.btn_plus) {

            count++;
            ed_count.setText(String.valueOf(count));


        } else if (view.getId() == R.id.btn_minus) {

            if (count > 0) {
                count--;
                ed_count.setText(String.valueOf(count));
            }

        }

    }

    public void add_Items(View view) {

        Drink_class drink_class = new Drink_class();
        drink_class.id = -1;
        drink_class.item_name = itemName;
        drink_class.quantity = Integer.parseInt(ed_count.getText().toString());
        drink_class.price = price;
        drink_class.Coffee_Unit = coffee;
        drink_class.Milk_Unit = suger;
        drink_class.Suger_Unit = suger;


        ArrayList<Drink_class> drink_class_List = new ArrayList<>();
        drink_class_List.add(drink_class);
        System.out.println(drink_class_List.get(drink_class_List.size() - 1).item_name);
        System.out.println(drink_class_List.get(drink_class_List.size() - 1).quantity);

        DB_Adaptor.insertData(drink_class.item_name, drink_class.price, drink_class.quantity,
                drink_class.Coffee_Unit, drink_class.Milk_Unit, drink_class.Suger_Unit);

        Toast.makeText(this, "item added", Toast.LENGTH_LONG).show();
        finish();
    }
}
