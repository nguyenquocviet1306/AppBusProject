package com.example.admin.appbus1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.appbus1.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFoodFragment extends Fragment implements FragmentWithSearch{

    @BindView(R.id.rv_food)
    RecyclerView rv_food;

    public ListFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_food, container, false);
        ButterKnife.bind(this, view);
//        EventBus.getDefault().register(this);
        setupUI();
        return view;
    }

    private void setupUI() {
    }

    @Override
    public void doSearch(String searchString) {

    }

    @Override
    public void closeSearch() {

    }
}
