package com.example.admin.appbus1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.appbus1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFoodFragment extends Fragment implements FragmentWithSearch {


    public InfoFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_food, container, false);
    }

    @Override
    public void doSearch(String searchString) {

    }

    @Override
    public void closeSearch() {

    }
}
