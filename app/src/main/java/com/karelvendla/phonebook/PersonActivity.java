package com.karelvendla.phonebook;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.*;
import android.view.*;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import java.util.*;

public class PersonActivity extends AppCompatActivity {


    private Zipcode zipcode = null;
    private Person person = null;
    private EditText txtZip, txtFirstName, txtLastName, txtAddress, txtPhone, txtEmail, txtDate, txtTitle, txtCity, txtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Intent intent = getIntent();

        txtFirstName = findViewById(R.id.etfirstname);
        txtLastName = findViewById(R.id.etlastname);
        txtAddress = findViewById(R.id.etaddress);
        txtPhone = findViewById(R.id.etphone);
        txtEmail = findViewById(R.id.etemail);
        txtDate = findViewById(R.id.etdate);
        txtTitle = findViewById(R.id.ettitle);
        txtZip = findViewById(R.id.etZipcode);
        txtCity = findViewById(R.id.etCitycode);
        txtText = findViewById(R.id.ettext);
        Object obj = intent.getSerializableExtra("zipcode");
        if (obj != null) {
            zipcode = (Zipcode) obj;
            Calendar cal = Calendar.getInstance();
            txtDate.setText(String.format("%02d-%02d-%d", cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR)));
        } else {
            person = (Person) intent.getSerializableExtra("person");
            txtFirstName.setText(person.getFirstname());
            txtLastName.setText(person.getLastname());
            txtAddress.setText(person.getAddress());
            txtPhone.setText(person.getPhone());
            txtEmail.setText(person.getMail());
            txtDate.setText(person.getDate());
            txtTitle.setText(person.getTitle());
            txtText.setText(person.getText());
            zipcode = person.getZipcode();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtZip.setText(zipcode.getCode());
        txtCity.setText(zipcode.getCity());
        disable(txtCity);
        disable(txtDate);
    }

    private void disable(EditText view) {
        view.setKeyListener(null);
        view.setEnabled(false);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void onOk(View view)
    {
        String fname = txtFirstName.getText().toString().trim();
        if (fname.length() > 0)
        {
            String lname = txtLastName.getText().toString().trim();
            String addr = txtAddress.getText().toString().trim();
            String phone = txtPhone.getText().toString().trim();
            String mail = txtEmail.getText().toString().trim();
            String date = txtDate.getText().toString().trim();
            String title = txtTitle.getText().toString().trim();
            String text = txtText.getText().toString().trim();
            String zipCode = txtZip.getText().toString().trim();


            DbHelper dbHelper = new DbHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues(9);


            if (zipCode.length() == 4)
            {
                Log.d("ZipcodeCheck","Kas Zipcode saab kontrollitud?");
                values.put(DbHelper.ATABLE_COLUMNS[DbHelper.ACOLUMN_CODE], zipCode);
            } else {

                Toast.makeText(this, "Invalid zipcode", Toast.LENGTH_SHORT).show();
            }

            values.put(DbHelper.ATABLE_COLUMNS[DbHelper.ACOLUMN_FIRSTNAME], fname);
            values.put(DbHelper.ATABLE_COLUMNS[DbHelper.ACOLUMN_LASTNAME], lname);
            values.put(DbHelper.ATABLE_COLUMNS[DbHelper.ACOLUMN_ADDRESS], addr);
            values.put(DbHelper.ATABLE_COLUMNS[DbHelper.ACOLUMN_PHONE], phone);
            values.put(DbHelper.ATABLE_COLUMNS[DbHelper.ACOLUMN_MAIL], mail);
            values.put(DbHelper.ATABLE_COLUMNS[DbHelper.ACOLUMN_TITLE], title);
            values.put(DbHelper.ATABLE_COLUMNS[DbHelper.ACOLUMN_TEXT], text);

            if (person == null)
            {
                values.put(DbHelper.ATABLE_COLUMNS[DbHelper.ACOLUMN_DATE], date);
                db.insert(DbHelper.ATABLE_NAME, null, values);
            }
            else
            {
                Calendar cal = Calendar.getInstance();
                values.put(DbHelper.ATABLE_COLUMNS[DbHelper.ACOLUMN_DATE], String.format("%02d-%02d-%d", cal.get(Calendar.DAY_OF_MONTH),
                        cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR)));
                String[] args = { "" + person.getId() };
                db.update(DbHelper.ATABLE_NAME, values, "id = ?", args);
            }
            db.close();
            onSupportNavigateUp();
        }
    }

    public void onRemove(View view)
    {
        if (person != null)
        {
            DbHelper dbHelper = new DbHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String[] args = { "" + person.getId() };
            db.delete(DbHelper.ATABLE_NAME, "id = ?", args);
            db.close();
            onSupportNavigateUp();
        }
    }
}