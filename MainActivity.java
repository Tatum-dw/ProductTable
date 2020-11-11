package com.example.sqliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDatabase;
    EditText editID, editName, editPrice, editAvailability;
    Button btnAddProduct;
    Button btnViewAll;
    Button btnViewUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDatabase = new DatabaseHelper(this);

        editID = (EditText)findViewById(R.id.editText_id);
        editName = (EditText)findViewById(R.id.editText_name);
        editPrice = (EditText)findViewById(R.id.editText_price);
        editAvailability = (EditText)findViewById(R.id.editText_availability);
        btnAddProduct = (Button) findViewById(R.id.button_add);
        btnViewAll = (Button)findViewById(R.id.button_viewAll);
        btnViewUpdate = (Button)findViewById(R.id.button_viewUpdate);
        btnDelete = (Button)findViewById(R.id.button_delete);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }

    public void DeleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDatabase.deleteData(editID.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void UpdateData(){
        btnViewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDatabase.updateData(editID.getText().toString(), editName.getText().toString(),
                                editPrice.getText().toString(), editAvailability.getText().toString());
                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data Not Updated", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void AddData(){
        btnAddProduct.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      boolean isInserted =  myDatabase.insertData(editID.getText().toString(),
                                editName.getText().toString(), editPrice.getText().toString(),
                                editAvailability.getText().toString());
                      if(isInserted == true)
                          Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                      else
                          Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll(){
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDatabase.getAllData();
                        if(res.getCount() == 0){
                            //show message
                            showMessage("Error", "Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()) {
                            buffer.append("Product ID: " + res.getString(0)+ "\n");
                            buffer.append("Product Name: " + res.getString(1)+ "\n");
                            buffer.append("Unit Price: " + res.getString(2)+ "\n");
                            buffer.append("Availability: " + res.getString(3)+ "\n\n");
                        }
                        //show all data
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public boolean onCreateOptionMenu(Menu menu){
        //inflate the menu; this adds item to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //handle action bar item clicks here
        //the action bar will automatically handle clicks on the home button
        //as long as you specify a parent activity in the Android Manifest.xml
        int id = item.getItemId();
        return false;
    }
}