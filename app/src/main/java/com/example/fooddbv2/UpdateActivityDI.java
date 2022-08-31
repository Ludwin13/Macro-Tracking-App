package com.example.fooddbv2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivityDI extends AppCompatActivity {

    EditText intakeDateUpdate, foodIDUpdate, multiplierUpdate, intakeTypeUpdate, remarksUpdate, intake_date, foodIDquery;
    Button update_button, delete_button, showTotalCal;
    String  id, date, foodID, multi, type, remarks;
    DBHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_di);

        intakeDateUpdate = findViewById(R.id.intakeDateUpdate);
        foodIDUpdate = findViewById(R.id.foodIDUpdate);
        multiplierUpdate = findViewById(R.id.multiplierUpdate);
        intakeTypeUpdate = findViewById(R.id.intakeTypeUpdate);
        remarksUpdate = findViewById(R.id.remarksUpdate);
        update_button = findViewById(R.id.updateFoodDI);
        delete_button = findViewById(R.id.deleteFood);
        showTotalCal = findViewById(R.id.showTotalCalDI);


        getAndSetIntentDateDI();


        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setTitle(date);
        }

        update_button.setOnClickListener(view -> {
            DBHelper db = new DBHelper(UpdateActivityDI.this);
            date = intakeDateUpdate.getText().toString().trim();
            foodID = foodIDUpdate.getText().toString().trim();
            multi = multiplierUpdate.getText().toString().trim();
            type = intakeTypeUpdate.getText().toString().trim();
            remarks = remarksUpdate.getText().toString().trim();

            intake_date = findViewById(R.id.intake_date);
            foodIDquery = findViewById(R.id.foodID);

            db.updateDataDI(id, date, foodID, multi, type, remarks);
        });

        delete_button.setOnClickListener(view -> {
            try {
                confirmDialog();
            }catch (Exception e) {
                e.printStackTrace();
            }
        });

        showTotalCal();
    }

    void getAndSetIntentDateDI(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("date") && getIntent().hasExtra("foodID") &&
                getIntent().hasExtra("multi") && getIntent().hasExtra("type") && getIntent().hasExtra("remarks")) {

            id = getIntent().getStringExtra("id");
            date = getIntent().getStringExtra("date");
            foodID = getIntent().getStringExtra("foodID");
            multi = getIntent().getStringExtra("multi");
            type = getIntent().getStringExtra("type");
            remarks = getIntent().getStringExtra("remarks");


            intakeDateUpdate.setText(date);
            foodIDUpdate.setText(foodID);
            multiplierUpdate.setText(multi);
            intakeTypeUpdate.setText(type);
            remarksUpdate.setText(remarks);

        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }


    void showTotalCal() {
        showTotalCal.setOnClickListener(view -> {
            try{
                DBHelper db = new DBHelper(UpdateActivityDI.this);
                Cursor cursor = db.showTotalCal(foodID, date);

                if (cursor.getCount() == 0) {
                    totalCalMsg("ERROR", "No data");
                    return;
                } else {
                    StringBuffer buffer = new StringBuffer();
                    while (cursor.moveToNext()) {
                        buffer.append("intake_date: " + cursor.getString(0) + "\n");
                        buffer.append("carbs_grams: " + cursor.getString(1) + "\n");
                        buffer.append("fat_grams: " + cursor.getString(2) + "\n");
                        buffer.append("protein_grams: " + cursor.getString(3) + "\n");
                        buffer.append("total_cal: " + cursor.getString(4));
                    }
                    totalCalMsg("Data", buffer.toString());
                }
            }catch (Exception e) {
                e.printStackTrace(); }
        });

    }


    void totalCalMsg(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete" + date + " ?");
        builder.setMessage("Are you sure you want to delete " + date + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper db = new DBHelper(UpdateActivityDI.this);
                db.deleteOneRowDI(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }

        });
        builder.create().show();
    }

    void displayTotalCalories(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Total Calories: ");
        builder.setMessage("Are you sure you want to delete " + date + " ?");
    }
}