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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.adapters.BusAdapter;
import com.example.admin.appbus1.managers.Constant;
import com.example.admin.appbus1.managers.EventDataReady;
import com.example.admin.appbus1.managers.RealmHandler;
import com.example.admin.appbus1.managers.Utils;
import com.example.admin.appbus1.models.Bus;
import com.example.admin.appbus1.services.ApiUrl;
import com.example.admin.appbus1.services.BusAPI;
import com.example.admin.appbus1.services.ServiceFactory;

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
public class ListBusFragment extends Fragment implements View.OnClickListener{

    private GridLayoutManager layoutManager;
    private BusAdapter busAdapter;
    private ServiceFactory serviceFactory;
    private Bus bus;
    @BindView(R.id.rv_listbus)
    RecyclerView rv_listbus;


    public ListBusFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_bus, container, false);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this, view);
        setupUI(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void setAdapterView(EventDataReady event){
        busAdapter = new BusAdapter();
        busAdapter.setOnItemClickListener(this);
        rv_listbus.setAdapter(busAdapter);
        busAdapter.notifyDataSetChanged();
    }

    private void loadData() {
        if(!Constant.isLoadedBus){
            serviceFactory = new ServiceFactory(ApiUrl.BASE_URL);
            BusAPI service = serviceFactory.createService(BusAPI.class);
            Call<BusAPI.Bus> call = service.callBus();
            call.enqueue(new Callback<BusAPI.Bus>() {
                @Override
                public void onResponse(Call<BusAPI.Bus> call, Response<BusAPI.Bus> response) {
                    RealmHandler.getInstance().clearBusInRealm();
                    List<BusAPI.BusList> list = response.body().getBusList();

                    for (int i = 0; i < list.size(); i++){
                        Bus bus = new Bus();
                        bus.setId(list.get(i).getId());
                        bus.setWay(list.get(i).getWay());
                        bus.setTime(list.get(i).getTime());
                        bus.setFrequency(list.get(i).getFrequency());
                        bus.setPrice(list.get(i).getPrice());
                        bus.setGo(list.get(i).getGo());
                        bus.setBack(list.get(i).getBack());
//                        List<UniversityAPI.Number> number = list.get(i).getBus();
//                        RealmList<StringRealmObject> numberList = new RealmList<>();
//                        for (int j = 0; j < number.size(); j ++){
//                            StringRealmObject stringRealmObject = new StringRealmObject(number.get(j).getNumber());
//                            numberList.add(stringRealmObject);
//                        }
//                        university.setBus(numberList);
                        RealmHandler.getInstance().addBusToRealm(bus);
                    }
                    EventBus.getDefault().post(new EventDataReady());
                    Utils.setLoadData(getActivity(), Constant.keyLoadedBus, true);
                }

                @Override
                public void onFailure(Call<BusAPI.Bus> call, Throwable t) {
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
        rv_listbus.setLayoutManager(layoutManager);

        loadData();
    }

    @Override
    public void onClick(View v) {
        bus = (Bus) v.getTag();
        EventBus.getDefault().postSticky( new com.example.admin.appbus1.managers.EventBus(bus));
        changeFragment(new InfoBusFragment(), true, null);

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
