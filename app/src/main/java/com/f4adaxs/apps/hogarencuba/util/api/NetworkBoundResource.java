package com.f4adaxs.apps.hogarencuba.util.api;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.f4adaxs.apps.hogarencuba.models.responsive.ResponseBaseModel;
import com.f4adaxs.apps.hogarencuba.util.AppExecutors;


// ResultType: Type for the Resource data.
// RequestType: Type for the API response.
public abstract class NetworkBoundResource<ResultType, RequestType> {

    private AppExecutors appExecutors;
    private MediatorLiveData result = new MediatorLiveData<Resource<ResultType>>();

    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        result.setValue(Resource.loading(null));
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch((ResultType) data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> {
                    setValue(Resource.success((ResultType) newData));
                });
            }
        });

    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (result.getValue() != newValue) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork(LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, newData -> {
            setValue(Resource.loading((ResultType) newData));
        });
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);
            if(response instanceof ApiSuccessResponse) {
                appExecutors.diskIO().execute(() -> {
                    RequestType requestType = processResponse((ApiSuccessResponse<RequestType>) response);
                    if(requestType != null) {
                        saveCallResult(requestType);
                    }
                    appExecutors.mainThread().execute(() -> {
                        // we specially request a new live data,
                        // otherwise we will get immediately last cached value,
                        // which may not be updated with latest results received from network.
                        result.addSource(loadFromDb(), newData -> {
                            setValue(Resource.success((ResultType) newData));
                        });
                    });
                });
            } else if(response instanceof ApiEmptyResponse) {
                appExecutors.mainThread().execute(() -> {
                    // reload from disk whatever we had
                    result.addSource(loadFromDb(), newData -> {
                        setValue(Resource.success((ResultType) newData));
                    });
                });
            } else if(response instanceof ApiErrorResponse) {
                onFetchFailed();
                result.addSource(dbSource, newData -> {
                    setValue(Resource.error(((ApiErrorResponse<RequestType>) response).getErrorMessage(), (ResultType) newData));
                });
            }

        });
    }

    @SuppressLint("WrongThread")
    @WorkerThread
    protected RequestType processResponse(ApiSuccessResponse<RequestType> response) {
//        ResponseBaseModel responseBaseModel = (ResponseBaseModel) response.getBody();
//        if(!responseBaseModel.isSuccess()) {
//            onFetchFailed();
//            result.addSource(loadFromDb(), newData -> {
//                setValue(Resource.error(responseBaseModel.getMessage(), (ResultType) newData));
//            });
//            return null;
//        } else {
            return response.getBody();
//        }
    }

    // Called to save the result of the API response into the database.
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    // Called to get the cached data from the database.
    @NonNull @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    // Called to create the API call.
    @NonNull @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    protected void onFetchFailed() {

    }

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    public LiveData<Resource<ResultType>> getAsLiveData() {
        return this.result;
    }
}