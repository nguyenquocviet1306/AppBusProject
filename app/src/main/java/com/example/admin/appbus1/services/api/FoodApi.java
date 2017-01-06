package com.example.admin.appbus1.services.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by giaqu on 1/6/2017.
 */

public interface FoodApi {
    @GET(ApiUrl.API_URL_FOOD)
    Call<FoodApi.Food> callFood();
    class Food{
        @SerializedName("food")
        private List<FoodApi.FoodList> foodList;

        public List<FoodApi.FoodList> getFoodList(){
            return foodList;
        }
    }

    class FoodList{
        @SerializedName("id")
        private String id;
        @SerializedName("foody")
        private String foody;

        public String getId() {
            return id;
        }

        public String getFoody() {
            return foody;
        }
    }

    class Number {
        @SerializedName("name")
        private String name;
        @SerializedName("address")
        private String address;
        @SerializedName("image")
        private String image;
        @SerializedName("time")
        private String time;
        @SerializedName("price")
        private String price;

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public String getImage() {
            return image;
        }

        public String getTime() {
            return time;
        }

        public String getPrice() {
            return price;
        }
    }
}
