package com.example.admin.appbus1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.models.Bus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowBusFragment extends Fragment implements FragmentWithSearch{

    @BindView(R.id.tv_id_show)
    TextView tv_id_show;
    @BindView(R.id.tv_way_show)
    TextView tv_way_show;
    @BindView(R.id.tv_time_show)
    TextView tv_time_show;
    @BindView(R.id.tv_frequency_show)
    TextView tv_frequency_show;
    @BindView(R.id.tv_price_show)
    TextView tv_price_show;
    @BindView(R.id.tv_go_show)
    TextView tv_go_show;
    @BindView(R.id.tv_back_show)
    TextView tv_back_show;

    private String detail;
    private Bus bus;

    public ShowBusFragment() {
        // Required empty public constructor
    }

    @Subscribe(sticky = true)
    public void receiveInfo(Bus event){
        this.bus = event;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_bus, container, false);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this, view);
        setupUI();
        return view;
    }

    private void setupUI() {
        tv_id_show.setText(bus.getId());
        tv_way_show.setText(bus.getWay());
        tv_time_show.setText(bus.getTime());
        tv_frequency_show.setText(bus.getFrequency());
        tv_price_show.setText(bus.getPrice());
        tv_go_show.setText(bus.getGo());
        tv_back_show.setText(bus.getBack());


    }

    @Override
    public void doSearch(String searchString) {

    }

    @Override
    public void closeSearch() {

    }
}
