package com.example.admin.appbus1.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.managers.EventFood;
import com.example.admin.appbus1.models.FoodRealmObject;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFoodFragment extends Fragment {

    private static final String TAG = InfoUniFragment.class.toString();
    @BindView(R.id.image_food)
    ImageView imageFood;
    @BindView(R.id.name_food)
    TextView nameFood;
    @BindView(R.id.address_food)
    TextView addressFood;
    @BindView(R.id.time_food)
    TextView timeFood;
    @BindView(R.id.price_food)
    TextView priceFood;
    @BindView(R.id.iv_fp)
    ImageView btFb;
    ShareDialog shareDialog;

    private FoodRealmObject foodRealmObject;


    public InfoFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_info_food, container, false);
        ButterKnife.bind(this, view);
        shareDialog = new ShareDialog(this);
        EventBus.getDefault().register(this);
        setupUI();
        onClickListener();
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    private void onClickListener() {
        btFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "clicked");
                if (ShareDialog.canShow(SharePhotoContent.class)) {
                    Bitmap bmp = getBitmapFromURL(foodRealmObject.getImage());


                    SharePhoto photo = new SharePhoto.Builder()
                            .setBitmap(bmp)
                            .setCaption("Give me my codez or I will ... you know, do that thing you don't like!")
                            .build();

                    SharePhotoContent content = new SharePhotoContent.Builder()
                            .addPhoto(photo)
                            .build();

                    ShareApi.share(content, null);
                    shareDialog.show(content);


                }
            }
        });
    }
    @Subscribe(sticky = true)
    public void receiveInfo(EventFood event){
        this.foodRealmObject = event.getFood();
    }

    private void setupUI() {
        Picasso.with(getContext()).load(foodRealmObject.getImage()).into(imageFood);
        nameFood.setText("Quán: " + foodRealmObject.getName());
        addressFood.setText("Địa chỉ: " + foodRealmObject.getAddress());
        timeFood.setText("Thời gian: " + foodRealmObject.getTime());
        priceFood.setText("Giá: " + foodRealmObject.getPrice());
    }


}
