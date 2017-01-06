package com.example.admin.appbus1.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.models.Food;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by giaqu on 1/6/2017.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.iv_food)
    ImageView iv_food;
    @BindView(R.id.tv_food)
    TextView tv_food;

    public FoodViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setDataFood(Food food){
//        Picasso.with(itemView.getContext())
//                .load(breath.getImage())
//                .fit()
//                .centerCrop()
//                .into(imageView);

//        tv_food.setText(food.getName());
        itemView.setTag(food);

    }
}
