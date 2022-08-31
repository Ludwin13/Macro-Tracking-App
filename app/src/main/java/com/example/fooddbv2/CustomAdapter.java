package com.example.fooddbv2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>  implements Filterable{

    private Context context;
    private Activity activity;
//    private ArrayList foodID, foodName, carbsGrams, fatGrams, proteinGrams, totalCal, remarks;
    private List<FoodList> foodList;
    private List<FoodList> foodListArrayList;

    CustomAdapter(@NonNull Activity activity,
                  @NonNull Context context,
//            ArrayList foodID,
//            ArrayList foodName,
//            ArrayList carbsGrams,
//            ArrayList fatGrams,
//            ArrayList proteinGrams,
//            ArrayList totalCal,
//            ArrayList remarks
                @NonNull ArrayList<FoodList> foodList) {


            this.activity = activity;
            this.context = context;
//          this.foodID = foodID;
//          this.foodName = foodName;
//          this.carbsGrams = carbsGrams;
//          this.fatGrams = fatGrams;
//          this.proteinGrams = proteinGrams;
//          this.totalCal = totalCal;
//          this.remarks = remarks;
            this.foodList = foodList;
            this.foodListArrayList = new ArrayList<>(foodList);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.my_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    try {

//        holder.foodIDText.setText(String.valueOf(foodID.get(position)));
//        holder.foodNameText.setText(String.valueOf(foodName.get(position)));
//        holder.carbsGramsText.setText(String.valueOf(carbsGrams.get(position)));
//        holder.fatGramsText.setText(String.valueOf(fatGrams.get(position)));
//        holder.proteinGramsText.setText(String.valueOf(proteinGrams.get(position)));
//        holder.totalCalText.setText(String.valueOf(totalCal.get(position)));
//        holder.remarksText.setText(String.valueOf(remarks.get(position)));

        FoodList food = foodList.get(position);
        holder.foodNameText.setText(String.valueOf(food.getFoodName()));
        holder.carbsGramsText.setText(String.valueOf(food.getCarbsGrams()));
        holder.fatGramsText.setText(String.valueOf(food.getFatGrams()));
        holder.proteinGramsText.setText(String.valueOf(food.getProteinGrams()));
        holder.totalCalText.setText(String.valueOf(food.getTotalCal()));
        holder.remarksText.setText(String.valueOf(food.getRemarks()));


        holder.mainLayout.setOnClickListener(view -> {

            Intent intent = new Intent(context, UpdateActivity.class);
//            intent.putExtra("id", String.valueOf(foodID(position)));
//            intent.putExtra("name", String.valueOf(foodName.get(position)));
//            intent.putExtra("carbs", String.valueOf(carbsGrams.get(position)));
//            intent.putExtra("fat", String.valueOf(fatGrams.get(position)));
//            intent.putExtra("protein", String.valueOf(proteinGrams.get(position)));
//            intent.putExtra("cal", String.valueOf(totalCal.get(position)));
//            intent.putExtra("remarks", String.valueOf(remarks.get(position)));

            intent.putExtra("id", String.valueOf(food.getFoodID()));
            intent.putExtra("name", String.valueOf(food.getFoodName()));
            intent.putExtra("carbs", String.valueOf(food.getCarbsGrams()));
            intent.putExtra("fat", String.valueOf(food.getFatGrams()));
            intent.putExtra("protein", String.valueOf(food.getProteinGrams()));
            intent.putExtra("cal", String.valueOf(food.getTotalCal()));
            intent.putExtra("remarks", String.valueOf(food.getRemarks()));

            activity.startActivityForResult(intent, 1);
        });
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }


    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<FoodList> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(foodListArrayList);
            } else {
                for (FoodList food : foodListArrayList) {
                    if (food.getFoodName().toLowerCase().contains(charSequence.toString().toLowerCase())
                                || food.getRemarks().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            filteredList.add(food);
                    }
                }

            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }



        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            foodList.clear();
            foodList.addAll((Collection<? extends FoodList>) filterResults.values);
            notifyDataSetChanged();



        }
    };


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView foodIDText, foodNameText, carbsGramsText, fatGramsText, proteinGramsText, totalCalText, remarksText;
        LinearLayout mainLayout;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodNameText = itemView.findViewById(R.id.foodNameText);
            carbsGramsText = itemView.findViewById(R.id.carbsGramsText);
            fatGramsText = itemView.findViewById(R.id.fatGramsText);
            proteinGramsText = itemView.findViewById(R.id.proteinGramsText);
            totalCalText = itemView.findViewById(R.id.totalCalText);
            remarksText = itemView.findViewById(R.id.remarksText);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate RecyclerView
//            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
//            mainLayout.setAnimation(translate_anim);

        }
    }
}
