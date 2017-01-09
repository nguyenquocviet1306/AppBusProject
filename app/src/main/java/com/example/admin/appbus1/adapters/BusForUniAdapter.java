package com.example.admin.appbus1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.models.StringRealmObject;
import com.example.admin.appbus1.viewholders.BusForUniViewHolder;

import io.realm.RealmList;

/**
 * Created by giaqu on 1/9/2017.
 */

public class BusForUniAdapter extends RecyclerView.Adapter<BusForUniViewHolder>  {
    RealmList<StringRealmObject> busList = new RealmList<>();
    private View.OnClickListener onItemClickListener;

    public BusForUniAdapter(RealmList<StringRealmObject> listBus) {
        this.busList = listBus;
    }


    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {

        return position % 2;
    }

    @Override
    public BusForUniViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView =  layoutInflater.inflate(R.layout.item_bus_for_uni, parent, false);
        return new BusForUniViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BusForUniViewHolder holder, int position) {
        holder.itemView.setOnClickListener(onItemClickListener);

        holder.setDataBus(busList.get(position));
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

//    public void reloadData(List<Bus> buses) {
//        this.busList = buses;
//        this.notifyDataSetChanged();
//    }
}
