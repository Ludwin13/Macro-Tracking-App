package com.example.fooddbv2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomAdapterDI extends RecyclerView.Adapter<CustomAdapterDI.MyViewHolder> implements Filterable {

    private Context context;
    private Activity activity;
//    private ArrayList intakeID, intake_date, foodID_DI, multiplier, intake_type, remarks_DI;
    private List<FoodListDI> foodListDI;
    private List<FoodListDI> foodListDIArrayList;
//    Animation translate_anim;

    CustomAdapterDI(@NonNull Activity activity,
                    @NonNull Context context,
//                    ArrayList intakeID,
//                  ArrayList intake_date,
//                  ArrayList foodID_DI,
//                  ArrayList multiplier,
//                  ArrayList intake_type,
                  @NonNull ArrayList <FoodListDI> foodListDI)
    {
        this.activity = activity;
        this.context = context;
//        this.intakeID = intakeID;
//        this.intake_date = intake_date;
//        this.foodID_DI = foodID_DI;
//        this.multiplier = multiplier;
//        this.intake_type = intake_type;
//        this.remarks_DI = remarks_DI;
        this.foodListDI = foodListDI;
        this.foodListDIArrayList = new ArrayList<>(foodListDI);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_di, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        FoodListDI foodDI = foodListDI.get(position);
        holder.intakeIDText.setText(String.valueOf(foodDI.getIntakeID()));
        holder.intakeDateText.setText(String.valueOf(foodDI.getIntake_Date()));
        holder.foodID_DIText.setText(String.valueOf(foodDI.getFoodID_DI()));
        holder.multiplierText.setText(String.valueOf(foodDI.getMultiplier()));
        holder.intakeTypeText.setText(String.valueOf(foodDI.getIntake_Type()));
        holder.remarksText.setText(String.valueOf(foodDI.getRemarks()));

//        holder.intakeIDText.setText(String.valueOf(intakeID.get(position)));
//        holder.intakeDateText.setText(String.valueOf(intake_date.get(position)));
//        holder.foodID_DIText.setText(String.valueOf(foodID_DI.get(position)));
//        holder.multiplierText.setText(String.valueOf(multiplier.get(position)));
//        holder.intakeTypeText.setText(String.valueOf(intake_type.get(position)));
//        holder.remarksText.setText(String.valueOf(remarks_DI.get(position)));

        holder.mainLayout_DI.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateActivityDI.class);
//            intent.putExtra("id", String.valueOf(intakeID.get(position)));
//            intent.putExtra("date", String.valueOf(intake_date.get(position)));
//            intent.putExtra("foodID", String.valueOf(foodID_DI.get(position)));
//            intent.putExtra("multi", String.valueOf(multiplier.get(position)));
//            intent.putExtra("type", String.valueOf(intake_type.get(position)));
//            intent.putExtra("remarks", String.valueOf(remarks_DI.get(position)));

            intent.putExtra("id", String.valueOf(foodDI.getIntakeID()));
            intent.putExtra("date", String.valueOf(foodDI.getIntake_Date()));
            intent.putExtra("foodID", String.valueOf(foodDI.getFoodID_DI()));
            intent.putExtra("multi", String.valueOf(foodDI.getMultiplier()));
            intent.putExtra("type", String.valueOf(foodDI.getIntake_Type()));
            intent.putExtra("remarks", String.valueOf(foodDI.getRemarks()));


            activity.startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return foodListDI.size();
    }

    public Filter getFilter() { return filter; }

    Filter filter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<FoodListDI> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(foodListDIArrayList);
            } else {
                for (FoodListDI foodDI : foodListDIArrayList) {
                    if (foodDI.getIntake_Date().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || foodDI.getIntake_Type().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || foodDI.getRemarks().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(foodDI);
                    }
                }

            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            foodListDI.clear();
            foodListDI.addAll((Collection<? extends FoodListDI>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView intakeIDText, intakeDateText, foodID_DIText, multiplierText, intakeTypeText, remarksText;
        LinearLayout mainLayout_DI;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            intakeIDText = itemView.findViewById(R.id.intakeIDText);
            intakeDateText = itemView.findViewById(R.id.intakeDateText);
            foodID_DIText = itemView.findViewById(R.id.foodID_DIText);
            multiplierText = itemView.findViewById(R.id.multiplierText);
            intakeTypeText = itemView.findViewById(R.id.intakeTypeText);
            remarksText = itemView.findViewById(R.id.remarksText);
            mainLayout_DI = itemView.findViewById(R.id.mainLayout_DI);
            //Animate RecyclerView
//            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
//            mainLayout_DI.setAnimation(translate_anim);


        }
    }


}
