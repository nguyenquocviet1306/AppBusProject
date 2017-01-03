package com.example.admin.appbus1.services;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by giaqu on 1/1/2017.
 */

public interface UniversityAPI {
    @GET(ApiUrl.API_URL_UNIVERSITY)
    Call<University> callUniversity();
    class University{
        @SerializedName("university")
        private List<UniversityList> universityList;

        public List<UniversityList> getUniversityList(){
            return universityList;
        }
    }

    class UniversityList{
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("abbreviation")
        private String abbreviation;
        @SerializedName("logo")
        private String logo;
        @SerializedName("address")
        private String address;
        @SerializedName("bus")
        private List<Number> bus;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getAbbreviation() {
            return abbreviation;
        }

        public String getLogo() {
            return logo;
        }

        public String getAddress() {
            return address;
        }

        public List<Number> getBus() {
            return bus;
        }
    }

    class Number{
        @SerializedName("number")
        private String number;

        public String getNumber() {
            return number;
        }
    }
}
