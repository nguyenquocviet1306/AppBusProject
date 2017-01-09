package com.example.admin.appbus1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.managers.RealmHandler;
import com.example.admin.appbus1.models.Bus;
import com.example.admin.appbus1.models.StringRealmObject;
import com.example.admin.appbus1.models.University;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

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

    RealmHandler realmHandler;
    private StringRealmObject detail;
//    private Bus bus = new Bus();
    private String number;
    private RealmList<StringRealmObject> list = new RealmList<>();
    private University university;
    Bus bus = new Bus();

    public ShowBusFragment() {
        // Required empty public constructor
    }

    @Subscribe(sticky = true)
    public void receiveInfo(StringRealmObject event){
        this.detail = event;
        Log.d("ahihi", detail + "");

//        bus.setId(detail.getString());
//        bus.setWay(detail.getString());
//        bus.setTime(detail.getString());
//        bus.setFrequency(detail.getString());
//        bus.setPrice(detail.getString());
//        bus.setGo(detail.getString());
//        bus.setBack(detail.getString());
//        Log.d("ahuhu", realmHandler.getDetailBus(detail.getString()) +"");
        bus = realmHandler.getDetailBus(detail.getString());

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_bus, container, false);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this, view);
        realmHandler = RealmHandler.getInstance();
        setupUI();

        return view;
    }

    private void setupUI() {
//        EventBus.getDefault().postSticky(detail);
//
//        changeFragment(new InfoBusFragment(), true, null);
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
