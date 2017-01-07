package com.example.admin.appbus1.viewholders;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.appbus1.R;
import com.example.admin.appbus1.models.University;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by giaqu on 12/14/2016.
 */

public class UniversityViewholder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_uni)
    ImageView iv_uni;
    @BindView(R.id.tv_university)
    TextView tv_university;
    Context context;

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
        Glide.with(context).load(Uri.parse("file:///android_asset/logo/" + university.getLogo())).centerCrop().into(iv_uni);
        tv_university.setText(university.getName());
        itemView.setTag(university);

    }


}
