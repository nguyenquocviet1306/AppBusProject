package com.example.admin.appbus1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
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
public class InfoBusFragment extends Fragment implements FragmentWithSearch{

    @BindView(R.id.tv_id)
    TextView tv_id;
    @BindView(R.id.tv_way)
    TextView tv_way;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_frequency)
    TextView tv_frequency;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_go)
    TextView tv_go;
    @BindView(R.id.tv_back)
    TextView tv_back;

    private Bus bus;

    public InfoBusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_bus, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        getActivity().setTitle( bus.getId() +" : " + bus.getWay());
        setHasOptionsMenu(true);
        setupUI();
        return view;
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void receiveInfo(com.example.admin.appbus1.managers.event.EventBus event){
        this.bus = event.getBus();
    }

    private void setupUI() {
        tv_id.setText(bus.getId());
        tv_way.setText(bus.getWay());
        tv_time.setText(bus.getTime());
        tv_frequency.setText(bus.getFrequency());
        tv_price.setText(bus.getPrice());
        tv_go.setText(bus.getGo());
        tv_back.setText(bus.getBack());
    }

    @Override
    public void doSearch(String searchString) {

    }

    @Override
    public void closeSearch() {

    }
}
