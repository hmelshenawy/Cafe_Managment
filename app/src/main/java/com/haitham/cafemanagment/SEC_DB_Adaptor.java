package com.haitham.cafemanagment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import de.siegmar.fastcsv.writer.CsvAppender;
import de.siegmar.fastcsv.writer.CsvWriter;

public class SEC_DB_Adaptor {

    SEC_DB_Helper mhelper;
    SQLiteDatabase mSQLiteDB;
    Calendar calendar;
    ArrayList<Drink_class> items_ArrayList;
    File f;


    public SEC_DB_Adaptor(Context context) {

        mhelper = new SEC_DB_Helper(context);
        mSQLiteDB = mhelper.getWritableDatabase();
        calendar = Calendar.getInstance();
        f = new File(context.getExternalFilesDir(null).getPath() + File.separator + "Records.csv");

    }

    public ArrayList<Drink_class> getAllData() {

        items_ArrayList = new ArrayList<>();
        Collection<String[]> collection = new ArrayList<>();


        String[] columns = {SEC_DB_Helper.sCOLUMN_UID, SEC_DB_Helper.sCOLUMN_ITEM, SEC_DB_Helper.sCOLUMN_QUANTITY, SEC_DB_Helper.sCOLUMN_PRICE,
                SEC_DB_Helper.sCOLUMN_COFFEE_UNITS, SEC_DB_Helper.sCOLUMN_MILK_UNITS, SEC_DB_Helper.sCOLUMN_SUGER_UNITS, SEC_DB_Helper.sCOLUMN_TIME};
        Cursor cursor = mSQLiteDB.query(SEC_DB_Helper.sTABLE_NAME, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {

            long item_ID = cursor.getLong(0);
            String itemName = cursor.getString(1);
            int quantity = cursor.getInt(2);
            double price = cursor.getDouble(3);
            int coffee = cursor.getInt(4);
            int milk = cursor.getInt(5);
            int suger = cursor.getInt(6);
            String time = cursor.getString(7);

            Drink_class object = new Drink_class();
            object.id = item_ID;
            object.item_name = itemName;
            object.price = price;
            object.quantity = quantity;
            object.Coffee_Unit = coffee;
            object.Milk_Unit = milk;
            object.Suger_Unit = suger;
            object.order_Time = time;

            items_ArrayList.add(object);

            String[] arr = new String[8];
            arr[0] = String.valueOf(item_ID);
            arr[1] = itemName;
            arr[2] = String.valueOf(price);
            arr[3] = String.valueOf(quantity);
            arr[4] = String.valueOf(coffee);
            arr[5] = String.valueOf(milk);
            arr[6] = String.valueOf(suger);
            arr[7] = time;

            collection.add(arr);

        }
        return items_ArrayList;
    }

    public void csv_Writer() {

        String baseDir = Environment.DIRECTORY_DOWNLOADS;
        String fileName = "AnalysisData.csv";
        String filePath = baseDir + File.separator + fileName;
        CsvWriter writer = new CsvWriter();

        String[] arr = {"ID", "Item", "Quantity", "Price", "Coffee", "Milk", "Suger"};
        try {
            CsvAppender appender = writer.append(f, StandardCharsets.UTF_8);
            appender.appendLine("ID", "Item", "Quantity", "Price", "Coffee", "Milk", "Suger", "Time");
            for (int i = 0; i < getAllData().size(); i++) {


                appender.appendLine(String.valueOf(getAllData().get(i).id),
                        getAllData().get(i).item_name,
                        String.valueOf(getAllData().get(i).quantity),
                        String.valueOf(getAllData().get(i).price),
                        String.valueOf(getAllData().get(i).Coffee_Unit),
                        String.valueOf(getAllData().get(i).Milk_Unit),
                        String.valueOf(getAllData().get(i).Suger_Unit),
                        String.valueOf(getAllData().get(i).order_Time));
            }
            appender.flush();
            appender.endLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void insertData(String itemName, double price, int quantity, int coffee, int milk, int suger) {

        String time = calendar.getTime().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SEC_DB_Helper.sCOLUMN_ITEM, itemName);
        contentValues.put(SEC_DB_Helper.sCOLUMN_QUANTITY, quantity);
        contentValues.put(SEC_DB_Helper.sCOLUMN_PRICE, price * quantity);
        contentValues.put(SEC_DB_Helper.sCOLUMN_COFFEE_UNITS, coffee * quantity);
        contentValues.put(SEC_DB_Helper.sCOLUMN_MILK_UNITS, milk * quantity);
        contentValues.put(SEC_DB_Helper.sCOLUMN_SUGER_UNITS, suger * quantity);
        contentValues.put(SEC_DB_Helper.sCOLUMN_TIME, time.substring(0, time.length() - 15));

        long l = mSQLiteDB.insert(SEC_DB_Helper.sTABLE_NAME, null, contentValues);
    }

    public class SEC_DB_Helper extends SQLiteOpenHelper {

        private static final String sDB_NAME = "RECORDS";
        private static final int sDB_VER = 1;
        private static final String sTABLE_NAME = "RECORDS_TABLE";
        private static final String sCOLUMN_UID = "ID";
        private static final String sCOLUMN_ITEM = "Item_Name";
        private static final String sCOLUMN_QUANTITY = "Quantity";
        private static final String sCOLUMN_PRICE = "Price";
        private static final String sCOLUMN_COFFEE_UNITS = "CoffeeUnits";
        private static final String sCOLUMN_MILK_UNITS = "MilkUnits";
        private static final String sCOLUMN_SUGER_UNITS = "SugerUnits";
        private static final String sCOLUMN_TIME = "Time";
        private static final String sCREATE_TABLE = "CREATE TABLE " + sTABLE_NAME
                + "("
                + sCOLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + sCOLUMN_ITEM + " VARCHAR(255), "
                + sCOLUMN_QUANTITY + " VARCHAR(255), "
                + sCOLUMN_PRICE + " VARCHAR(255), "
                + sCOLUMN_COFFEE_UNITS + " VARCHAR(255), "
                + sCOLUMN_MILK_UNITS + " VARCHAR(255), "
                + sCOLUMN_SUGER_UNITS + " VARCHAR(255), "
                + sCOLUMN_TIME + " VARCHAR(255)); ";

        private static final String DROP_SEC_TABLE = "  DROP TABLE IF EXISTS " + sTABLE_NAME + "; ";
        Context mContext;


        public SEC_DB_Helper(@Nullable Context context) {
            super(context, sDB_NAME, null, sDB_VER);
            this.mContext = context;

        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            sqLiteDatabase.execSQL(sCREATE_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            sqLiteDatabase.execSQL(DROP_SEC_TABLE);
            onCreate(sqLiteDatabase);
        }
    }
}
