package com.example.sardorbek.adminside.Remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sardorbek on 4/22/18.
 */

public interface IGeoCordinates {

    @GET("maps/api/geocode/json")
    Call<String> getGeoCode (@Query("address") String address);

    @GET("maps/api/direction/json")
    Call<String> getDirection(@Query("origin") String origin,@Query("destination") String destionation);

}
