package com.example.admin.appbus1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.managers.RealmHandler;
import com.example.admin.appbus1.models.University;
import com.example.admin.appbus1.viewholders.UniversityViewholder;

import java.util.List;



/**
 * Created by giaqu on 1/2/2017.
 */

public class UniversityAdapter extends RecyclerView.Adapter<UniversityViewholder> {
    List<University> universityList = RealmHandler.getInstance().getUniversityFromRealm();
    private View.OnClickListener onItemClickListener;

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public UniversityViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView =  layoutInflater.inflate(R.layout.item_university, parent, false);
        return new UniversityViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(UniversityViewholder holder, int position) {
        holder.itemView.setOnClickListener(onItemClickListener);
        holder.setDataUniversity(universityList.get(position));
    }

    @Override
    public int getItemCount() {
        return universityList.size();
    }
}
