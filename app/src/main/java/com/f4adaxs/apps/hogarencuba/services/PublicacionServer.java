package com.f4adaxs.apps.hogarencuba.services;

import android.arch.lifecycle.LiveData;

import com.f4adaxs.apps.hogarencuba.models.responsive.PublicacionResponse;
import com.f4adaxs.apps.hogarencuba.models.responsive.data.PublicacionData;
import com.f4adaxs.apps.hogarencuba.util.api.ApiResponse;

import java.util.List;

import retrofit2.http.GET;

public interface PublicacionServer {

//    @GET("serie/json2")
    @GET("api.json")
    LiveData<ApiResponse<List<PublicacionData>>> getAll();
}
