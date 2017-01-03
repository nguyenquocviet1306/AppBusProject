package com.example.admin.appbus1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.managers.EventUniversity;
import com.example.admin.appbus1.models.University;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoUniFragment extends Fragment {
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_address)
    TextView tv_address;

    private University university;


    public InfoUniFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_info_uni, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        setupUI();
        return view;
    }
    @Subscribe(sticky = true)
    public void receiveInfo(EventUniversity event){
        this.university = event.getUniversity();
    }

    private void setupUI() {
        tv_name.setText(university.getName());
        //tv_abbreviation.setText(university.getAbbreviation());
        tv_address.setText(university.getAddress());
        //tv_bus.setText(university.getBus());
    }

}
