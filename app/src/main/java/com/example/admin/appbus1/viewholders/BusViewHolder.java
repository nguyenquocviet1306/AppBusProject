package com.example.admin.appbus1.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.models.Bus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by giaqu on 1/5/2017.
 */

public class BusViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_bus_id)
    TextView tv_bus_id;
    @BindView(R.id.tv_bus)
    TextView tv_bus;

    public BusViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setDataBus(Bus bus){
//        Picasso.with(itemView.getContext())
//                .load(breath.getImage())
//                .fit()
//                .centerCrop()
//                .into(imageView);
        tv_bus_id.setText(bus.getId());
        tv_bus.setText(bus.getWay());
        itemView.setTag(bus);

    }
}
