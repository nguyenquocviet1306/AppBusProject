package com.example.admin.appbus1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.managers.Constant;
import com.example.admin.appbus1.managers.EvenBusForUni;
import com.example.admin.appbus1.managers.RealmHandler;
import com.example.admin.appbus1.managers.event.EventDataReady;
import com.example.admin.appbus1.models.Bus;
import com.example.admin.appbus1.models.StringRealmObject;
import com.example.admin.appbus1.models.University;
import com.example.admin.appbus1.services.ServiceFactory;
import com.example.admin.appbus1.services.api.ApiUrl;
import com.example.admin.appbus1.services.api.BusAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowBusFragment extends Fragment implements FragmentWithSearch{

    private static final String TAG = ShowBusFragment.class.toString() ;
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
    @BindView(R.id.progressBus)
    ProgressBar progessbarBus;
    RealmHandler realmHandler;
    @BindView(R.id.sv)
    LinearLayout sv;
    private ServiceFactory serviceFactory;
    private StringRealmObject detail;
//    private Bus bus = new Bus();
    private String number;
    private RealmList<StringRealmObject> list = new RealmList<>();
    private University university;
    Bus bus = new Bus();

    public ShowBusFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_bus, container, false);
        EventBus.getDefault().register(this);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        realmHandler = RealmHandler.getInstance();


        return view;
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
    @Subscribe(sticky = true)
    public void receiveInfo(EvenBusForUni event) {
        this.detail = event.getStringRealmObject();
        Log.d(TAG, "Info la " + String.valueOf(detail));
        String id = detail.getString();
        final int i = Integer.parseInt(id) - 1;
        Log.d(TAG, id);
        if (!Constant.isLoadedBus) {
            //progessbarBus.setVisibility(View.VISIBLE);
            serviceFactory = new ServiceFactory(ApiUrl.BASE_URL);
            BusAPI service = serviceFactory.createService(BusAPI.class);
            Call<BusAPI.Bus> call = service.callBus();
            call.enqueue(new Callback<BusAPI.Bus>() {
                @Override
                public void onResponse(Call<BusAPI.Bus> call, Response<BusAPI.Bus> response) {
                    RealmHandler.getInstance().clearBusInRealm();
                    List<BusAPI.BusList> list = response.body().getBusList();
                    Log.d(TAG, "onResponse");

                    bus = new Bus();
                    bus.setId(list.get(i).getId());
                    bus.setWay(list.get(i).getWay());
                    bus.setTime(list.get(i).getTime());
                    bus.setFrequency(list.get(i).getFrequency());
                    bus.setPrice(list.get(i).getPrice());
                    bus.setGo(list.get(i).getGo());
                    bus.setBack(list.get(i).getBack());
                    bus.setWayWithoutUnicode(bus.getWayWithoutUnicode());

//                        List<UniversityAPI.Number> number = list.get(i).getBus();
//                        RealmList<StringRealmObject> numberList = new RealmList<>();
//                        for (int j = 0; j < number.size(); j ++){
//                            StringRealmObject stringRealmObject = new StringRealmObject(number.get(j).getNumber());
//                            numberList.add(stringRealmObject);
//                        }
//                        university.setBus(numberList);
                    RealmHandler.getInstance().addBusToRealm(bus);
                    Log.d(TAG, bus.getId());
                    tv_id_show.setText(bus.getId());
                    tv_way_show.setText(bus.getWay());
                    tv_time_show.setText(bus.getTime());
                    tv_frequency_show.setText(bus.getFrequency());
                    tv_price_show.setText(bus.getPrice());
                    tv_go_show.setText(bus.getGo());
                    tv_back_show.setText(bus.getBack());


                    EventBus.getDefault().post(new EventDataReady());
                    progessbarBus.setVisibility(View.INVISIBLE);
                    sv.setVisibility(View.VISIBLE);

//                    Utils.setLoadData(getActivity(), Constant.keyLoadedBus, true);

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
    public void changeFragment(Fragment fragment, boolean addToBackstack, String tag){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container, fragment);
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.commit();
    }





    @Override
    public void doSearch(String searchString) {

    }

    @Override
    public void closeSearch() {

    }
}
