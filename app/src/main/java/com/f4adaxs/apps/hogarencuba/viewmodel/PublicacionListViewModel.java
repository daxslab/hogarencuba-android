package com.f4adaxs.apps.hogarencuba.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.f4adaxs.apps.hogarencuba.db.entity.Publicacion;
import com.f4adaxs.apps.hogarencuba.util.BasicApp;
import com.f4adaxs.apps.hogarencuba.util.api.Resource;

import java.util.List;

public class PublicacionListViewModel extends AndroidViewModel {
    private final LiveData<Resource<List<Publicacion>>> publicacionListLiveData;

    public PublicacionListViewModel(@NonNull Application application) {
        super(application);

        publicacionListLiveData = ((BasicApp) application).getPublicacionRepository().getAll();

    }

    public LiveData<Resource<List<Publicacion>>> getPublicacionListList() {
        return publicacionListLiveData;
    }
}
