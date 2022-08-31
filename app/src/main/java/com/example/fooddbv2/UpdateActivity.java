package com.example.fooddbv2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText foodNameUpdate, carbsGramsUpdate, fatGramsUpdate, proteinGramsUpdate, totalCalUpdate, remarksUpdate;
    Button update_button, delete_button;
    String id, name, carbs, fat, protein, cal, remarks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        foodNameUpdate = findViewById(R.id.foodNameUpdate);
        carbsGramsUpdate = findViewById(R.id.carbsGramsUpdate);
        fatGramsUpdate = findViewById(R.id.fatGramsUpdate);
        proteinGramsUpdate = findViewById(R.id.proteinGramsUpdate);
        totalCalUpdate = findViewById(R.id.totalCalUpdate);
        remarksUpdate = findViewById(R.id.remarksUpdate);
        update_button = findViewById(R.id.updateFood);
        delete_button = findViewById(R.id.deleteFood);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }

        update_button.setOnClickListener(view -> {
            DBHelper db = new DBHelper(UpdateActivity.this);
            name = foodNameUpdate.getText().toString().trim();
            carbs = carbsGramsUpdate.getText().toString().trim();
            fat = fatGramsUpdate.getText().toString().trim();
            protein = proteinGramsUpdate.getText().toString().trim();
            cal = totalCalUpdate.getText().toString().trim();
            remarks = remarksUpdate.getText().toString().trim();
            db.updateData(id, name, carbs, fat, protein, cal, remarks);

        });
        delete_button.setOnClickListener(view -> confirmDialog());


    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("carbs") &&
                getIntent().hasExtra("fat") && getIntent().hasExtra("protein") && getIntent().hasExtra("cal") &&
                getIntent().hasExtra("remarks")) {
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            carbs = getIntent().getStringExtra("carbs");
            fat = getIntent().getStringExtra("fat");
            protein = getIntent().getStringExtra("protein");
            cal = getIntent().getStringExtra("cal");
            remarks = getIntent().getStringExtra("remarks");

            //Setting Intent Data
            foodNameUpdate.setText(name);
            carbsGramsUpdate.setText(carbs);
            fatGramsUpdate.setText(fat);
            proteinGramsUpdate.setText(protein);
            totalCalUpdate.setText(cal);
            remarksUpdate.setText(remarks);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete" + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper db = new DBHelper(UpdateActivity.this);
                db.deleteOneRow(id);
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

}