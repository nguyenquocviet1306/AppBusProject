package com.example.admin.appbus1.fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.managers.RealmHandler;
import com.example.admin.appbus1.models.FoodRealmObject;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends Fragment implements FragmentWithSearch, LocationListener,OnMapReadyCallback {

    private FoodRealmObject foodRealmObject;
    public static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 100;
    private View rootView;
    GoogleMap myMap;
    MapView mMapView;
    RealmHandler realmHandler;
    Location myLocation = null;
    private String myMarkerLocationId;
    private ProgressDialog progressDialog;
    double x;
    double y;


    public AddressFragment() {
        // Required empty public constructor
    }

    @Subscribe(sticky = true)
    public void receiveInfo(FoodRealmObject event){
        this.foodRealmObject = event;
//        foodForUniAdapter = new FoodForUniAdapter();
//        foodForUniAdapter.setOnItemClickListener(this);
//        rv_uni_food.setAdapter(foodForUniAdapter);
//        foodForUniAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        realmHandler = RealmHandler.getInstance();
        EventBus.getDefault().register(this);
        getActivity().setTitle("Map");
//        setupProgress();
        try {
            rootView = inflater.inflate(R.layout.fragment_address, container, false);
            MapsInitializer.initialize(this.getActivity());
            mMapView = (MapView) rootView.findViewById(R.id.mapAddress);
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);
        }
        catch (InflateException e){
            Log.e(TAG, "Inflate exception");
        }
        return rootView;
    }

//    private void setupProgress() {
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setTitle("Map loading...");
//        progressDialog.setMessage("Please wait...");
//        progressDialog.setCancelable(true);
//        progressDialog.show();
//    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void doSearch(String searchString) {

    }

    @Override
    public void closeSearch() {

    }

    public boolean getLatitudeAndLongitudeFromGoogleMapForAddress(String searchedAddress){

        Geocoder coder = new Geocoder(getContext());
        List<Address> address;
        try
        {
            address = coder.getFromLocationName(searchedAddress,5);
            if (address == null) {
                Log.d(TAG, "############Address not correct #########");
            }
            Address location = address.get(0);

            Log.d(TAG, "Address Latitude : "+ location.getLatitude() + "Address Longitude : " + location.getLongitude());
            x = location.getLatitude();
            y = location.getLongitude();
            return true;

        }
        catch(Exception e)
        {
            Log.d(TAG, "MY_ERROR : ############Address Not Found");
            return false;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        myMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
//                progressDialog.dismiss();
//                askPermissionsAndShowMyLocation();
            }
        });
        myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        myMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        myMap.setMyLocationEnabled(true);
        showMyLocation();
    }

    private void showMyLocation() {
        getLatitudeAndLongitudeFromGoogleMapForAddress(foodRealmObject.getAddress());
        LatLng TTTH_KHTN = new LatLng(x, y);
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(TTTH_KHTN, 13));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(TTTH_KHTN)             // Sets the center of the map to location user
                .zoom(16)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        myMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        MarkerOptions option=new MarkerOptions();
        option.position(TTTH_KHTN);
        option.title(foodRealmObject.getName()).snippet(foodRealmObject.getAddress());
        option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        option.alpha(0.8f);
        option.rotation(90);
        Marker maker = myMap.addMarker(option);
        maker.showInfoWindow();
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(TTTH_KHTN, 15));

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState); mMapView.onSaveInstanceState(outState);
    }
    @Override public void onLowMemory()
    {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    @Override public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override public void onDestroyView() {
        super.onDestroyView();
    }
}
