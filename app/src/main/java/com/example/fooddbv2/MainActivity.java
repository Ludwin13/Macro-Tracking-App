package com.example.fooddbv2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    DBHelper db;
//    ArrayList<String> foodID, foodName, carbsGrams, fatGrams, proteinGrams, totalCal, remarks;
    ArrayList<FoodList> foodList = new ArrayList<FoodList>();
    CustomAdapter customAdapter;



    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        db = new DBHelper(MainActivity.this);
//        foodName = findViewById(R.id.foodName);
//        carbsGrams = findViewById(R.id.carbsGrams);
//        fatGrams = findViewById(R.id.fatGrams);
//        proteinGrams = findViewById(R.id.proteinGrams);
//        totalCal = findViewById(R.id.totalCal);
//        remarks = findViewById(R.id.remarks);

//        foodID = new ArrayList<>();
//        foodName = new ArrayList<>();
//        carbsGrams = new ArrayList<>();
//        fatGrams = new ArrayList<>();
//        proteinGrams = new ArrayList<>();
//        totalCal = new ArrayList<>();
//        remarks = new ArrayList<>();



        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this, this, foodList);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            recreate();
        }
    }

//    void storeDataInArrays(){
//        Cursor cursor = db.getData();
//        if(cursor.getCount() == 0) {
//            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
//        }else{
//            while (cursor.moveToNext()) {
//                foodID.add(cursor.getString(0));
//                foodName.add(cursor.getString(1));
//                carbsGrams.add(cursor.getString(2));
//                fatGrams.add(cursor.getString(3));
//                proteinGrams.add(cursor.getString(4));
//                totalCal.add(cursor.getString(5));
//                remarks.add(cursor.getString(6));
//
//            }
//        }
//    }

    void storeDataInArrays(){
        Cursor cursor = db.getData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()) {
                foodList.add(new FoodList(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getFloat(2),
                        cursor.getFloat(3),
                        cursor.getFloat(4),
                        cursor.getFloat(5),
                        cursor.getString(6)));
            }
        }

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
try {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.my_menu, menu);
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
            customAdapter.getFilter().filter(newText);
            return false;
        }
    });


}catch (Exception e) {
    e.printStackTrace();
    System.out.println("onCreateOptionsMenu");
}
        return super.onCreateOptionsMenu(menu);
    }

//    void filter(String text) {
//        ArrayList<FoodList> filteredlist = new ArrayList<>();
//
//        for (FoodList item : foodListArrayList) {
//            if (item.getFoodName().toLowerCase().contains(text.toLowerCase())) {
//                filteredlist.add(item);
//            }
//        }
//        if (filteredlist.isEmpty()) {
//            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
//        } else {
//            customAdapter.filterList(filteredlist);
//        }
//    }
//
//        void buildRecyclerView () {
//            foodListArrayList = new ArrayList<>();
//
//            customAdapter = new CustomAdapter(MainActivity.this, this , foodListArrayList);
//            recyclerView.setAdapter(customAdapter);
//            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all) {

            confirmDialog();
        }

        if(item.getItemId() == R.id.daily_intake) {
            Intent intent = new Intent(this, DailyIntakeDisplay.class);
            startActivity(intent);
        }

        if(item.getItemId() == R.id.add_excel) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add Excel Values?");
            builder.setMessage("Are you sure you want to delete all Data?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DBHelper db = new DBHelper(MainActivity.this);
                    db.addExcelValues();
                    Intent intent = new Intent (MainActivity.this, MainActivity.class);
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

        return super.onOptionsItemSelected(item);
}



    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to add excel values?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper db = new DBHelper(MainActivity.this);
                db.deleteAllData();
                Intent intent = new Intent (MainActivity.this, MainActivity.class);
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


