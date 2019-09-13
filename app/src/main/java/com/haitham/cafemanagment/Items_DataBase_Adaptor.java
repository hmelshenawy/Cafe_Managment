package com.haitham.cafemanagment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Items_DataBase_Adaptor {

    Items_DB_helper mhelper;
    SQLiteDatabase mSQLiteDB;
    ArrayList<Drink_class> items_ArrayList;


    public Items_DataBase_Adaptor(Context context) {
        mhelper = new Items_DB_helper(context);
        mSQLiteDB = mhelper.getWritableDatabase();
    }

    public void insertData(String itemName, double price, int quantity, int coffee, int milk, int suger) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Items_DB_helper.COLUMN_ITEMNAME, itemName);
        contentValues.put(Items_DB_helper.COLUMN_PRICE, price);
        contentValues.put(Items_DB_helper.COLUMN_QUANTITY, quantity);
        contentValues.put(Items_DB_helper.COLUMN_COFFEE_UNITS, coffee);
        contentValues.put(Items_DB_helper.COLUMN_MILK_UNITS, milk);
        contentValues.put(Items_DB_helper.COLUMN_SUGER_UNITS, suger);
        long l = mSQLiteDB.insert(Items_DB_helper.TABLE_NAME, null, contentValues);

    }

    public ArrayList<Drink_class> getAllData() {

        items_ArrayList = new ArrayList<>();
        String[] columns = {Items_DB_helper.UID, Items_DB_helper.COLUMN_ITEMNAME, Items_DB_helper.COLUMN_QUANTITY, Items_DB_helper.COLUMN_PRICE,
                Items_DB_helper.COLUMN_COFFEE_UNITS, Items_DB_helper.COLUMN_MILK_UNITS, Items_DB_helper.COLUMN_SUGER_UNITS};
        Cursor cursor = mSQLiteDB.query(Items_DB_helper.TABLE_NAME, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {

            long item_ID = cursor.getLong(0);
            String itemName = cursor.getString(1);
            int quantity = cursor.getInt(2);
            double price = cursor.getDouble(3);
            int coffee = cursor.getInt(4);
            int milk = cursor.getInt(5);
            int suger = cursor.getInt(6);

            Drink_class object = new Drink_class();
            object.id = item_ID;
            object.item_name = itemName;
            object.price = price;
            object.quantity = quantity;
            object.Coffee_Unit = coffee;
            object.Milk_Unit = milk;
            object.Suger_Unit = suger;

            items_ArrayList.add(object);
        }
        return items_ArrayList;
    }

    public void removeAllData_FirstTable() {

        String delete = "DELETE FROM " + Items_DB_helper.TABLE_NAME;
        mSQLiteDB.execSQL(delete);
    }

    public void removeRow_FirstTable(long id){

        mSQLiteDB.delete(Items_DB_helper.TABLE_NAME, Items_DB_helper.UID + " = " + id, null);
    }

    class Items_DB_helper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "SHOP_DATABASE";
        private static final String TABLE_NAME = "FIRST_TABLE";
        private static final int DATABASE_VER = 1;
        private static final String UID = "ID";
        private static final String COLUMN_ITEMNAME = "Item";
        private static final String COLUMN_QUANTITY = "Quantity";
        private static final String COLUMN_PRICE = "Price";
        private static final String COLUMN_COFFEE_UNITS = "CoffeeUnits";
        private static final String COLUMN_MILK_UNITS = "MilkUnits";
        private static final String COLUMN_SUGER_UNITS = "SugerUnits";
        private static final String CREATE_FIRST_TABLE = " CREATE TABLE " + TABLE_NAME
                + " ( "
                + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ITEMNAME + " VARCHAR(255), "
                + COLUMN_QUANTITY + " VARCHAR(255), "
                + COLUMN_PRICE + " VARCHAR(255),"
                + COLUMN_COFFEE_UNITS + " VARCHAR(255),"
                + COLUMN_MILK_UNITS + " VARCHAR(255), "
                + COLUMN_SUGER_UNITS + " VARCHAR(255));";

        private static final String DROP_FIRST_TABLE = "  DROP TABLE IF EXISTS " + TABLE_NAME + "; ";
        Context mContext;




        public Items_DB_helper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VER);
            this.mContext = context;

        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            try {
                sqLiteDatabase.execSQL(CREATE_FIRST_TABLE);
            } catch (SQLException e) {
                System.out.println("SQL Exception");
            }
        }


        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            sqLiteDatabase.execSQL(DROP_FIRST_TABLE);
            onCreate(sqLiteDatabase);

        }
    }
}
