package com.example.fooddbv2;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;


class DBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME ="food_db.db";
    private static final int DATABASE_VERSION = 1;

    //Food List Table
    private static final String ID_COL ="foodID";
    private static final String TABLE_NAME ="food_list";
    private static final String FOOD_NAME ="foodName";
    private static final String CARBS_COL ="carbsGrams";
    private static final String FAT_COL ="fatGrams";
    private static final String PROTEIN_COL ="proteinGrams";
    private static final String TOTAL_COL ="totalCal";
    private static final String REMARKS_COL ="remarks";

    //Daily Intake Table
    private static final String ID_COL_DI = "intakeID";
    private static final String TABLE_NAME_DI  ="daily_intake";
    private static final String INTAKE_DATE = "intake_date";
    private static final String FOOD_ID = "foodID_DI";
    private static final String MULTIPLIER_COL  = "multiplier";
    private static final String INTAKE_TYPE_COL = "intake_type";
    private static final String REMARKS_DI_COL  = "remarks_DI";

    private static final String CREATE_FOOD_LIST = "CREATE TABLE " + TABLE_NAME + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FOOD_NAME + " TEXT, "
            + CARBS_COL + " FLOAT, "
            + FAT_COL + " FLOAT, "
            + PROTEIN_COL + " FLOAT, "
            + TOTAL_COL + " FLOAT, "
            + REMARKS_COL + " TEXT);";

    private static final String CREATE_DAILY_INTAKE = "CREATE TABLE " + TABLE_NAME_DI + " ("
            + ID_COL_DI + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + INTAKE_DATE + " TEXT, "
            + FOOD_ID + " INTEGER, "
            + MULTIPLIER_COL + " FLOAT, "
            + INTAKE_TYPE_COL + " TEXT, "
            + REMARKS_DI_COL + " TEXT);";


    // creating a constructor for our database handler.
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_FOOD_LIST);
        db.execSQL(CREATE_DAILY_INTAKE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DI);
        onCreate(db);
    }


    //CREATE DATA
    void insertData(String foodName, float carbsGrams, float fatGrams, float proteinGrams, float totalCal, String remarks) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FOOD_NAME, foodName);
        values.put(CARBS_COL, carbsGrams);
        values.put(FAT_COL, fatGrams);
        values.put(PROTEIN_COL, proteinGrams);
        values.put(TOTAL_COL, totalCal);
        values.put(REMARKS_COL, remarks);

        long result = db.insert(TABLE_NAME, null, values);
        if(result == -1) {
            Toast.makeText(context, "Entry not inserted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Entry inserted", Toast.LENGTH_SHORT).show();
        }
    }

    void insertDataDI(String intake_date, int foodID_DI, float multiplier, String intake_type, String remarks_DI) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(INTAKE_DATE, intake_date);
        values.put(FOOD_ID, foodID_DI);
        values.put(MULTIPLIER_COL, multiplier);
        values.put(INTAKE_TYPE_COL, intake_type);
        values.put(REMARKS_DI_COL, remarks_DI);

        long result = db.insert(TABLE_NAME_DI, null, values);
        if(result == -1) {
            Toast.makeText(context, "Entry not inserted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Entry inserted", Toast.LENGTH_SHORT).show();
        }
    }

    //READ DATA
    void updateData(String row_id, String foodName, String carbsGrams, String fatGrams, String proteinGrams, String totalCal, String remarks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FOOD_NAME, foodName);
        values.put(CARBS_COL, carbsGrams);
        values.put(FAT_COL, fatGrams);
        values.put(PROTEIN_COL, proteinGrams);
        values.put(TOTAL_COL, totalCal);
        values.put(REMARKS_COL, remarks);

        long result = db.update(TABLE_NAME, values, "foodID=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully updated ", Toast.LENGTH_SHORT).show();
        }
    }

    void updateDataDI(String row_id_di, String intake_date, String foodID_DI, String multiplier, String intake_type, String remarks_DI){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(INTAKE_DATE, intake_date);
        values.put(FOOD_ID, foodID_DI);
        values.put(MULTIPLIER_COL, multiplier);
        values.put(INTAKE_TYPE_COL, intake_type);
        values.put(REMARKS_DI_COL, remarks_DI);

        long result = db.update(TABLE_NAME_DI, values, "intakeID=?", new String[]{row_id_di});
        if(result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully updated ", Toast.LENGTH_SHORT).show();
        }
    }

    //READ DATA
    Cursor getData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor getDataDI() {
        String query = "SELECT * FROM " + TABLE_NAME_DI;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor showTotalCal(String foodID_DI, String intake_date){
        String query = "SELECT "+ INTAKE_DATE +", " +
                "sum(" + MULTIPLIER_COL + "*" + CARBS_COL + ") AS " + CARBS_COL + ", " +
                "sum(" + MULTIPLIER_COL + "*" + FAT_COL + ") AS " + FAT_COL + ", " +
                "SUM(" + MULTIPLIER_COL + "*" + PROTEIN_COL + ") AS " + PROTEIN_COL + ", " +
                "SUM(" + MULTIPLIER_COL + "*" + TOTAL_COL + ") AS " + TOTAL_COL + " " +
                "FROM " + TABLE_NAME_DI + " " +
                "LEFT JOIN " + TABLE_NAME + " on " + TABLE_NAME_DI + "." + FOOD_ID + " = " + TABLE_NAME + "." + ID_COL + " " +
                "WHERE " + INTAKE_DATE + " " +
                "in (SELECT " + INTAKE_DATE + " FROM " + TABLE_NAME_DI + " WHERE " + FOOD_ID + " = ? AND " + INTAKE_DATE + " = ?) ";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, new String[]{foodID_DI, intake_date});

        }
        return cursor;
    }



    //DELETE DATA
    void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "foodID=?", new String[]{row_id});
        if(result == -1) {
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);


    }

    void deleteOneRowDI(String row_id_di) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "intakeID=?", new String[]{row_id_di});
        if(result == -1) {
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllDataDI() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_DI);
    }

    void addExcelValues(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FOOD_NAME, "Chicken Thigh 100g");	values.put(CARBS_COL, 0);	values.put(FAT_COL, 10.9);	values.put(PROTEIN_COL, 26);	values.put(TOTAL_COL, 209);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Chicken Breast (100g)");	values.put(CARBS_COL, 0);	values.put(FAT_COL, 3.6);	values.put(PROTEIN_COL, 31);	values.put(TOTAL_COL, 165);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Pork Shoulder cut (100g)");	values.put(CARBS_COL, 0);	values.put(FAT_COL, 7.14);	values.put(PROTEIN_COL, 19.55);	values.put(TOTAL_COL, 148);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Porkchop (100g)");	values.put(CARBS_COL, 0);	values.put(FAT_COL, 11);	values.put(PROTEIN_COL, 26);	values.put(TOTAL_COL, 209);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Bangus (100g)");	values.put(CARBS_COL, 0);	values.put(FAT_COL, 6.73);	values.put(PROTEIN_COL, 20.53);	values.put(TOTAL_COL, 148);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Chicken Drumstick No Skin 100g");	values.put(CARBS_COL, 0);	values.put(FAT_COL, 5.7);	values.put(PROTEIN_COL, 28.3);	values.put(TOTAL_COL, 172);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Chicken Drumstick w/ skin 100g");	values.put(CARBS_COL, 0);	values.put(FAT_COL, 14.62);	values.put(PROTEIN_COL, 37.1);	values.put(TOTAL_COL, 280);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Rice (100g)");	values.put(CARBS_COL, 27.86);	values.put(FAT_COL, 0.46);	values.put(PROTEIN_COL, 2.86);	values.put(TOTAL_COL, 129);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Carrots (100g)");	values.put(CARBS_COL, 9.6);	values.put(FAT_COL, 0.2);	values.put(PROTEIN_COL, 0.9);	values.put(TOTAL_COL, 41);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Tomato (100g)");	values.put(CARBS_COL, 3.9);	values.put(FAT_COL, 0.2);	values.put(PROTEIN_COL, 0.9);	values.put(TOTAL_COL, 18);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Cucumber (100g)");	values.put(CARBS_COL, 3.6);	values.put(FAT_COL, 0.1);	values.put(PROTEIN_COL, 0.7);	values.put(TOTAL_COL, 15);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Sitaw (100g)");	values.put(CARBS_COL, 7);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 2);	values.put(TOTAL_COL, 31);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Avocado (100g)");	values.put(CARBS_COL, 8.5);	values.put(FAT_COL, 15);	values.put(PROTEIN_COL, 2);	values.put(TOTAL_COL, 160);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Apple (192g)");	values.put(CARBS_COL, 29);	values.put(FAT_COL, 0.3);	values.put(PROTEIN_COL, 0.4);	values.put(TOTAL_COL, 121);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Ripe Mango (100g)");	values.put(CARBS_COL, 15);	values.put(FAT_COL, 0.4);	values.put(PROTEIN_COL, 0.8);	values.put(TOTAL_COL, 60);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Lakatan (100g)");	values.put(CARBS_COL, 22.8);	values.put(FAT_COL, 0.3);	values.put(PROTEIN_COL, 1.1);	values.put(TOTAL_COL, 89);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Kangkong (100g)");	values.put(CARBS_COL, 3.14);	values.put(FAT_COL, 0.2);	values.put(PROTEIN_COL, 2.6);	values.put(TOTAL_COL, 25);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Ampalaya (100g)");	values.put(CARBS_COL, 4);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 1);	values.put(TOTAL_COL, 17);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Corn (100g)");	values.put(CARBS_COL, 21);	values.put(FAT_COL, 1.5);	values.put(PROTEIN_COL, 3.4);	values.put(TOTAL_COL, 96);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Green Peas (100g)");	values.put(CARBS_COL, 16);	values.put(FAT_COL, 0.2);	values.put(PROTEIN_COL, 5.4);	values.put(TOTAL_COL, 84);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Red Onion (100g)");	values.put(CARBS_COL, 10.11);	values.put(FAT_COL, 0.08);	values.put(PROTEIN_COL, 0.92);	values.put(TOTAL_COL, 42);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Bell Pepper 100g");	values.put(CARBS_COL, 6.7);	values.put(FAT_COL, 0.2);	values.put(PROTEIN_COL, 0.9);	values.put(TOTAL_COL, 28);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Curry Powder 3 tsp");	values.put(CARBS_COL, 3.3);	values.put(FAT_COL, 0.8);	values.put(PROTEIN_COL, 0.9);	values.put(TOTAL_COL, 19.5);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Pepper 1tsp");	values.put(CARBS_COL, 1.36);	values.put(FAT_COL, 0.07);	values.put(PROTEIN_COL, 0.23);	values.put(TOTAL_COL, 5);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Garlic 100g");	values.put(CARBS_COL, 33);	values.put(FAT_COL, 0.5);	values.put(PROTEIN_COL, 6.4);	values.put(TOTAL_COL, 149);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Mixed Vegetables 227g");	values.put(CARBS_COL, 21);	values.put(FAT_COL, 1);	values.put(PROTEIN_COL, 7);	values.put(TOTAL_COL, 113);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Watermelon 100g");	values.put(CARBS_COL, 7.6);	values.put(FAT_COL, 0.2);	values.put(PROTEIN_COL, 0.6);	values.put(TOTAL_COL, 30);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Potato 100g");	values.put(CARBS_COL, 15.4);	values.put(FAT_COL, 0.1);	values.put(PROTEIN_COL, 2.2);	values.put(TOTAL_COL, 75);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Tomato Sauce 60g");	values.put(CARBS_COL, 5);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 1.25);	values.put(TOTAL_COL, 25);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "White Onion 100g");	values.put(CARBS_COL, 10);	values.put(FAT_COL, 0.2);	values.put(PROTEIN_COL, 1.4);	values.put(TOTAL_COL, 44);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Eggplant 100g");	values.put(CARBS_COL, 8.7);	values.put(FAT_COL, 0.2);	values.put(PROTEIN_COL, 0.8);	values.put(TOTAL_COL, 35);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Squash 100g");	values.put(CARBS_COL, 11.69);	values.put(FAT_COL, 0.1);	values.put(PROTEIN_COL, 1);	values.put(TOTAL_COL, 45);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Sayote 100g");	values.put(CARBS_COL, 4.5);	values.put(FAT_COL, 0.1);	values.put(PROTEIN_COL, 0.8);	values.put(TOTAL_COL, 19);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Papaya 100g");	values.put(CARBS_COL, 11);	values.put(FAT_COL, 0.3);	values.put(PROTEIN_COL, 0.5);	values.put(TOTAL_COL, 43);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Guava 100g");	values.put(CARBS_COL, 14);	values.put(FAT_COL, 1);	values.put(PROTEIN_COL, 2.6);	values.put(TOTAL_COL, 68);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Large egg (50g)");	values.put(CARBS_COL, 0.6);	values.put(FAT_COL, 5.3);	values.put(PROTEIN_COL, 6.3);	values.put(TOTAL_COL, 77);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Jumbo TJ Hotdog");	values.put(CARBS_COL, 4);	values.put(FAT_COL, 10);	values.put(PROTEIN_COL, 8);	values.put(TOTAL_COL, 224);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Hash Brown (100g)");	values.put(CARBS_COL, 34.9);	values.put(FAT_COL, 12.44);	values.put(PROTEIN_COL, 2.98);	values.put(TOTAL_COL, 263);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Olive Oil 1tbsp");	values.put(CARBS_COL, 0);	values.put(FAT_COL, 14);	values.put(PROTEIN_COL, 0);	values.put(TOTAL_COL, 124);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Quaker Oats 40g");	values.put(CARBS_COL, 26);	values.put(FAT_COL, 3);	values.put(PROTEIN_COL, 5);	values.put(TOTAL_COL, 153);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Pancake Magnolia w/ egg 40g");	values.put(CARBS_COL, 31);	values.put(FAT_COL, 1.5);	values.put(PROTEIN_COL, 3);	values.put(TOTAL_COL, 149);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "CDO cheesy chicken franks 55g");	values.put(CARBS_COL, 1);	values.put(FAT_COL, 7);	values.put(PROTEIN_COL, 7);	values.put(TOTAL_COL, 90);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Oil 1 tbsp (13.6g)");	values.put(CARBS_COL, 0);	values.put(FAT_COL, 14);	values.put(PROTEIN_COL, 0);	values.put(TOTAL_COL, 120);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "CDO chicken franks 55g");	values.put(CARBS_COL, 1);	values.put(FAT_COL, 8);	values.put(PROTEIN_COL, 8);	values.put(TOTAL_COL, 110);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Magnolia Gold Salted Butter 15g");	values.put(CARBS_COL, 0);	values.put(FAT_COL, 12);	values.put(PROTEIN_COL, 0);	values.put(TOTAL_COL, 110);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Garlic Powder 1tsp");	values.put(CARBS_COL, 2.3);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 0.5);	values.put(TOTAL_COL, 10);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Paprika 1tsp");	values.put(CARBS_COL, 1.24);	values.put(FAT_COL, 0.296);	values.put(PROTEIN_COL, 0.325);	values.put(TOTAL_COL, 6.49);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Chili Powder 1tsp");	values.put(CARBS_COL, 1.3);	values.put(FAT_COL, 0.4);	values.put(PROTEIN_COL, 0.4);	values.put(TOTAL_COL, 7.5);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Bagoong 30g");	values.put(CARBS_COL, 1.33672);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 1.85504);	values.put(TOTAL_COL, 12.8216);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Purefoods Ham 1 Slice 32g");	values.put(CARBS_COL, 3);	values.put(FAT_COL, 1);	values.put(PROTEIN_COL, 6);	values.put(TOTAL_COL, 41);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "555 tuna spicy caldereta (56g)");	values.put(CARBS_COL, 7);	values.put(FAT_COL, 1);	values.put(PROTEIN_COL, 5);	values.put(TOTAL_COL, 60);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Chinese Luncheon Meat (68g)");	values.put(CARBS_COL, 9);	values.put(FAT_COL, 7);	values.put(PROTEIN_COL, 6);	values.put(TOTAL_COL, 121);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Libby's Vienna Sausage (130g)");	values.put(CARBS_COL, 2);	values.put(FAT_COL, 19.5);	values.put(PROTEIN_COL, 10);	values.put(TOTAL_COL, 221);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Chunky Corned Tuna (56g)");	values.put(CARBS_COL, 3);	values.put(FAT_COL, 2.5);	values.put(PROTEIN_COL, 8);	values.put(TOTAL_COL, 70);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Popcorn (100g)");	values.put(CARBS_COL, 77.78);	values.put(FAT_COL, 4.54);	values.put(PROTEIN_COL, 12.94);	values.put(TOTAL_COL, 387);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Mixed Nuts 100g");	values.put(CARBS_COL, 21);	values.put(FAT_COL, 20);	values.put(PROTEIN_COL, 5);	values.put(TOTAL_COL, 190);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Wow Mani 30g");	values.put(CARBS_COL, 11);	values.put(FAT_COL, 13);	values.put(PROTEIN_COL, 5);	values.put(TOTAL_COL, 181);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Arabica Coffee");	values.put(CARBS_COL, 15.115349);	values.put(FAT_COL, 1.5136);	values.put(PROTEIN_COL, 1.5136);	values.put(TOTAL_COL, 86.0681);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Yakult");	values.put(CARBS_COL, 15);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 1);	values.put(TOTAL_COL, 50);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Siopao 1pc");	values.put(CARBS_COL, 36);	values.put(FAT_COL, 8);	values.put(PROTEIN_COL, 10);	values.put(TOTAL_COL, 260);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Mi Goreng 1 pack");	values.put(CARBS_COL, 53);	values.put(FAT_COL, 16);	values.put(PROTEIN_COL, 8);	values.put(TOTAL_COL, 390);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "All Purpose Flour 30g");	values.put(CARBS_COL, 23);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 4);	values.put(TOTAL_COL, 108);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Eden Cheese 30g");	values.put(CARBS_COL, 4);	values.put(FAT_COL, 7);	values.put(PROTEIN_COL, 3);	values.put(TOTAL_COL, 90);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Gardenia 1 slice");	values.put(CARBS_COL, 16.5);	values.put(FAT_COL, 0.5);	values.put(PROTEIN_COL, 2.5);	values.put(TOTAL_COL, 76.5);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Bacon 100g");	values.put(CARBS_COL, 1.43);	values.put(FAT_COL, 41.78);	values.put(PROTEIN_COL, 37.04);	values.put(TOTAL_COL, 541);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Toblerone Dark 33g");	values.put(CARBS_COL, 20);	values.put(FAT_COL, 10);	values.put(PROTEIN_COL, 2);	values.put(TOTAL_COL, 180);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Jollibee Chicken Breast");	values.put(CARBS_COL, 6);	values.put(FAT_COL, 17);	values.put(PROTEIN_COL, 36);	values.put(TOTAL_COL, 320);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "JaBee Choco Sundae");	values.put(CARBS_COL, 47);	values.put(FAT_COL, 9);	values.put(PROTEIN_COL, 3);	values.put(TOTAL_COL, 290);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "JaBee Steamed Rice 184g");	values.put(CARBS_COL, 41);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 3);	values.put(TOTAL_COL, 180);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Jabee Gravy Regular 77g");	values.put(CARBS_COL, 5);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 0);	values.put(TOTAL_COL, 25);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Gardenia Whole Wheat 1 slice");	values.put(CARBS_COL, 13);	values.put(FAT_COL, 1);	values.put(PROTEIN_COL, 3.5);	values.put(TOTAL_COL, 75);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Magnolia Fresh Milk 250ml/250g");	values.put(CARBS_COL, 12);	values.put(FAT_COL, 9);	values.put(PROTEIN_COL, 9);	values.put(TOTAL_COL, 170);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Dari Crème 15g");	values.put(CARBS_COL, 0.5);	values.put(FAT_COL, 12);	values.put(PROTEIN_COL, 0);	values.put(TOTAL_COL, 110);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Ginisang Bagoong 50g");	values.put(CARBS_COL, 9.99);	values.put(FAT_COL, 9.99);	values.put(PROTEIN_COL, 6.66);	values.put(TOTAL_COL, 149.85);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Mayonnaise 15g");	values.put(CARBS_COL, 4);	values.put(FAT_COL, 3);	values.put(PROTEIN_COL, 0);	values.put(TOTAL_COL, 45);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Buffet");	values.put(CARBS_COL, 0);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 0);	values.put(TOTAL_COL, 2992.8856);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Brown Sugar 100g");	values.put(CARBS_COL, 0);	values.put(FAT_COL, 97.33);	values.put(PROTEIN_COL, 0);	values.put(TOTAL_COL, 377);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Coke Zero");	values.put(CARBS_COL, 0);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 0);	values.put(TOTAL_COL, 0);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "UFC Gravy 1 Pack");	values.put(CARBS_COL, 4);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 1);	values.put(TOTAL_COL, 20);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "KFC Zinger");	values.put(CARBS_COL, 39);	values.put(FAT_COL, 24);	values.put(PROTEIN_COL, 27);	values.put(TOTAL_COL, 480);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Jabee Chicken Thigh");	values.put(CARBS_COL, 15);	values.put(FAT_COL, 21);	values.put(PROTEIN_COL, 15);	values.put(TOTAL_COL, 380);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Cornstarch 100g");	values.put(CARBS_COL, 91);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 0);	values.put(TOTAL_COL, 381);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Alaska Chicken Evap 60ml");	values.put(CARBS_COL, 5);	values.put(FAT_COL, 3);	values.put(PROTEIN_COL, 4);	values.put(TOTAL_COL, 64);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Cabbage 100g");	values.put(CARBS_COL, 5.5);	values.put(FAT_COL, 0.1);	values.put(PROTEIN_COL, 1.3);	values.put(TOTAL_COL, 23);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Pullman Loaf 1 Slice 28g");	values.put(CARBS_COL, 14);	values.put(FAT_COL, 0.5);	values.put(PROTEIN_COL, 9);	values.put(TOTAL_COL, 70);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Unripe Mango 100g");	values.put(CARBS_COL, 10.6);	values.put(FAT_COL, 0.2);	values.put(PROTEIN_COL, 0.9);	values.put(TOTAL_COL, 48);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Gardenia Whole Wheat Thick 1 Slice");	values.put(CARBS_COL, 15);	values.put(FAT_COL, 1);	values.put(PROTEIN_COL, 4);	values.put(TOTAL_COL, 85);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Baking Soda");	values.put(CARBS_COL, 0);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 0);	values.put(TOTAL_COL, 0);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Beefies Cheesedog 1 pc");	values.put(CARBS_COL, 3);	values.put(FAT_COL, 4);	values.put(PROTEIN_COL, 3);	values.put(TOTAL_COL, 56);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Korean Soft Tofu 85g");	values.put(CARBS_COL, 3);	values.put(FAT_COL, 5);	values.put(PROTEIN_COL, 8);	values.put(TOTAL_COL, 93);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Green Hot Chili Peppers 100g");	values.put(CARBS_COL, 9.46);	values.put(FAT_COL, 0.2);	values.put(PROTEIN_COL, 2);	values.put(TOTAL_COL, 40);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Red Hot Chili Peppers 100g");	values.put(CARBS_COL, 5.1);	values.put(FAT_COL, 0.1);	values.put(PROTEIN_COL, 0.9);	values.put(TOTAL_COL, 21);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Cake");	values.put(CARBS_COL, 0);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 0);	values.put(TOTAL_COL, 0);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Uncooked White Rice 100g");	values.put(CARBS_COL, 80.42);	values.put(FAT_COL, 0.54);	values.put(PROTEIN_COL, 6.65);	values.put(TOTAL_COL, 364);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Ginger 100g");	values.put(CARBS_COL, 17.77);	values.put(FAT_COL, 0.75);	values.put(PROTEIN_COL, 1.82);	values.put(TOTAL_COL, 80);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Tomato Catsup Packet");	values.put(CARBS_COL, 3);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 0);	values.put(TOTAL_COL, 10);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Banana Catsup UFC 1tbsp");	values.put(CARBS_COL, 2);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 0);	values.put(TOTAL_COL, 9);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Grapes 100g");	values.put(CARBS_COL, 18);	values.put(FAT_COL, 0.2);	values.put(PROTEIN_COL, 0.7);	values.put(TOTAL_COL, 69);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Dilly Bar");	values.put(CARBS_COL, 23);	values.put(FAT_COL, 15);	values.put(PROTEIN_COL, 4);	values.put(TOTAL_COL, 240);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Dry Peas 1 Can");	values.put(CARBS_COL, 18);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 6);	values.put(TOTAL_COL, 100);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Mama Sitas Oyster Sauce");	values.put(CARBS_COL, 5);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 1);	values.put(TOTAL_COL, 25);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Turmeric Ginger Tea 1tbsp");	values.put(CARBS_COL, 8);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 0);	values.put(TOTAL_COL, 32);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Boiled Egg Yolk");	values.put(CARBS_COL, 0.6);	values.put(FAT_COL, 4.5);	values.put(PROTEIN_COL, 2.7);	values.put(TOTAL_COL, 55);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Selecta Double Dutch 100ml (60g)");	values.put(CARBS_COL, 17);	values.put(FAT_COL, 6);	values.put(PROTEIN_COL, 2);	values.put(TOTAL_COL, 120);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Toblerone Milk Chocolate 25g");	values.put(CARBS_COL, 15.2);	values.put(FAT_COL, 7.2);	values.put(PROTEIN_COL, 1.4);	values.put(TOTAL_COL, 132);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Mug Root Beer 200ml");	values.put(CARBS_COL, 10);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 0);	values.put(TOTAL_COL, 36);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Magnolia Cheeze 30g");	values.put(CARBS_COL, 5);	values.put(FAT_COL, 6);	values.put(PROTEIN_COL, 3);	values.put(TOTAL_COL, 80);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Pineapple 100g");	values.put(CARBS_COL, 13);	values.put(FAT_COL, 0.1);	values.put(PROTEIN_COL, 0.5);	values.put(TOTAL_COL, 50);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Calamansi Juice 1tbsp");	values.put(CARBS_COL, 1);	values.put(FAT_COL, 1);	values.put(PROTEIN_COL, 0);	values.put(TOTAL_COL, 4);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Chicken Breast w/ Skin 100g");	values.put(CARBS_COL, 0);	values.put(FAT_COL, 7.38);	values.put(PROTEIN_COL, 29.52);	values.put(TOTAL_COL, 180.4);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Peanut Sauce 2tbsp");	values.put(CARBS_COL, 3.5);	values.put(FAT_COL, 6);	values.put(PROTEIN_COL, 2);	values.put(TOTAL_COL, 60);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Mang Inasal - Pecho Large");	values.put(CARBS_COL, 10);	values.put(FAT_COL, 2);	values.put(PROTEIN_COL, 70);	values.put(TOTAL_COL, 378);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Pork Loin 100g");	values.put(CARBS_COL, 8.8);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 26);	values.put(TOTAL_COL, 192);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Chicken Gizzard 100g");	values.put(CARBS_COL, 3);	values.put(FAT_COL, 0);	values.put(PROTEIN_COL, 30);	values.put(TOTAL_COL, 154);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Mang Inasal - Halo-Halo");	values.put(CARBS_COL, 0);	values.put(FAT_COL, 40);	values.put(PROTEIN_COL, 5);	values.put(TOTAL_COL, 180);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Magnum Cookies and Cream");	values.put(CARBS_COL, 14);	values.put(FAT_COL, 23);	values.put(PROTEIN_COL, 3);	values.put(TOTAL_COL, 230);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Selecta Quezo Real 100ml");	values.put(CARBS_COL, 6);	values.put(FAT_COL, 12);	values.put(PROTEIN_COL, 2);	values.put(TOTAL_COL, 120);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Alaska Milk 200ml");	values.put(CARBS_COL, 7);	values.put(FAT_COL, 10);	values.put(PROTEIN_COL, 7);	values.put(TOTAL_COL, 133);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Melon 100g");	values.put(CARBS_COL, 0.19);	values.put(FAT_COL, 8.16);	values.put(PROTEIN_COL, 0.84);	values.put(TOTAL_COL, 34);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Okra 100g");	values.put(CARBS_COL, 0.2);	values.put(FAT_COL, 7.5);	values.put(PROTEIN_COL, 1.9);	values.put(TOTAL_COL, 33);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Honeydew Melon 100g");	values.put(CARBS_COL, 0.1);	values.put(FAT_COL, 9.1);	values.put(PROTEIN_COL, 0.5);	values.put(TOTAL_COL, 36);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Magnolia Panckage 20g");	values.put(CARBS_COL, 5.48);	values.put(FAT_COL, 15.56);	values.put(PROTEIN_COL, 2.13);	values.put(TOTAL_COL, 118.2);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);
        values.put(FOOD_NAME, "Red Ribbon Butter Mamon");	values.put(CARBS_COL, 9);	values.put(FAT_COL, 16);	values.put(PROTEIN_COL, 4);	values.put(TOTAL_COL, 150);	values.put(REMARKS_COL, "_");	db.insert(TABLE_NAME, null, values);






