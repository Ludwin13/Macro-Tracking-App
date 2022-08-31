package com.example.fooddbv2;

public class FoodListDI {
    private int intakeID;
    private String intake_date;
    private int foodID_DI;
    private float multiplier;
    private String intake_type;
    private String remarks;

    public int getIntakeID() { return intakeID; }

    public String getIntake_Date() { return intake_date; }

    public int getFoodID_DI() { return foodID_DI; }

    public float getMultiplier() { return multiplier; }

    public String getIntake_Type() { return intake_type; }

    public String getRemarks() { return remarks; }

    public FoodListDI(int intakeID, String intake_date, int foodID_DI, float multiplier, String intake_type, String remarks) {
        this.intakeID = intakeID;
        this.intake_date = intake_date;
        this.foodID_DI = foodID_DI;
        this.multiplier = multiplier;
        this.intake_type = intake_type;
        this.remarks = remarks;
    }




}
