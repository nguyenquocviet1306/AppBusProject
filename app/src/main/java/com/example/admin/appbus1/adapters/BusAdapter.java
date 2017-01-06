package com.example.admin.appbus1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.managers.RealmHandler;
import com.example.admin.appbus1.models.Bus;
import com.example.admin.appbus1.viewholders.BusViewHolder;

import java.util.List;

/**
 * Created by giaqu on 1/4/2017.
 */

public class BusAdapter extends RecyclerView.Adapter<BusViewHolder>  {
    List<Bus> busList = RealmHandler.getInstance().getBusFromRealm();
    private View.OnClickListener onItemClickListener;

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {

        return position % 2;
    }

    @Override
    public BusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView =  layoutInflater.inflate(R.layout.item_bus, parent, false);
        return new BusViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BusViewHolder holder, int position) {
        holder.itemView.setOnClickListener(onItemClickListener);
        holder.setDataBus(busList.get(position));
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    public void reloadData(List<Bus> buses) {
        this.busList = buses;
        this.notifyDataSetChanged();
    }
}
