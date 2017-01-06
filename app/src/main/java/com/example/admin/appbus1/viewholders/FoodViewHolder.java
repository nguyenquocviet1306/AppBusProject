package com.example.admin.appbus1.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.models.Food;
import com.example.admin.appbus1.models.FoodRealmObject;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by giaqu on 1/6/2017.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.iv_food)
    ImageView ivFood;
    @BindView(R.id.tv_food)
    TextView tvFood;
    Context context;

    public FoodViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setDataFood(FoodRealmObject food){
//        Picasso.with(itemView.getContext())
//                .load(breath.getImage())
//                .fit()
//                .centerCrop()
//                .into(imageView);

        tvFood.setText(food.getName());
        Picasso.with(itemView.getContext()).load(food.getImage()).fit().centerCrop().into(ivFood);


        itemView.setTag(food);

    }
}
