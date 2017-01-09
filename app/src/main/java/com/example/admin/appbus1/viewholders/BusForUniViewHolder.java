package com.example.admin.appbus1.viewholders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.models.StringRealmObject;

/**
 * Created by giaqu on 1/9/2017.
 */

public class BusForUniViewHolder extends RecyclerView.ViewHolder{
//    @BindView(R.id.tv_id_bus)
    TextView tv_id_bus;

//    private University university;


    public BusForUniViewHolder(View itemView) {
        super(itemView);
//        ButterKnife.bind(this, itemView);
        tv_id_bus = (TextView) itemView.findViewById(R.id.tv_id_bus);
    }

    public void setDataBus(StringRealmObject bus){
//        Picasso.with(itemView.getContext())
//                .load(breath.getImage())
//                .fit()
//                .centerCrop()
//                .into(imageView);
//        String IDsave = university.getId();
//        org.greenrobot.eventbus.EventBus.getDefault().postSticky(IDsave);
//        EventBus.getDefault().postSticky(university.getId());
//        tv_id_bus.setText(RealmHandler.getInstance().getNumberList(university));
        if (bus != null) {
            Log.d("1234", bus + "");
            tv_id_bus.setText(bus.getString());
            itemView.setTag(bus);
        }
    }
}
