package com.example.admin.appbus1.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.adapters.UniversityAdapter;
import com.example.admin.appbus1.managers.Constant;
import com.example.admin.appbus1.managers.EventDataReady;
import com.example.admin.appbus1.managers.EventUniversity;
import com.example.admin.appbus1.managers.RealmHandler;
import com.example.admin.appbus1.managers.Utils;
import com.example.admin.appbus1.models.University;
import com.example.admin.appbus1.services.ApiUrl;
import com.example.admin.appbus1.services.ServiceFactory;
import com.example.admin.appbus1.services.UniversityAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListUniFragment extends Fragment implements View.OnClickListener {


    private GridLayoutManager layoutManager;
    private UniversityAdapter universityAdapter;
    private ServiceFactory serviceFactory;
    private University university;
    @BindView(R.id.rv_university)
    RecyclerView rv_university;

    public ListUniFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_uni, container, false);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this, view);
        setupUI(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void setAdapterView(EventDataReady event){
        universityAdapter = new UniversityAdapter();
        universityAdapter.setOnItemClickListener(this);
        rv_university.setAdapter(universityAdapter);
        universityAdapter.notifyDataSetChanged();
    }

    private void loadData() {
        if(!Constant.isLoadedUniversity){
            serviceFactory = new ServiceFactory(ApiUrl.BASE_URL);
            UniversityAPI service = serviceFactory.createService(UniversityAPI.class);
            Call<UniversityAPI.University> call = service.callUniversity();
            call.enqueue(new Callback<UniversityAPI.University>() {
                @Override
                public void onResponse(Call<UniversityAPI.University> call, Response<UniversityAPI.University> response) {
                    RealmHandler.getInstance().clearUniversityInRealm();
                    List<UniversityAPI.UniversityList> list = response.body().getUniversityList();

                    for (int i = 0; i < list.size(); i++){
                        University university = new University();
                        university.setId(list.get(i).getId());
                        university.setName(list.get(i).getName());
                        university.setAbbreviation(list.get(i).getAbbreviation());
                        university.setAddress(list.get(i).getAddress());
                        university.setBus(list.get(i).getBus());

                        RealmHandler.getInstance().addUniversityToRealm(university);
                    }
                    EventBus.getDefault().post(new EventDataReady());
                    Utils.setLoadData(getActivity(), Constant.keyLoadedUniversity, true);
                }

                @Override
                public void onFailure(Call<UniversityAPI.University> call, Throwable t) {
                    Log.d("Failure", t.toString());
                }
            });
        } else {
            EventBus.getDefault().post(new EventDataReady());
        }
    }

    private void setupUI(View view) {
        layoutManager = new GridLayoutManager(
                view.getContext(), 1, LinearLayoutManager.VERTICAL, false);
        rv_university.setLayoutManager(layoutManager);

        loadData();
    }

    @Override
    public void onClick(View v) {
        university = (University) v.getTag();
        EventBus.getDefault().postSticky( new EventUniversity(university));
        changeFragment(new InfoUniFragment(), true, null);
    }

    public void changeFragment(Fragment fragment, boolean addToBackstack, String tag){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container, fragment);
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.commit();
    }
}
