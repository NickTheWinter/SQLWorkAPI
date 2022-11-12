package com.example.apiwork;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("airlines")
    Call<Mask> createPost(@Body Mask maskModel);

    @PUT("airlines/")
    Call<Mask> updatePost(@Query("id") int Id,@Body Mask maskModel);

    @DELETE("airlines/")
    Call<Mask> deleteData(@Query("id") int Id);
}
