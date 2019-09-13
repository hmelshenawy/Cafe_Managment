package com.haitham.cafemanagment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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

        //set stock values to item satus to corsponding TextView.
        tv_CoffeStat.setText(setColor(tv_CoffeStat, coffee) + "\nUnits");
        tv_MilkStat.setText(setColor(tv_MilkStat, milk) + "\nUnits");
        tv_SugerStat.setText(setColor(tv_SugerStat, suger) + "\nUnits");

    }

    public int setColor(TextView view, int i) {

        //check if stock is low (less than 30 serving), it will change the font color to red
        if (i <= 30) {

            view.setTextColor(Color.parseColor("#D81B60"));

        } else if (i > 30) {
            view.setTextColor(Color.parseColor("#27C5B4"));
        }

        return i;
    }

    public void add_Stock(View view) {

        if (view.getId() == R.id.btn_add1) {

            if (ed_coffAmnt.getText().toString().trim().length() > 0) {

                int addedcoffee = Integer.valueOf(ed_coffAmnt.getText().toString());
                coffee = addedcoffee + coffee;
                tv_CoffeStat.setText(setColor(tv_CoffeStat, coffee) + "\nUnits");

            } else {
                Toast.makeText(this, "Input is invalid!", Toast.LENGTH_LONG).show();
            }

            // clear the values from the editText and close the keyboard when prssing add button.
            ed_coffAmnt.getText().clear();
            close_kBoard();
        } else if (view.getId() == R.id.btn_add2) {


            if (ed_milkAmnt.getText().toString().trim().length() > 0) {

                int addedmilk = Integer.valueOf(ed_milkAmnt.getText().toString());
                milk = addedmilk + milk;
                tv_MilkStat.setText(setColor(tv_MilkStat, milk) + "\nUnits");

            } else {

                Toast.makeText(this, "Input is invalid!", Toast.LENGTH_LONG).show();
            }

            // clear the values from the editText and close the keyboard when prssing add button.
            ed_milkAmnt.getText().clear();
            close_kBoard();

        } else if (view.getId() == R.id.btn_add3) {

            if (ed_sugerAmnt.getText().toString().trim().length() > 0) {

                int addedsuger = Integer.valueOf(ed_sugerAmnt.getText().toString());
                suger = addedsuger + suger;
                tv_SugerStat.setText(setColor(tv_SugerStat, suger) + "\nUnits");

            } else {
                Toast.makeText(this, "Input is invalid!", Toast.LENGTH_LONG).show();
            }

            // clear the values from the editText and close the keyboard when prssing add button.
            ed_sugerAmnt.getText().clear();
            close_kBoard();

        }

        // store the new stock values to shared preferance
        storeData();

    }

    public void storeData() {

        // store the new stock values to shared preferance

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("coffee", coffee);
        editor.putInt("milk", milk);
        editor.putInt("suger", suger);
        editor.apply();
    }

    public void loadData() {

        // Load stock values from shared preferance when calling onCreate activity.

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        coffee = prefs.getInt("coffee", 0);
        milk = prefs.getInt("milk", 0);
        suger = prefs.getInt("suger", 0);

    }

    public void close_kBoard() {

        // it will close the keyboard after taking the text value from editText box.

        if (getCurrentFocus() != null) {

            InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}

