package com.example.fooddbv2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DailyIntakeDisplay extends AppCompatActivity {

    RecyclerView recyclerView_DI;
    FloatingActionButton add_button_DI;

    DBHelper db;
//    ArrayList<String> intakeID, intake_date, foodID_DI, multiplier, intake_type, remarks_DI;
    ArrayList<FoodListDI> foodListDI = new ArrayList<>();
    CustomAdapterDI customAdapterDI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_intake_display);

        recyclerView_DI = findViewById(R.id.recyclerView_DI);

        add_button_DI = findViewById(R.id.add_button_DI);
        add_button_DI.setOnClickListener(view -> {
            Intent intent = new Intent(DailyIntakeDisplay.this, AddActivityDI.class);
            startActivity(intent);
        });

        db = new DBHelper(DailyIntakeDisplay.this);
//        intakeID = new ArrayList<>();
//        intake_date = new ArrayList<>();
//        foodID_DI = new ArrayList<>();
//        multiplier = new ArrayList<>();
//        intake_type = new ArrayList<>();
//        remarks_DI = new ArrayList<>();

        storeDataInArraysDI();

        customAdapterDI = new CustomAdapterDI(DailyIntakeDisplay.this, this, foodListDI);
        recyclerView_DI.setAdapter(customAdapterDI);
        recyclerView_DI.setLayoutManager(new LinearLayoutManager(DailyIntakeDisplay.this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(DailyIntakeDisplay.this, DividerItemDecoration.VERTICAL);
        recyclerView_DI.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            recreate();
        }
    }

    void storeDataInArraysDI() {
        Cursor cursor = db.getDataDI();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                foodListDI.add( new FoodListDI(cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getFloat(3),
                cursor.getString(4),
                cursor.getString(5)));

            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.searchBar);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapterDI.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {

            confirmDialog();
        }
        return super.onOptionsItemSelected(item);


    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper db = new DBHelper(DailyIntakeDisplay.this);
                db.deleteAllDataDI();
                Intent intent = new Intent (DailyIntakeDisplay.this, DailyIntakeDisplay.class);
                startActivity(intent);
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