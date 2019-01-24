package com.karelvendla.phonebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.*;

public class DbHelper extends SQLiteOpenHelper {

    public static final String ZTABLE_NAME = "zipcodes";
    public static final int ZCOLUMN_CODE = 0;
    public static final int ZCOLUMN_CITY = 1;
    public static final String[] ZTABLE_COLUMNS = new String[] {"code", "city"};


    public static final  String ATABLE_NAME = "addresses";
    public static final int ACOLUMN_ID = 0;
    public static final int ACOLUMN_FIRSTNAME = 1;
    public static final int ACOLUMN_LASTNAME = 2;
    public static final int ACOLUMN_ADDRESS = 3;
    public static final int ACOLUMN_CODE = 4;
    public static final int ACOLUMN_PHONE = 5;
    public static final int ACOLUMN_MAIL = 6;
    public static final int ACOLUMN_DATE = 7;
    public static final int ACOLUMN_TITLE = 8;
    public static final int ACOLUMN_TEXT = 9;

    public static final String[] ATABLE_COLUMNS = new String[] {"id", "firstname", "lastname", "address", "code", "phone", "mail", "date", "title", "text"};


    private static final  String DBFILENAME = "phonebook.db";
    private static final int DBVERSION = 1;

    private static final String ZINITIAL_SCHEMA =
            "create table zipcodes " +
                    "(code char(4) primary key," +
                    "city varchar(30) not null)";
    private static final String AINITIAL_SCHEMA =
            "create table addresses (" +
                    "id integer primary key autoincrement," +
                    "firstname varchar(50) not null," +
                    "lastname varchar(30)," +
                    "address varchar(50)," +
                    "code char(4) not null," +
                    "phone varchar(20)," +
                    "mail varchar(50)," +
                    "date varchar(10)," +
                    "title varchar(50)," +
                    "text varchar(1000)," +
                    "foreign key (code) references zipcodes (codes))";

    private Context context;

    public DbHelper(Context context) {
        super(context, DBFILENAME, null, DBVERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ZINITIAL_SCHEMA);
        db.execSQL(AINITIAL_SCHEMA);
        db.execSQL(inserZipcodes());
    }

    private String inserZipcodes() {
        InputStream stream = context.getResources().openRawResource(R.raw.zipcodes);
        StringBuilder builder = new StringBuilder("insert into zipcodes (code, city) values");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))){
            addRow(builder, reader.readLine());
            for (String line = reader.readLine(); line != null; line = reader.readLine()){
                if (line.length() > 0) {
                    builder.append(",");
                    addRow(builder, line);
                }
            }
        }
        catch (Exception e) {}
        return builder.toString();
    }

    private void addRow(StringBuilder builder, String line) {
        String[] elems = line.split(",");
        builder.append("('");
        builder.append(elems[0]);
        builder.append("','");
        builder.append(elems[1]);
        builder.append("')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            try {
                db.execSQL("ALTER TABLE addresses ADD COLUMN text VARCHAR(1000) DEFAULT ''");
            } catch (Exception ex) {}
        }
    }
}

