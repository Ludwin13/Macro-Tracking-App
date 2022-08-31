package com.example.fooddbv2;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class FoodList   {
    private int foodID;
    private String foodName;
    private float carbsGrams;
    private float fatGrams;
    private float proteinGrams;
    private float totalCal;
    private String remarks;



    public int getFoodID() { return foodID; }
    public void setFoodID(int foodID) { this.foodID = foodID; }

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public float getCarbsGrams() { return carbsGrams; }
    public void setCarbsGrams(float carbsGrams) { this.carbsGrams = carbsGrams; }

    public float getFatGrams() { return fatGrams; }
    public void setFatGrams(float fatGrams ) { this.fatGrams = fatGrams; }

    public float getProteinGrams() { return proteinGrams; }
    public void setProteinGrams(float proteinGrams) { this.proteinGrams = proteinGrams; }

    public float getTotalCal() { return totalCal; }
    public void setTotalCal(float totalCal ) { this.totalCal = totalCal; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }





    public FoodList(int foodID, String foodName, float carbsGrams, float fatGrams, float proteinGrams, float totalCal, String remarks){

        this.foodID = foodID;
        this.foodName = foodName;
        this.carbsGrams = carbsGrams;
        this.fatGrams = fatGrams;
        this.proteinGrams = proteinGrams;
        this.totalCal = totalCal;
        this.remarks = remarks;
    }





}
