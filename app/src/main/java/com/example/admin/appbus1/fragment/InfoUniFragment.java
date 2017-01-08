package com.example.admin.appbus1.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.appbus1.R;
import com.example.admin.appbus1.adapters.BusAdapter;
import com.example.admin.appbus1.managers.RealmHandler;
import com.example.admin.appbus1.managers.event.EventDataReady;
import com.example.admin.appbus1.managers.event.EventUniversity;
import com.example.admin.appbus1.models.Bus;
import com.example.admin.appbus1.models.StringRealmObject;
import com.example.admin.appbus1.models.University;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoUniFragment extends Fragment implements FragmentWithSearch{
    private BusAdapter busAdapter;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.iv_university)
    ImageView iv_university;
    @BindView(R.id.lv_bus)
    ListView lv_bus;
    @BindView(R.id.btn_food)
    Button btn_food;

    private RealmHandler realmHandler;
    private University university;
    private StringRealmObject bus;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> busList =  new ArrayList<>();

    public InfoUniFragment() {
        // Required empty public constructor
    }

    @Subscribe(sticky = true)
    public void setAdapterView(EventDataReady event){


        busAdapter = new BusAdapter();
        //busAdapter.setOnItemClickListener(this);
//        rv_listbus.setAdapter(busAdapter);
        busAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_info_uni, container, false);
        ButterKnife.bind(this, view);
        realmHandler = RealmHandler.getInstance();
        EventBus.getDefault().register(this);
        setHasOptionsMenu(true);
        setupUI();
        addListener();
        return view;
    }
<<<<<<< HEAD

=======
>>>>>>> ed87581454cb954c1a6660953be302e30f7a1b4b
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
<<<<<<< HEAD


=======
>>>>>>> ed87581454cb954c1a6660953be302e30f7a1b4b
    private void addListener() {
        String IDsave = university.getId();
        org.greenrobot.eventbus.EventBus.getDefault().postSticky(IDsave);
        EventBus.getDefault().postSticky(university.getId());
        busList = RealmHandler.getInstance().getNumberList(university);

        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_textview, busList);
        lv_bus.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        lv_bus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                busList.get(position);
                Bus detail = realmHandler.getDetailBus(busList.get(position));

                org.greenrobot.eventbus.EventBus.getDefault().postSticky(detail);

                changeFragment(new ShowBusFragment(), true);
            }
        });
        btn_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new ListFoodForUniFragment(), true);
            }
        });
    }

    @Subscribe(sticky = true)
    public void receiveInfo(EventUniversity event){

        this.university = event.getUniversity();

    }

//    @Subscribe(sticky = true)
//    public void receiveInfo(EventBus event){
//
//        this. = event.ge();
//
//    }

    private void setupUI() {
        tv_name.setText(university.getName());
        //tv_abbreviation.setText(university.getAbbreviation());
        tv_address.setText(university.getAddress());
        //tv_bus.setText(bus.getNumber());
        Glide.with(this).load(Uri.parse("file:///android_asset/logo/" + university.getLogo())).centerCrop().into(iv_university);

    }
    
    
    
    private void changeFragment(Fragment fragment, boolean addToBackStack){
        FragmentTransaction fragmentTransaction =
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_container,fragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
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
