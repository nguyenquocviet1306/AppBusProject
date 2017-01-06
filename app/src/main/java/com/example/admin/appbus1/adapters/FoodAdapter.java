package com.example.admin.appbus1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.managers.RealmHandler;
import com.example.admin.appbus1.models.Food;
import com.example.admin.appbus1.viewholders.FoodViewHolder;

import java.util.List;

/**
 * Created by giaqu on 1/6/2017.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder> {
    List<Food> foodList = RealmHandler.getInstance().getFoodFromRealm();
    private View.OnClickListener onItemClickListener;

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {

        return position % 2;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView =  layoutInflater.inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        holder.itemView.setOnClickListener(onItemClickListener);
        holder.setDataFood(foodList.get(position));
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
}
