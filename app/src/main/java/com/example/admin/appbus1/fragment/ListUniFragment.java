package com.example.admin.appbus1.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.adapters.UniversityAdapter;
import com.example.admin.appbus1.managers.Constant;
import com.example.admin.appbus1.managers.RealmHandler;
import com.example.admin.appbus1.managers.Utils;
import com.example.admin.appbus1.managers.event.EventDataReady;
import com.example.admin.appbus1.managers.event.EventUniversity;
import com.example.admin.appbus1.models.Bus;
import com.example.admin.appbus1.models.StringRealmObject;
import com.example.admin.appbus1.models.University;
import com.example.admin.appbus1.services.ServiceFactory;
import com.example.admin.appbus1.services.api.ApiUrl;
import com.example.admin.appbus1.services.api.BusAPI;
import com.example.admin.appbus1.services.api.UniversityAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.Normalizer;
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
public class ListUniFragment extends Fragment implements View.OnClickListener, FragmentWithSearch {

    private SearchView searchView;
    private RealmHandler realmHandler;
    private List<University> universities = RealmHandler.getInstance().getUniversityFromRealm();
    private static final String TAG = ListUniFragment.class.toString();
    private GridLayoutManager layoutManager;
    private UniversityAdapter universityAdapter;
    private ServiceFactory serviceFactory;
    private University university;
    @BindView(R.id.rv_university)
    RecyclerView rv_university;
    @BindView(R.id.progress)
    ProgressBar progressBar;
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
        realmHandler = RealmHandler.getInstance();
//        setHasOptionsMenu(true);
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
        universityAdapter = new UniversityAdapter(universities);
        universityAdapter.setOnItemClickListener(this);
        rv_university.setAdapter(universityAdapter);
        universityAdapter.notifyDataSetChanged();



    }

    private void loadData() {
        if(!Constant.isLoadedUniversity){
            progressBar.setVisibility(View.VISIBLE);
            serviceFactory = new ServiceFactory(ApiUrl.BASE_URL);
            UniversityAPI service = serviceFactory.createService(UniversityAPI.class);
            Call<UniversityAPI.University> call = service.callUniversity();
            call.enqueue(new Callback<UniversityAPI.University>() {
                @Override
                public void onResponse(Call<UniversityAPI.University> call, Response<UniversityAPI.University> response) {
                    RealmHandler.getInstance().clearUniversityInRealm();
                    List<UniversityAPI.UniversityList> list = response.body().getUniversityList();
                    Log.d(TAG,list.size() +"");
                    for (int i = 0; i < list.size(); i++){
                        University university = new University();
                        university.setId(list.get(i).getId());
                        university.setName(list.get(i).getName());
                        university.setAbbreviation(list.get(i).getAbbreviation());
                        university.setLogo(list.get(i).getLogo());
                        university.setAddress(list.get(i).getAddress());
                        university.setNameWithoutUnicode(university.getNameWithoutUnicode());
                        List<UniversityAPI.Number> number = list.get(i).getBus();
                        Log.d(TAG,number.toString());

                        RealmList<StringRealmObject> numberList = new RealmList<>();
                        for (int j = 0; j < number.size(); j ++){
                            StringRealmObject stringRealmObject = new StringRealmObject(number.get(j).getNumber());
                            numberList.add(stringRealmObject);
                        }
                        university.setBus(numberList);

                        RealmHandler.getInstance().addUniversityToRealm(university);
                    }
                    EventBus.getDefault().post(new EventDataReady());
                    Utils.setLoadData(getActivity(), Constant.keyLoadedUniversity, true);
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<UniversityAPI.University> call, Throwable t) {
                    Log.d("Failure", t.toString());
                    EventBus.getDefault().post("loadDataFromDB");
                }
            });
        } else {
            EventBus.getDefault().post(new EventDataReady());
        }
    }
    @Subscribe
    public void loadDataFromDB(String string){
        if(string.equals("loadDataFromDB")){
            universities = RealmHandler.getInstance().getUniversityFromRealm();
            universityAdapter.notifyDataSetChanged();
        }
    }

    private void loadDataBus() {
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
                    progressBar.setVisibility(View.INVISIBLE);
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
                view.getContext(), 2, GridLayoutManager.VERTICAL, false);
        rv_university.setLayoutManager(layoutManager);

        loadData();
        loadDataBus();
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

//    @Override
//    public void setHasOptionsMenu(boolean hasMenu) {
//        super.setHasOptionsMenu(hasMenu);
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.search, menu);


    }

    @Override
    public void doSearch(String searchString) {
        String stringWithoutUnicode = Normalizer.normalize(searchString, Normalizer.Form.NFD)
                .replace("Đ", "D")
                .replace("đ", "d")
                .replaceAll("[^\\p{ASCII}]", "");

        List<University> universityList = realmHandler.findUniversityByName(stringWithoutUnicode);
        if (this.universityAdapter != null) {
            this.universityAdapter.reloadData(universityList);
        }
        

    }

    @Override
    public void closeSearch() {
        universityAdapter.reloadData(universities);
    }

}
