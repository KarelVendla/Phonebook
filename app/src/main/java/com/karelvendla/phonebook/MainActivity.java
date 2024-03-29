package com.karelvendla.phonebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.*;
import android.content.Intent;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    public static final int MENU_CREATE = Menu.FIRST + 1;
    public static final int MENU_SHOW = Menu.FIRST + 2;
    public static final int MENU_SEARCH = Menu.FIRST + 3;

    private People people;

    private SQLiteDatabase db;
    private DbHelper dbHelper;
    private ListView lstCodes;
    private EditText txtCode, txtCity;
    private ArrayAdapter<Zipcode> adapter;
    private Zipcodes zipcodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle(R.string.app_name);

        dbHelper = new DbHelper(this);
        db = dbHelper.getReadableDatabase();
        zipcodes = new Zipcodes(db);
        lstCodes = findViewById(R.id.lstCodes);
        txtCode = findViewById(R.id.etCode);
        txtCity = findViewById(R.id.etCity);
        registerForContextMenu(lstCodes);

        displayCodes("", "");
    }

    private void displayCodes(String code, String city) {
        lstCodes.setAdapter(adapter = new ArrayAdapter<Zipcode>(this,
                android.R.layout.simple_list_item_1, getCodes(code, city)));
    }

    private List<Zipcode> getCodes(String code, String city) {
        List<Zipcode> list = new ArrayList<>();
        for (Zipcode zipcode : zipcodes.getZipcodes())
            if (zipcode.getCode().startsWith(code) && zipcode.getCity().contains(city))
                list.add(zipcode);
        return list;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, MENU_SHOW, Menu.NONE, R.string.shwContact);
        menu.add(Menu.NONE, MENU_CREATE, Menu.NONE, R.string.crtContact);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Zipcode zipcode = adapter.getItem(menuInfo.position);
        switch (item.getItemId()) {
            case MENU_SHOW:
                showPeople(zipcode);
                return true;
            case MENU_CREATE:
                createPerson(zipcode);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void showPeople(Zipcode zipcode) {
        people = new People(db, zipcode);
        if (people.getPeople().size() == 0) {
            Toast.makeText(this, R.string.NCF, Toast.LENGTH_LONG).show();
        } else if (people.getPeople().size() == 1) {
            Intent intent = new Intent(this, PersonActivity.class);
            intent.putExtra("person", people.getPeople().get(0));
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, PeopleActivity.class);
            intent.putExtra("people", people);
            startActivity(intent);
        }
    }

    private void createPerson(Zipcode zipcode) {
        Intent intent = new Intent(this, PersonActivity.class);
        intent.putExtra("zipcode", zipcode);
        startActivity(intent);
    }

    public void onClear(View view) {
        txtCode.setText("");
        txtCity.setText("");
    }

    public void onOk(View view) {
        displayCodes(txtCode.getText().toString(), txtCity.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, MENU_SEARCH, Menu.NONE, R.string.srchContact);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_SEARCH:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}