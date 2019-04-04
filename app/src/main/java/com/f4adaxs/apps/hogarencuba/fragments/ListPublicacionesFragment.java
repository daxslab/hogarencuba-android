package com.f4adaxs.apps.hogarencuba.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.f4adaxs.apps.hogarencuba.R;
import com.f4adaxs.apps.hogarencuba.activities.BaseActivity;
import com.f4adaxs.apps.hogarencuba.activities.DetailsActivity;
import com.f4adaxs.apps.hogarencuba.adapters.PublicacionAdapter;
import com.f4adaxs.apps.hogarencuba.db.entity.Publicacion;
import com.f4adaxs.apps.hogarencuba.util.api.Resource;
import com.f4adaxs.apps.hogarencuba.viewmodel.PublicacionListViewModel;

import java.util.List;


public class ListPublicacionesFragment extends BaseFragment {

    public static final String TAG = ListPublicacionesFragment.class.getSimpleName();

    private Context mContext;
    private PublicacionAdapter publicacionAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final PublicacionListViewModel viewModel = ViewModelProviders.of(this).get(PublicacionListViewModel.class);

        subscribeUi(viewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.variable_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        publicacionAdapter = new PublicacionAdapter(mContext, (publicacion) -> {

            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("code", publicacion.getCode());
            intent.putExtra("title", publicacion.getTitle());
            intent.putExtra("bedroomsCount", publicacion.getBedroomsCount());
            intent.putExtra("bathroomsCount", publicacion.getBathroomsCount());
            intent.putExtra("garageCount", publicacion.getGarageCount());
            intent.putExtra("areaCount", publicacion.getAreaCount());
            intent.putExtra("metadata", publicacion.getMetadata());
            intent.putExtra("description", publicacion.getDescription());
            intent.putExtra("location", publicacion.getLocation());
            intent.putExtra("price", publicacion.getPrice());
            intent.putExtra("ranking", publicacion.getRanking());
            intent.putExtra("created_at", publicacion.getCreated_at());
            intent.putExtra("firstImageUrl", publicacion.getFirstImageUrl());
            intent.putExtra("type", publicacion.getType());
            startActivity(intent);

        });
        recyclerView.setAdapter(publicacionAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    private void subscribeUi(PublicacionListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getPublicacionListList().observe(this, new Observer<Resource<List<Publicacion>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Publicacion>> listResource) {
                if(listResource != null) {
                    if(listResource.status == Resource.Status.SUCCESS) {
                        ((BaseActivity)getActivity()).hideProgressDialog();
                        List<Publicacion> list = listResource.data;
//                        for (Variable v : list) {
//                            VariableCheckByNombreViewModel.Factory factory = new VariableCheckByNombreViewModel.Factory(((BaseActivity)getActivity()).getApplication(), v.getNombre());
//                            VariableCheckByNombreViewModel variableCheckByNombreViewModel = ViewModelProviders.of(getActivity(), factory).get(VariableCheckByNombreViewModel.class);
//                            variableCheckByNombreViewModel.getObservableVariableCheckLiveData().observe(getActivity(), new Observer<VariableCheck>() {
//                                @Override
//                                public void onChanged(@Nullable VariableCheck variableCheck) {
//                                    if (variableCheck != null) {
//                                        v.setChecked(variableCheck.isCheked());
//                                        Toast.makeText(mContext, "v "+v.isChecked(), Toast.LENGTH_SHORT).show();
//                                        Toast.makeText(mContext, "variableCheck "+variableCheck.isCheked(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                        }


                        publicacionAdapter.setPublicacionList(list);
                    } else if(listResource.status == Resource.Status.ERROR) {
                        ((BaseActivity)getActivity()).hideProgressDialog();
                        ((BaseActivity)getActivity()).processRequestError(listResource.message);
                    } else if(listResource.status == Resource.Status.LOADING) {
                        ((BaseActivity)getActivity()).showProgressDialog(null);
                    }
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        // Associate searchable configuration with the SearchView
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                publicacionAdapter.getFilter().filter(query);
                publicacionAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                publicacionAdapter.getFilter().filter(query);
                publicacionAdapter.notifyDataSetChanged();
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}
