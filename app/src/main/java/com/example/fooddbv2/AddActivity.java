package com.example.fooddbv2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    EditText foodName, carbsGrams, fatGrams, proteinGrams, totalCal, remarks;
    Button addFood;
    private ArrayList<FoodList> foodList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        AutoCompleteTextView editText = findViewById(R.id.foodName);
        carbsGrams = findViewById(R.id.carbsGrams);
        fatGrams = findViewById(R.id.fatGrams);
        proteinGrams = findViewById(R.id.proteinGrams);
        totalCal = findViewById(R.id.totalCal);
        remarks = findViewById(R.id.remarks);
        addFood = findViewById(R.id.addFood);
        

        addFood.setOnClickListener(view -> {

            DBHelper db = new DBHelper(AddActivity.this);
            db.insertData(editText.getText().toString().trim(),
                    Float.valueOf(carbsGrams.getText().toString().trim()),
                    Float.valueOf(fatGrams.getText().toString().trim()),
                    Float.valueOf(proteinGrams.getText().toString().trim()),
                    Float.valueOf(totalCal.getText().toString().trim()),
                    remarks.getText().toString().trim());





        });


    }

}




