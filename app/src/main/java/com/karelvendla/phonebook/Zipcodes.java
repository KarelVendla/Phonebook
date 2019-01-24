package com.karelvendla.phonebook;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Zipcodes implements Serializable {

    private List<Zipcode> zipcodes = new ArrayList<>();

    private List<String> Zips = new ArrayList<String>();

    public Zipcodes(SQLiteDatabase db) {
        try {
            Cursor cursor = db.query(DbHelper.ZTABLE_NAME, DbHelper.ZTABLE_COLUMNS,
                    null,null,null,null,null);

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String code = cursor.getString(DbHelper.ZCOLUMN_CODE);
                String city = cursor.getString(DbHelper.ZCOLUMN_CITY);
                zipcodes.add(new Zipcode(code,city));
            }
            cursor.close();//TODO: PUUDUS
        } catch (Exception ex) { zipcodes.clear(); }
    }
    public List<Zipcode> getZipcodes(){return zipcodes;}

}

