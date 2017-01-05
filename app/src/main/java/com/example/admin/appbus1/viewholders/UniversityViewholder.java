package com.example.admin.appbus1.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.models.University;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by giaqu on 12/14/2016.
 */

public class UniversityViewholder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_university)
    TextView tv_university;

    public UniversityViewholder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setDataUniversity(University university){
//        Picasso.with(itemView.getContext())
//                .load(breath.getImage())
//                .fit()
//                .centerCrop()
//                .into(imageView);

        tv_university.setText(university.getName());
        itemView.setTag(university);

    }


}
