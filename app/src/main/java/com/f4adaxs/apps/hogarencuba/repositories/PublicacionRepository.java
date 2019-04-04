package com.f4adaxs.apps.hogarencuba.repositories;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.f4adaxs.apps.hogarencuba.db.AppDatabase;
import com.f4adaxs.apps.hogarencuba.db.entity.Publicacion;
import com.f4adaxs.apps.hogarencuba.models.responsive.data.PublicacionData;
import com.f4adaxs.apps.hogarencuba.models.transformer.PublicacionTransformer;
import com.f4adaxs.apps.hogarencuba.services.PublicacionServer;
import com.f4adaxs.apps.hogarencuba.util.AppExecutors;
import com.f4adaxs.apps.hogarencuba.util.ConnectionUtils;
import com.f4adaxs.apps.hogarencuba.util.RateLimiter;
import com.f4adaxs.apps.hogarencuba.util.ServiceGenerator;
import com.f4adaxs.apps.hogarencuba.util.api.ApiResponse;
import com.f4adaxs.apps.hogarencuba.util.api.NetworkBoundResource;
import com.f4adaxs.apps.hogarencuba.util.api.Resource;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PublicacionRepository {

    private PublicacionServer webservice;
    private static PublicacionRepository sInstance;

    private final AppDatabase mDatabase;

    private final Context mContext;

    private RateLimiter<String> nivelListRateLimit = new RateLimiter<String>(10, TimeUnit.MINUTES);

    private PublicacionRepository(final AppDatabase database, final Context context) {
        mDatabase = database;
        mContext = context;
        webservice = ServiceGenerator.create(PublicacionServer.class);
    }

    public static PublicacionRepository getInstance(final AppDatabase database, final Context context) {
        if (sInstance == null) {
            synchronized (PublicacionRepository.class) {
                if (sInstance == null) {
                    sInstance = new PublicacionRepository(database, context);
                }
            }
        }
        return sInstance;
    }

    public LiveData<Resource<List<Publicacion>>> getAll() {
        return new NetworkBoundResource<List<Publicacion>,List<PublicacionData>>(new AppExecutors()) {
            @Override
            protected void saveCallResult(@NonNull List<PublicacionData> item) {
                List<Publicacion> publicacionEntities = PublicacionTransformer.transform(item);
                addPublicacion(publicacionEntities);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Publicacion> data) {
                return ConnectionUtils.isNetworkConnected(mContext) && nivelListRateLimit.shouldFetch("nivelListRateLimit");
            }

            @NonNull
            @Override
            protected LiveData<List<Publicacion>> loadFromDb() {
                return mDatabase.publicacionDao().findAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<PublicacionData>>> createCall() {
                return webservice.getAll();
            }
        }.getAsLiveData();
    }


    public void addPublicacion(List<Publicacion> publicacionEntityList) {
        new PublicacionRepository.addAsyncTask(mDatabase).execute(publicacionEntityList);
    }

    private static class addAsyncTask extends AsyncTask<List<Publicacion>, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final List<Publicacion>... params) {
            db.publicacionDao().insert(params[0]);
            return null;
        }

    }

}
