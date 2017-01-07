package com.example.admin.appbus1.viewholders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.models.FoodRealmObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by giaqu on 1/6/2017.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.iv_food)
    ImageView ivFood;
    @BindView(R.id.tv_food)
    TextView tvFood;
    Context context;

    public FoodViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
    class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public ImageDownloader(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public void setDataFood(FoodRealmObject food)  {
//        Picasso.with(itemView.getContext())
//                .load(breath.getImage())
//                .fit()
//                .centerCrop()
//                .into(imageView);

        tvFood.setText(food.getName());
        //Picasso.with(tvFood.getContext()).load(food.getImage()).fit().centerCrop().into(ivFood);
        //URL url = new URL(food.getImage());
        //Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        //itemView.setImageBitmap(bmp);
        new ImageDownloader(ivFood).execute(food.getImage());

//        try {
//            URL url = null;
//            url = new URL(food.getImage());
//            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            ivFood.setImageBitmap(bmp);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        itemView.setTag(food);

    }
}
