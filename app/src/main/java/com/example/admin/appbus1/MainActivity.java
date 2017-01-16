package com.example.admin.appbus1;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.admin.appbus1.fragment.AboutFragment;
import com.example.admin.appbus1.fragment.FeedBackFragment;
import com.example.admin.appbus1.fragment.FragmentWithSearch;
import com.example.admin.appbus1.fragment.ListBusFragment;
import com.example.admin.appbus1.fragment.ListFoodFragment;
import com.example.admin.appbus1.fragment.ListUniFragment;
import com.example.admin.appbus1.fragment.MapFragment;
import com.example.admin.appbus1.managers.RealmHandler;
import com.facebook.FacebookSdk;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener {

    private SearchView searchView;
    RealmHandler realmHandler;
    ArrayAdapter<String> adapter;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.admin.appbus1",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        changeFragment(new ListUniFragment());

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private void changeFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_container,fragment);
        fragmentTransaction.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItemImpl menuItem = (MenuItemImpl) menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {

            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                closeSearch();
            }
        });
        initSearchView(searchView);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_map) {
            changeFragment(new MapFragment());
        } else if (id == R.id.nav_listuniversity) {
            changeFragment(new ListUniFragment());
        } else if (id == R.id.nav_listbus) {
            changeFragment(new ListBusFragment());
        } else if (id == R.id.nav_food) {
            changeFragment(new ListFoodFragment());
        }  else if (id == R.id.nav_about) {
            changeFragment(new AboutFragment());
        } else if (id == R.id.nav_feedback) {
            changeFragment(new FeedBackFragment());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initSearchView(SearchView searchView) {
        final AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, R.drawable.cursor);
        } catch (Exception e) {
        }

        searchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    doSearch(searchTextView.getText().toString());
                }
                return false;
            }
        });

    }

    private void closeSearch() {
        FragmentWithSearch fragmentWithSearch = (FragmentWithSearch) getSupportFragmentManager().findFragmentById(R.id.fl_container);
        if (fragmentWithSearch != null) {
            fragmentWithSearch.closeSearch();
        }
    }

    private void doSearch(String searchString) {
        FragmentWithSearch fragmentWithSearch = (FragmentWithSearch) getSupportFragmentManager().findFragmentById(R.id.fl_container);
        if (fragmentWithSearch != null) {
            fragmentWithSearch.doSearch(searchString);
        }

    }


    @Override
    public void onBackStackChanged() {
        int fragmenCount = getSupportFragmentManager().getBackStackEntryCount();
        if(fragmenCount > 0) {
            // Change to arrow icon

            toggle.setDrawerIndicatorEnabled(false);
            toggle.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24px);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {

            toggle.setDrawerIndicatorEnabled(true);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.setToolbarNavigationClickListener(null);
        }
    }
}
