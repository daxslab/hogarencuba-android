package com.f4adaxs.apps.hogarencuba.models.responsive;

import com.f4adaxs.apps.hogarencuba.models.responsive.data.PublicacionData;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class PublicacionResponse extends ResponseBaseModel {

    @SerializedName("data")
    private List<PublicacionData> data;

    public List<PublicacionData> getData() {
        return data;
    }

    public void setData(List<PublicacionData> data) {
        this.data = data;
    }

}
