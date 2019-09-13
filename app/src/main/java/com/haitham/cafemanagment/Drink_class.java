package com.haitham.cafemanagment;

import android.os.Parcel;
import android.os.Parcelable;

public class Drink_class implements Parcelable {

    public static final Creator<Drink_class> CREATOR = new Creator<Drink_class>() {

        @Override
        public Drink_class createFromParcel(Parcel in) {
            return new Drink_class(in);
        }


        @Override
        public Drink_class[] newArray(int size) {
            return new Drink_class[size];
        }
    };

    String item_name;
    double price;
    int quantity;
    long id;
    int Coffee_Unit;
    int Milk_Unit;
    int Suger_Unit;
    String order_Time;


    public Drink_class() {
    }

    public Drink_class(String name, double price, int coffee, int milk, int suger) {

        this.price = price;
        this.item_name = name;
        this.Coffee_Unit = coffee;
        this.Milk_Unit = milk;
        this.Suger_Unit = suger;
    }


    protected Drink_class(Parcel in) {

        item_name = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
        Coffee_Unit = in.readInt();
        Milk_Unit = in.readInt();
        Suger_Unit = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(item_name);
        parcel.writeDouble(price);
        parcel.writeInt(quantity);
        parcel.writeInt(Coffee_Unit);
        parcel.writeInt(Milk_Unit);
        parcel.writeInt(Suger_Unit);
    }
}