//    void addExcelValues(String foodName, float carbsGrams, float fatGrams, float proteinGrams, float totalCal, String remarks){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//
//        String sql = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Chicken Thigh 100g","0","10.9","26","209")";
//        String sql1 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Chicken Breast (100g)","0","3.6","31","165")";
//        String sql2 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Pork Shoulder cut (100g)","0","7.14","19.55","148")";
//        String sql3 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Porkchop (100g)","0","11","26","209")";
//        String sql4 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Bangus (100g)","0","6.73","20.53","148")";
//        String sql5 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Chicken Drumstick No Skin 100g","0","5.7","28.3","172")";
//        String sql6 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Chicken Drumstick w/ skin 100g","0","14.62","37.1","280")";
//        String sql7 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Rice (100g)","27.86","0.46","2.86","129")";
//        String sql8 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Carrots (100g)","9.6","0.2","0.9","41")";
//        String sql9 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Tomato (100g)","3.9","0.2","0.9","18")";
//        String sql10 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Cucumber (100g)","3.6","0.1","0.7","15")";
//        String sql11 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Sitaw (100g)","7","0","2","31")";
//        String sql12 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Avocado (100g)","8.5","15","2","160")";
//        String sql13 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Apple (192g)","29","0.3","0.4","121")";
//        String sql14 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Ripe Mango (100g)","15","0.4","0.8","60")";
//        String sql15 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Lakatan (100g)","22.8","0.3","1.1","89")";
//        String sql16 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Kangkong (100g)","3.14","0.2","2.6","25")";
//        String sql17 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Ampalaya (100g)","4","0","1","17")";
//        String sql18 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Corn (100g)","21","1.5","3.4","96")";
//        String sql19 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Green Peas (100g)","16","0.2","5.4","84")";
//        String sql20 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Red Onion (100g)","10.11","0.08","0.92","42")";
//        String sql21 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Bell Pepper 100g","6.7","0.2","0.9","28")";
//        String sql22 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Curry Powder 3 tsp","3.3","0.8","0.9","19.5")";
//        String sql23 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Pepper 1tsp","1.36","0.07","0.23","5")";
//        String sql24 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Garlic 100g","33","0.5","6.4","149")";
//        String sql25 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Mixed Vegetables 227g","21","1","7","113")";
//        String sql26 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Watermelon 100g","7.6","0.2","0.6","30")";
//        String sql27 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Potato 100g","15.4","0.1","2.2","75")";
//        String sql28 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Tomato Sauce 60g","5","0","1.25","25")";
//        String sql29 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("White Onion 100g","10","0.2","1.4","44")";
//        String sql30 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Eggplant 100g","8.7","0.2","0.8","35")";
//        String sql31 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Squash 100g","11.69","0.1","1","45")";
//        String sql32 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Sayote 100g","4.5","0.1","0.8","19")";
//        String sql33 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Papaya 100g","11","0.3","0.5","43")";
//        String sql34 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Guava 100g","14","1","2.6","68")";
//        String sql35 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Large egg (50g)","0.6","5.3","6.3","77")";
//        String sql36 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Jumbo TJ Hotdog","4","10","8","224")";
//        String sql37 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Hash Brown (100g)","34.9","12.44","2.98","263")";
//        String sql38 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Olive Oil 1tbsp","0","14","0","124")";
//        String sql39 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Quaker Oats 40g","26","3","5","153")";
//        String sql40 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Pancake Magnolia w/ egg 40g","31","1.5","3","149")";
//        String sql41 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("CDO cheesy chicken franks 55g","1","7","7","90")";
//        String sql42 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Oil 1 tbsp (13.6g)","0","14","0","120")";
//        String sql43 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("CDO chicken franks 55g","1","8","8","110")";
//        String sql44 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Magnolia Gold Salted Butter 15g","0","12","0","110")";
//        String sql45 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Garlic Powder 1tsp","2.3","0","0.5","10")";
//        String sql46 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Paprika 1tsp","1.24","0.296","0.325","6.49")";
//        String sql47 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Chili Powder 1tsp","1.3","0.4","0.4","7.5")";
//        String sql48 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Bagoong 30g","1.33672","0","1.85504","12.8216")";
//        String sql49= "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Purefoods Ham 1 Slice 32g","3","1","6","41")";
//        String sql50 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("555 tuna spicy caldereta (56g)","7","1","5","60")";
//        String sql51 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Chinese Luncheon Meat (68g)","9","7","6","121")";
//        String sql52 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Libbys Vienna Sausage (130g)","2","19.5","10","221")";
//        String sql53 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Chunky Corned Tuna (56g)","3","2.5","8","70")";
//        String sql54 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Popcorn (100g)","77.78","4.54","12.94","387")";
//        String sql55 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Mixed Nuts 100g","21","20","5","190")";
//        String sql56 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Wow Mani 30g","11","13","5","181")";
//        String sql57 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Arabica Coffee","15.115349","1.5136","1.5136","86.0681")";
//        String sql58 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Yakult","15","0","1","50")";
//        String sql59 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Siopao 1pc","36","8","10","260")";
//        String sql60 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Mi Goreng 1 pack","53","16","8","390")";
//        String sql61 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("All Purpose Flour 30g","23","0","4","108")";
//        String sql62 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Eden Cheese 30g","4","7","3","90")";
//        String sql63 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Gardenia 1 slice","16.5","0.5","2.5","76.5")";
//        String sql64 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Bacon 100g","1.43","41.78","37.04","541")";
//        String sql65 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Toblerone Dark 33g","20","10","2","180")";
//        String sql66 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Jollibee Chicken Breast","6","17","36","320")";
//        String sql67 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("JaBee Choco Sundae","47","9","3","290")";
//        String sql68 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("JaBee Steamed Rice 184g","41","0","3","180")";
//        String sql69 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Jabee Gravy Regular 77g","5","0","0","25")";
//        String sql70 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Gardenia Whole Wheat 1 slice","13","1","3.5","75")";
//        String sql71 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Magnolia Fresh Milk 250ml/250g","12","9","9","170")";
//        String sql72 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Dari Crème 15g","0.5","12","0","110")";
//        String sql73 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Ginisang Bagoong 50g","9.99","9.99","6.66","149.85")";
//        String sql74 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Mayonnaise 15g","4","3","0","45")";
//        String sql75 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Buffet","0","0","0","2992.8856")";
//        String sql76 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Brown Sugar 100g","0","97.33","0","377")";
//        String sql78 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Coke Zero","0","0","0","0")";
//        String sql79 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("UFC Gravy 1 Pack","4","0","1","20")";
//        String sql80 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("KFC Zinger","39","24","27","480")";
//        String sql81 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Jabee Chicken Thigh","15","21","15","380")";
//        String sql82 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Cornstarch 100g","91","0","0","381")";
//        String sql83 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Alaska Chicken Evap 60ml","5","3","4","64")";
//        String sql84 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Cabbage 100g","5.5","0.1","1.3","23")";
//        String sql85 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Pullman Loaf 1 Slice 28g","14","0.5","9","70")";
//        String sql86 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Unripe Mango 100g","10.6","0.2","0.9","48")";
//        String sql87 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Gardenia Whole Wheat Thick 1 Slice","15","1","4","85")";
//        String sql88 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Baking Soda","0","0","0","0")";
//        String sql89 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Beefies Cheesedog 1 pc","3","4","3","56")";
//        String sql90 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Korean Soft Tofu 85g","3","5","8","93")";
//        String sql91 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Green Hot Chili Peppers 100g","9.46","0.2","2","40")";
//        String sql92 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Red Hot Chili Peppers 100g","5.1","0.1","0.9","21")";
//        String sql93 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Cake","0","0","0","0")";
//        String sql94 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Uncooked White Rice 100g","80.42","0.54","6.65","364")";
//        String sql95 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Ginger 100g","17.77","0.75","1.82","80")";
//        String sql96 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Tomato Catsup Packet","3","0","0","10")";
//        String sql97 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Banana Catsup UFC 1tbsp","2","0","0","9")";
//        String sql98 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Grapes 100g","18","0.2","0.7","69")";
//        String sql99 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Dilly Bar","23","15","4","240")";
//        String sql100 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Dry Peas 1 Can","18","0","6","100")";
//        String sql101 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Mama Sitas Oyster Sauce","5","0","1","25")";
//        String sql102 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Turmeric Ginger Tea 1tbsp","8","0","0","32")";
//        String sql103 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Boiled Egg Yolk","0.6","4.5","2.7","55")";
//        String sql104 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Selecta Double Dutch 100ml (60g)","17","6","2","120")";
//        String sql105 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Toblerone Milk Chocolate 25g","15.2","7.2","1.4","132")";
//        String sql106 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Mug Root Beer 200ml","10","0","0","36")";
//        String sql107 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Magnolia Cheeze 30g","5","6","3","80")";
//        String sql108 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Pineapple 100g","13","0.1","0.5","50")";
//        String sql109 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Calamansi Juice 1tbsp","1","1","0","4")";
//        String sql110 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Chicken Breast w/ Skin 100g","0","7.38","29.52","180.4")";
//        String sql111 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Peanut Sauce 2tbsp","3.5","6","2","60")";
//        String sql112 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Mang Inasal - Pecho Large","10","2","70","378")";
//        String sql113 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Pork Loin 100g","8.8","0","26","192")";
//        String sql114 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Chicken Gizzard 100g","3","0","30","154")";
//        String sql115 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Mang Inasal - Halo-Halo","0","40","5","180")";
//        String sql116 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Magnum Cookies and Cream","14","23","3","230")";
//        String sql117 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Selecta Quezo Real 100ml","6","12","2","120")";
//        String sql118 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Alaska Milk 200ml","7","10","7","133")";
//        String sql119 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Melon 100g","0.19","8.16","0.84","34")";
//        String sql120 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Okra 100g","0.2","7.5","1.9","33")";
//        String sql121 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Honeydew Melon 100g","0.1","9.1","0.5","36")";
//        String sql122 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Magnolia Panckage 20g","5.48","15.56","2.13","118.2")";
//        String sql123 = "INSERT INTO "+TABLE_NAME+"(foodName, carbsGrams, fatGrams,proteinGrams, totalCal) VALUES ("Red Ribbon Butter Mamon","9","16","4","150")";
//
//
//        db.execSQL(sql);
//        db.execSQL(sql1);
//        db.execSQL(sql2);
//        db.execSQL(sql3);
//        db.execSQL(sql4);
//        db.execSQL(sql5);
//        db.execSQL(sql6);
//        db.execSQL(sql7);
//        db.execSQL(sql8);
//        db.execSQL(sql9);
//        db.execSQL(sql10);
//        db.execSQL(sql11);
//        db.execSQL(sql12);
//        db.execSQL(sql13);
//        db.execSQL(sql14);
//        db.execSQL(sql15);
//        db.execSQL(sql16);
//        db.execSQL(sql17);
//        db.execSQL(sql18);
//        db.execSQL(sql19);
//        db.execSQL(sql20);
//        db.execSQL(sql21);
//        db.execSQL(sql22);
//        db.execSQL(sql23);
//        db.execSQL(sql24);
//        db.execSQL(sql25);
//        db.execSQL(sql26);
//        db.execSQL(sql27);
//        db.execSQL(sql28);
//        db.execSQL(sql29);
//        db.execSQL(sql30);
//        db.execSQL(sql31);
//        db.execSQL(sql32);
//        db.execSQL(sql33);
//        db.execSQL(sql34);
//        db.execSQL(sql35);
//        db.execSQL(sql36);
//        db.execSQL(sql37);
//        db.execSQL(sql38);
//        db.execSQL(sql39);
//        db.execSQL(sql40);
//        db.execSQL(sql41);
//        db.execSQL(sql42);
//        db.execSQL(sql43);
//        db.execSQL(sql44);
//        db.execSQL(sql45);
//        db.execSQL(sql46);
//        db.execSQL(sql47);
//        db.execSQL(sql48);
//        db.execSQL(sql49);
//        db.execSQL(sql50);
//        db.execSQL(sql51);
//        db.execSQL(sql52);
//        db.execSQL(sql53);
//        db.execSQL(sql54);
//        db.execSQL(sql55);
//        db.execSQL(sql56);
//        db.execSQL(sql57);
//        db.execSQL(sql58);
//        db.execSQL(sql59);
//        db.execSQL(sql60);
//        db.execSQL(sql61);
//        db.execSQL(sql62);
//        db.execSQL(sql63);
//        db.execSQL(sql64);
//        db.execSQL(sql65);
//        db.execSQL(sql66);
//        db.execSQL(sql67);
//        db.execSQL(sql68);
//        db.execSQL(sql69);
//        db.execSQL(sql70);
//        db.execSQL(sql71);
//        db.execSQL(sql72);
//        db.execSQL(sql73);
//        db.execSQL(sql74);
//        db.execSQL(sql75);
//        db.execSQL(sql76);
//        db.execSQL(sql78);
//        db.execSQL(sql79);
//        db.execSQL(sql80);
//        db.execSQL(sql81);
//        db.execSQL(sql82);
//        db.execSQL(sql83);
//        db.execSQL(sql84);
//        db.execSQL(sql85);
//        db.execSQL(sql86);
//        db.execSQL(sql87);
//        db.execSQL(sql88);
//        db.execSQL(sql89);
//        db.execSQL(sql90);
//        db.execSQL(sql91);
//        db.execSQL(sql92);
//        db.execSQL(sql93);
//        db.execSQL(sql94);
//        db.execSQL(sql95);
//        db.execSQL(sql96);
//        db.execSQL(sql97);
//        db.execSQL(sql98);
//        db.execSQL(sql99);
//        db.execSQL(sql100);
//        db.execSQL(sql101);
//        db.execSQL(sql102);
//        db.execSQL(sql103);
//        db.execSQL(sql104);
//        db.execSQL(sql105);
//        db.execSQL(sql106);
//        db.execSQL(sql107);
//        db.execSQL(sql108);
//        db.execSQL(sql109);
//        db.execSQL(sql110);
//        db.execSQL(sql111);
//        db.execSQL(sql112);
//        db.execSQL(sql113);
//        db.execSQL(sql114);
//        db.execSQL(sql115);
//        db.execSQL(sql116);
//        db.execSQL(sql117);
//        db.execSQL(sql118);
//        db.execSQL(sql119);
//        db.execSQL(sql120);
//        db.execSQL(sql121);
//        db.execSQL(sql122);
//        db.execSQL(sql123);
//
    }
}
