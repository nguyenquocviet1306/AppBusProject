package com.example.admin.appbus1.fragment;


import android.os.Bundle;
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
import android.widget.ProgressBar;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.adapters.FoodAdapter;
import com.example.admin.appbus1.managers.Constant;
import com.example.admin.appbus1.managers.EventFood;
import com.example.admin.appbus1.managers.RealmHandler;
import com.example.admin.appbus1.managers.Utils;
import com.example.admin.appbus1.managers.event.EventDataReady;
import com.example.admin.appbus1.models.Food;
import com.example.admin.appbus1.models.FoodRealmObject;
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
public class ListFoodFragment extends Fragment implements View.OnClickListener,FragmentWithSearch {

    private static final String TAG = ListFoodFragment.class.toString();
    private List<FoodRealmObject> foods = RealmHandler.getInstance().getFoodFromRealmObject();

    private RealmHandler realmHandler;
    private GridLayoutManager layoutManager;
    private FoodAdapter foodAdapter;
    private ServiceFactory serviceFactory;
    private FoodRealmObject food;
    public University university;
    @BindView(R.id.progressfood)
    ProgressBar progressBar;
    //String IDsave;

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
        realmHandler = RealmHandler.getInstance();
        EventBus.getDefault().register(this);
        setHasOptionsMenu(true);

//        setHasOptionsMenu(true);
        setupUI(view);
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.search, menu);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

//    @Subscribe(sticky = true)
//    public void receiveInfo(String event){
//        this.IDsave = event;
//    }

    @Subscribe(sticky = true)
    public void setAdapterView(EventDataReady event){
        foodAdapter = new FoodAdapter();
        foodAdapter.setOnItemClickListener(this);
        rv_food.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();
    }

    private void loadData() {
        RealmHandler.getInstance().clearFoodInRealm();

        if(!Constant.isLoadedFood){
            progressBar.setVisibility(View.VISIBLE);
            //final int iD = Integer.parseInt(IDsave) - 1;            // String aidi = String.valueOf(university.equals(getId()));
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
                    for (int i = 0; i <  list.size(); i++){
                    Food food = new Food();
                    food.setId(list.get(i).getId());
                    List<FoodApi.Foody> foodyLists = list.get(i).getFoody();
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
                    }
                    EventBus.getDefault().post(new EventDataReady());
                    Utils.setLoadData(getActivity(), Constant.keyLoadedFood, true);
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

    private void setupUI(View view) {

        layoutManager = new GridLayoutManager(
                view.getContext(), 1, LinearLayoutManager.VERTICAL, false);
        rv_food.setLayoutManager(layoutManager);

        loadData();
    }


    @Override
    public void onClick(View view) {
        food = (FoodRealmObject) view.getTag();
        EventBus.getDefault().postSticky( new EventFood(food));
        changeFragment(new InfoFoodFragment(), true, null);

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
