package com.example.fooddbv2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivityDI extends AppCompatActivity {

    EditText intake_date, foodID, multiplier, intake_type, remarks;
    Button addFoodDI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_di);

        intake_date = findViewById(R.id.intake_date);
        foodID = findViewById(R.id.foodID);
        multiplier = findViewById(R.id.multiplier);
        intake_type = findViewById(R.id.intake_type);
        remarks = findViewById(R.id.remarks);
        addFoodDI = findViewById(R.id.addFoodDI);

        addFoodDI.setOnClickListener(view -> {
            try {
                DBHelper db = new DBHelper(AddActivityDI.this);
                db.insertDataDI(intake_date.getText().toString(),
                        Integer.valueOf(foodID.getText().toString().trim()),
                        Float.valueOf(multiplier.getText().toString().trim()),
                        intake_type.getText().toString().trim(),
                        remarks.getText().toString().trim());
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });

    }
}