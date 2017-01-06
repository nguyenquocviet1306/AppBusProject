package com.example.admin.appbus1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.models.FoodRealmObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFoodFragment extends Fragment {

    @BindView(R.id.name_food)
    TextView nameFood;
    @BindView(R.id.address_food)
    TextView addressFood;
    @BindView(R.id.time_food)
    TextView timeFood;
    @BindView(R.id.price_food)
    TextView priceFood;

    private FoodRealmObject foodRealmObject;


    public InfoFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_info_food, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        setupUI();
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void receiveInfo(com.example.admin.appbus1.managers.EvenFood event){
        this.foodRealmObject = event.getFood();
    }

    private void setupUI() {
        nameFood.setText(foodRealmObject.getName());
        addressFood.setText(foodRealmObject.getAddress());
        timeFood.setText(foodRealmObject.getTime());
        priceFood.setText(foodRealmObject.getPrice());
    }


}
