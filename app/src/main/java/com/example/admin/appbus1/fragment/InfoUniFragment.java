package com.example.admin.appbus1.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.appbus1.R;
import com.example.admin.appbus1.adapters.BusForUniAdapter;
import com.example.admin.appbus1.adapters.FoodForUniAdapter;
import com.example.admin.appbus1.managers.Constant;
import com.example.admin.appbus1.managers.RealmHandler;
import com.example.admin.appbus1.managers.event.EventDataReady;
import com.example.admin.appbus1.managers.event.EventUniversity;
import com.example.admin.appbus1.models.Food;
import com.example.admin.appbus1.models.FoodRealmObject;
import com.example.admin.appbus1.models.StringRealmObject;
import com.example.admin.appbus1.models.University;
import com.example.admin.appbus1.services.ServiceFactory;
import com.example.admin.appbus1.services.api.ApiUrl;
import com.example.admin.appbus1.services.api.FoodApi;

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
public class InfoUniFragment extends Fragment implements FragmentWithSearch, View.OnClickListener {
    private static final String TAG = InfoFoodFragment.class.toString();
    private BusForUniAdapter busAdapter;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.iv_university)
    ImageView iv_university;
    @BindView(R.id.rv_bus_for_uni)
    RecyclerView rv_bus_for_uni;
    @BindView(R.id.progressfood)
    ProgressBar progressBar;

//    @BindView(R.id.lv_bus)
//    ListView lv_bus;
//    @BindView(R.id.btn_food)
//    Button btn_food;
    private GridLayoutManager layoutManager;
    private StringRealmObject bus;
    private ArrayAdapter<String> arrayAdapter;
    private RealmList<StringRealmObject> busList =  new RealmList<>();
    private RealmHandler realmHandler;
    private GridLayoutManager layoutManager1;
    private FoodForUniAdapter foodForUniAdapter;
    private ServiceFactory serviceFactory;
    private FoodRealmObject food;
    public University university;
    String IDsave;
    @BindView(R.id.rv_uni_food1)
    RecyclerView rv_uni_food1;


    public InfoUniFragment() {
        // Required empty public constructor
    }

    @Subscribe(sticky = true)
    public void setAdapterView(EventDataReady event){
        Log.d("1234", "setting adapter...");
        busAdapter = new BusForUniAdapter(university.getBus());
        rv_bus_for_uni.setAdapter(busAdapter);
        busAdapter.setOnItemClickListener(this);


//        rv_listbus.setAdapter(busAdapter);
        busAdapter.notifyDataSetChanged();

        foodForUniAdapter = new FoodForUniAdapter();
        foodForUniAdapter.setOnItemClickListener(this);
        rv_uni_food1.setAdapter(foodForUniAdapter);
        foodForUniAdapter.notifyDataSetChanged();

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
        getActivity().setTitle(university.getAbbreviation());
        setHasOptionsMenu(true);
        setupUI(view);
        addListener();
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }


    private void addListener() {

//        busList = RealmHandler.getInstance().getNumberList(university);

//        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_textview, busList);
//        lv_bus.setAdapter(arrayAdapter);
//        arrayAdapter.notifyDataSetChanged();
//        lv_bus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                busList.get(position);
//                Bus detail = realmHandler.getDetailBus(busList.get(position));
//
//                org.greenrobot.eventbus.EventBus.getDefault().postSticky(detail);
//
//                changeFragment(new ShowBusFragment(), true);
//            }
//        });

        setup();

//        busAdapter.notifyDataSetChanged();
//        btn_food.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeFragment(new ListFoodForUniFragment(), true);
//            }
//        });
    }
    private void loadData() {

        RealmHandler.getInstance().clearFoodInRealm();
        if(!Constant.isLoadedFood){
            progressBar.setVisibility(View.VISIBLE);

            //List<University> universityList = RealmHandler.getInstance().getUniversityFromRealm();
            //Log.d(TAG, String.valueOf(universityList));
//            String ID = universityList.equals()
            //Log.d(TAG,IDsave);

            //String id = EventBus.getDefault().register(university.getId());
            final int iD = Integer.parseInt(university.getId()) - 1;
            // String aidi = String.valueOf(university.equals(getId()));
            //Log.d(TAG,aidi);
            serviceFactory = new ServiceFactory(ApiUrl.BASE_URL);
            FoodApi service = serviceFactory.createService(FoodApi.class);
            Call<FoodApi.Food> call = service.callFood();
            call.enqueue(new Callback<FoodApi.Food>() {
                @Override
                public void onResponse(Call<FoodApi.Food> call, Response<FoodApi.Food> response) {
                    Log.d(TAG, "onResponse");

                    List<FoodApi.FoodList> list = response.body().getFoodList();
                    //int iD = (int)university.getId();

                    Log.d(TAG,list.size() +"");
                    //for (int i = 1; i <  1; i++){
                    Food food = new Food();
                    food.setId(list.get(iD).getId());
                    List<FoodApi.Foody> foodyLists = list.get(iD).getFoody();
                    Log.d(TAG,foodyLists + "");

                    RealmList<FoodRealmObject> foodList = new RealmList<>();
                    for (int j = 0; j < foodyLists.size(); j ++){
                        FoodRealmObject foodRealmObject = new FoodRealmObject(foodyLists.get(j).getName(),foodyLists.get(j).getAddress(),
                                foodyLists.get(j).getImage(),foodyLists.get(j).getTime(),foodyLists.get(j).getPrice());
                        foodList.add(foodRealmObject);
                        Log.d(TAG,foodyLists.get(j).getName());
                    }
                    food.setFoody(foodList);


                    RealmHandler.getInstance().addFoodToRealm(food);
                    //}
                    EventBus.getDefault().post(new EventDataReady());
//                    Utils.setLoadData(getActivity(), Constant.keyLoadedFood, true);
                    progressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<FoodApi.Food> call, Throwable t) {
                    Log.d("No No ", t.toString());
                }
            });
        } else {
            EventBus.getDefault().post(new EventDataReady());
        }
    }


    private void setupUIFood(View view) {
        layoutManager1 = new GridLayoutManager(
                view.getContext(), 2, GridLayoutManager.VERTICAL, false);
        rv_uni_food1.setLayoutManager(layoutManager1);

        loadData();
    }

    @Subscribe(sticky = true)
    public void receiveInfo(EventUniversity event){

        this.university = event.getUniversity();
//        foodForUniAdapter = new FoodForUniAdapter();
//        foodForUniAdapter.setOnItemClickListener(this);
//        rv_uni_food.setAdapter(foodForUniAdapter);
//        foodForUniAdapter.notifyDataSetChanged();
    }

