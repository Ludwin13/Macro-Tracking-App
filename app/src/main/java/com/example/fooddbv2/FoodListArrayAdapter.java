package com.example.fooddbv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FoodListArrayAdapter extends ArrayAdapter<FoodList>{

    private List<FoodList> foodListAll;


    public FoodListArrayAdapter(@NonNull Context context, @NonNull List<FoodList> foodList) {
        super(context, 0, foodList);
        foodListAll = new ArrayList<>(foodList);

    }

    @NonNull
    @Override
    public Filter getFilter() {
        return foodFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        try {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.food_autocomplete_row, parent, false
                );
            }

            TextView name = convertView.findViewById(R.id.foodName);
            FoodList foodItem = getItem(position);

            if (foodItem != null) {
                name.setText(foodItem.getFoodName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
    private Filter foodFilter = new Filter() {
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();
        List<FoodList> foodListSuggestion = new ArrayList<>();
        if (constraint == null || constraint.length() == 0) {
            foodListSuggestion.addAll(foodListAll);
        } else {
            String filterPattern = constraint.toString().toLowerCase().trim();

            for (FoodList item : foodListAll) {
                if (item.getFoodName().toLowerCase().contains(filterPattern)) {
                    foodListSuggestion.add(item);
                }
            }

        }
        filterResults.values = foodListSuggestion;
        filterResults.count = foodListSuggestion.size();

        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults filterResults) {
        clear();
        addAll((List) filterResults.values);
        notifyDataSetChanged();

        }


    @Override
    public CharSequence convertResultToString(Object resultValue) {
        return ((FoodList) resultValue).getFoodName();
    }
        };
    }

