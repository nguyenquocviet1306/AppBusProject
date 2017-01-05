package com.example.admin.appbus1.services;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by giaqu on 1/4/2017.
 */

public interface BusAPI {
    @GET(ApiUrl.API_URL_BUS)
    Call<BusAPI.Bus> callBus();
    class Bus{
        @SerializedName("bus")
        private List<BusAPI.BusList> busList;
//        private List<UniversityAPI.Number> numberBusList;

        public List<BusAPI.BusList> getBusList(){
            return busList;
        }
//        public List<UniversityAPI.Number>  getNumberBusList(){
//            return numberBusList;
//        }
    }

    class BusList{
        @SerializedName("id")
        private String id;
        @SerializedName("way")
        private String way;
        @SerializedName("time")
        private String time;
        @SerializedName("frequency")
        private String frequency;
        @SerializedName("price")
        private String price;
        @SerializedName("go")
        private String go;
        @SerializedName("back")
        private String back;

        public String getId() {

            return id;
        }

        public String getWay() {
            return way;
        }

        public String getTime() {
            return time;
        }

        public String getFrequency() {
            return frequency;
        }

        public String getPrice() {
            return price;
        }

        public String getGo() {
            return go;
        }

        public String getBack() {
            return back;
        }
    }
}