//    @Subscribe(sticky = true)
//    public void receiveInfo(EventBus event){
//
//        this.bus = event;
//
//    }

    private void setupUI(View view) {
        tv_name.setText(university.getName());
        //tv_abbreviation.setText(university.getAbbreviation());
        tv_address.setText(university.getAddress());
        //tv_bus.setText(bus.getNumber());
        Glide.with(this).load(Uri.parse("file:///android_asset/logo/" + university.getLogo())).centerCrop().into(iv_university);
        setupUIFood(view);

    }

    private void setup() {
        layoutManager = new GridLayoutManager(
                getContext(), 4, GridLayoutManager.VERTICAL, false);
        rv_bus_for_uni.setLayoutManager(layoutManager);

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

   // @Override
   // public void onClick(View v) {
//        String IDsave = university.getId();
//        org.greenrobot.eventbus.EventBus.getDefault().postSticky(IDsave);
//        EventBus.getDefault().postSticky(university.getId());
//        busList = RealmHandler.getInstance().getNumberList(university);
        //StringRealmObject stringRealmObject = (StringRealmObject) v.getTag();

       // FoodRealmObject foodRealmObject = (FoodRealmObject) v.getTag();
//        Bus detail = realmHandler.getDetailBus(busList);

//        org.greenrobot.eventbus.EventBus.getDefault().postSticky(detail);
//                org.greenrobot.eventbus.EventBus.getDefault().postSticky(stringRealmObject);
//        bus = () view.getTag();

       // EventBus.getDefault().postSticky(foodRealmObject);
             //   changeFragment(new InfoFoodFragment(), true);
  //  }

    @Override
    public void onClick(View view) {
        if ( view.getTag() instanceof FoodRealmObject ){
            Log.d(TAG,"Click Food");
            food = (FoodRealmObject) view.getTag();
            EventBus.getDefault().postSticky( new com.example.admin.appbus1.managers.EventFood(food));
            changeFragment(new InfoFoodFragment(), true);
        } else if (view.getTag() instanceof StringRealmObject) {
            bus = (StringRealmObject) view.getTag();
            EventBus.getDefault().postSticky(new com.example.admin.appbus1.managers.EvenBusForUni(bus));
            changeFragment(new ShowBusFragment(), true);
        }

    }

    private void loadFood() {
        RealmHandler.getInstance().clearFoodInRealm();
        if(!Constant.isLoadedFood){

            //List<University> universityList = RealmHandler.getInstance().getUniversityFromRealm();
            //Log.d(TAG, String.valueOf(universityList));
//            String ID = universityList.equals()
            Log.d(TAG,IDsave);

            //String id = EventBus.getDefault().register(university.getId());
            final int iD = Integer.parseInt(IDsave) - 1;
            // String aidi = String.valueOf(university.equals(getId()));
            //Log.d(TAG,aidi);
            serviceFactory = new ServiceFactory(ApiUrl.BASE_URL);
            FoodApi service = serviceFactory.createService(FoodApi.class);
            Call<FoodApi.Food> call = service.callFood();
            call.enqueue(new Callback<FoodApi.Food>() {
                @Override
                public void onResponse(Call<FoodApi.Food> call, Response<FoodApi.Food> response) {
                    Log.d(TAG, "onResponse");

                    List<FoodApi.FoodList> list = response.body().getFoodList();
                    //int iD = (int)university.getId();

                    Log.d(TAG,list.size() +"");
                    //for (int i = 1; i <  1; i++){
                    Food food = new Food();
                    food.setId(list.get(iD).getId());
                    List<FoodApi.Foody> foodyLists = list.get(iD).getFoody();
                    Log.d(TAG,foodyLists + "");

                    RealmList<FoodRealmObject> foodList = new RealmList<>();
                    for (int j = 0; j < foodyLists.size(); j ++){
                        FoodRealmObject foodRealmObject = new FoodRealmObject(foodyLists.get(j).getName(),foodyLists.get(j).getAddress(),
                                foodyLists.get(j).getImage(),foodyLists.get(j).getTime(),foodyLists.get(j).getPrice());
                        foodList.add(foodRealmObject);
                        Log.d(TAG,foodyLists.get(j).getName());
                    }
                    food.setFoody(foodList);


                    RealmHandler.getInstance().addFoodToRealm(food);
                    //}
                    EventBus.getDefault().post(new EventDataReady());
//                    Utils.setLoadData(getActivity(), Constant.keyLoadedFood, true);

                }

                @Override
                public void onFailure(Call<FoodApi.Food> call, Throwable t) {
                    Log.d("No No ", t.toString());
                }
            });
        } else {
            EventBus.getDefault().post(new EventDataReady());
        }
    }
}
