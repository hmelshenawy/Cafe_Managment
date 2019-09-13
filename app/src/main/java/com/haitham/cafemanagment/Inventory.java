package com.haitham.cafemanagment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Inventory extends AppCompatActivity {

    ImageButton btn_AddCoffe;
    ImageButton btn_AddMilk;
    ImageView btn_AddSuger;
    TextView tv_CoffeStat;
    TextView tv_MilkStat;
    TextView tv_SugerStat;
    EditText ed_coffAmnt;
    EditText ed_milkAmnt;
    EditText ed_sugerAmnt;
    int coffee = 0;
    int milk = 0;
    int suger = 0;
    int[] arr = {coffee, milk, suger};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Load Data from shared prefrences.
        loadData();

        btn_AddCoffe = findViewById(R.id.btn_add1);
        btn_AddMilk = findViewById(R.id.btn_add2);
        btn_AddSuger = findViewById(R.id.btn_add3);

        ed_coffAmnt = findViewById(R.id.ed_coffAmnt);
        ed_milkAmnt = findViewById(R.id.ed_milkAmnt);
        ed_sugerAmnt = findViewById(R.id.ed_sugerAmnt);

        tv_CoffeStat = findViewById(R.id.tv_coffeeStat);
        tv_MilkStat = findViewById(R.id.tv_milkStat);
        tv_SugerStat = findViewById(R.id.tv_sugerStat);

        tv_CoffeStat.setText(coffee + "\nUnits");
        tv_MilkStat.setText(milk + "\nUnits");
        tv_SugerStat.setText(suger + "\nUnits");

    }

    public void add_Stock(View view) {

        if (view.getId() == R.id.btn_add1) {

            if (ed_coffAmnt.getText().toString().trim().length() > 0) {

                int addedcoffee = Integer.valueOf(ed_coffAmnt.getText().toString());
                coffee = addedcoffee + coffee;
                tv_CoffeStat.setText(coffee + "\nUnits");

            } else {
                Toast.makeText(this, "Input is invalid!", Toast.LENGTH_LONG).show();
            }
            ed_coffAmnt.getText().clear();
            close_kBoard();
        } else if (view.getId() == R.id.btn_add2) {


            if (ed_milkAmnt.getText().toString().trim().length() > 0) {

                int addedmilk = Integer.valueOf(ed_milkAmnt.getText().toString());
                milk = addedmilk + milk;
                tv_MilkStat.setText(milk + "\nUnits");

            } else {
                Toast.makeText(this, "Input is invalid!", Toast.LENGTH_LONG).show();
            }
            ed_milkAmnt.getText().clear();
            close_kBoard();

        } else if (view.getId() == R.id.btn_add3) {

            if (ed_sugerAmnt.getText().toString().trim().length() > 0) {

                int addedsuger = Integer.valueOf(ed_sugerAmnt.getText().toString());
                suger = addedsuger + suger;
                tv_SugerStat.setText(suger + "\nUnits");

            } else {
                Toast.makeText(this, "Input is invalid!", Toast.LENGTH_LONG).show();
            }
            ed_sugerAmnt.getText().clear();
            close_kBoard();

        }

        storeData();

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

    public void close_kBoard() {

        if (getCurrentFocus() != null) {

            InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}

