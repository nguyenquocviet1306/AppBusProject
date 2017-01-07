package com.example.admin.appbus1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.managers.RealmHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowBusFragment extends Fragment implements FragmentWithSearch{

    @BindView(R.id.tv_detail)
    TextView tv_detail;

    private String detail;

    public ShowBusFragment() {
        // Required empty public constructor
    }

    @Subscribe(sticky = true)
    public void receiveInfo(String event){
        this.detail = event;
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
        tv_detail.setText(detail);

    }

    @Override
    public void doSearch(String searchString) {

    }

    @Override
    public void closeSearch() {

    }
}
