package com.example.admin.appbus1.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.admin.appbus1.R;
import com.example.admin.appbus1.managers.event.EventMap;
import com.google.android.gms.maps.CameraUpdate;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class WayFragment extends Fragment implements FragmentWithSearch, LocationListener,OnMapReadyCallback, RoutingListener {



    public static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 100;
    private View rootView;
    GoogleMap myMap;
    MapView mMapView;
    Location myLocation = null;
    private String myMarkerLocationId;
    double startX;
    double startY;
    double endX;
    double endY;
    //    private ProgressDialog progressDialog;
    LatLng start;
    //    LatLng waypoint= new LatLng(21.0048173,105.8389954);
    LatLng end ;

//    LatLng start1;
//    LatLng end1;
    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.primary_dark,R.color.primary,R.color.primary_light,R.color.accent,R.color.primary_dark_material_light};


    public WayFragment(){

    }

    @Subscribe(sticky = true)
    public void setAdapterView(EventMap event){


        this.start = event.getStart();
        this.end = event.getEnd();

        Log.d("position", "" + start.toString() +" " + end.toString());
    }




    @Override public void onDetach() {
        super.onDetach();
    }

    @Override public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        polylines = new ArrayList<>();
        getActivity().setTitle("Map");
        EventBus.getDefault().register(this);
//        setupProgress();
        try {
            rootView = inflater.inflate(R.layout.fragment_way, container, false);
            MapsInitializer.initialize(this.getActivity());
            mMapView = (MapView) rootView.findViewById(R.id.way);
            mMapView.onCreate(savedInstanceState);
            Log.d("fuck", "run");
            hideKeyboard();
            mMapView.getMapAsync(this);
        }
        catch (InflateException e){
            Log.e(TAG, "Inflate exception");
        }
        if (mMapView != null &&
                mMapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 200);
        }
        ButterKnife.bind(this, rootView);

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
    public void doSearch(String searchString) {

    }

    @Override
    public void closeSearch() {

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
    @Override public void onMapReady(GoogleMap googleMap) {

        myMap = googleMap;

        myMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
//                progressDialog.dismiss();
                askPermissionsAndShowMyLocation();
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

//        start1 = start;
//
//        end1 = end;

        Routing routing = new Routing.Builder()
                .travelMode(Routing.TravelMode.WALKING)
                .withListener(this)
                .waypoints(start, end)
                .build();
        routing.execute();

    }



    private void askPermissionsAndShowMyLocation() {
        // Với API >= 23, bạn phải hỏi người dùng cho phép xem vị trí của họ.
        if (Build.VERSION.SDK_INT >= 23) {
            int accessCoarsePermission
                    = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
            int accessFinePermission
                    = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
            Log.d(TAG, String.valueOf(accessCoarsePermission)+"cou");
            Log.d(TAG, String.valueOf(accessFinePermission+"fine"));
            if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED
                    || accessFinePermission != PackageManager.PERMISSION_GRANTED) {
                // Các quyền cần người dùng cho phép.
                String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION};
                // Hiển thị một Dialog hỏi người dùng cho phép các quyền trên.
                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);
                return;
            }
        }

        // Hiển thị vị trí hiện thời trên bản đồ.
        this.showMyLocation();
    }

    private void showMyLocation() {

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        String locationProvider = this.getEnabledLocationProvider();

        if (locationProvider == null) {
            return;
        }

        // Millisecond
        final long MIN_TIME_BW_UPDATES = 1000;
        // Met
        final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;

        try {
            // Đoạn code nay cần người dùng cho phép (Hỏi ở trên ***).
            locationManager.requestLocationUpdates(
                    locationProvider,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
            // Lấy ra vị trí.
            myLocation = locationManager
                    .getLastKnownLocation(locationProvider);
        }
        // Với Android API >= 23 phải catch SecurityException.
        catch (SecurityException e) {
            Toast.makeText(getContext(), "Show My Location Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(TAG, "Show My Location Error:" + e.getMessage());
            e.printStackTrace();
            return;
        }
        if (myLocation != null) {

            LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
            Log.d("AAA", String.format("%s, %s", myLocation.getLatitude(), myLocation.getLongitude()))  ;

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)             // Sets the center of the map to location user
                    .zoom(16)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            myMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            // Thêm Marker cho Map:
            MarkerOptions option = new MarkerOptions();
            option.title("My Location");
            option.snippet("....");
            option.position(latLng);
            option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
            Marker currentMarker = myMap.addMarker(option);
            myMarkerLocationId = currentMarker.getId();
            currentMarker.showInfoWindow();

        } else {
//            Toast.makeText(getContext(), "", Toast.LENGTH_LONG).show();
        }
    }

    private String getEnabledLocationProvider() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        // Tiêu chí để tìm một nhà cung cấp vị trí.
        Criteria criteria = new Criteria();

        // Tìm một nhà cung vị trí hiện thời tốt nhất theo tiêu chí trên.
        // ==> "gps", "network",...
        String bestProvider = locationManager.getBestProvider(criteria, true);

        boolean enabled = locationManager.isProviderEnabled(bestProvider);
        Log.d(TAG, enabled+"enable");
        if (!enabled) {
            Toast.makeText(getContext(), "No location provider enabled!", Toast.LENGTH_LONG).show();
            return null;
        }
        return bestProvider;
    }

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
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 13));

        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

        myMap.moveCamera(center);


        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i <route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = myMap.addPolyline(polyOptions);
            polylines.add(polyline);

            //Toast.makeText(getApplicationContext(),"Route "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()+": duration - "+ route.get(i).getDurationValue(),Toast.LENGTH_SHORT).show();
        }

//        start1 = start;
//        end1 = end;
                // Start marker
                MarkerOptions options = new MarkerOptions();
                options.position(start);
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
                myMap.addMarker(options);

                // End marker
                options = new MarkerOptions();
                options.position(end);
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));
                myMap.addMarker(options);


    }

    @Override
    public void onRoutingCancelled() {

    }

    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                .getSystemService(android.content.Context.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(
                getActivity().getCurrentFocus()
                        .getWindowToken(), 0);

    }

}
